import requests
from datetime import datetime
from fastapi import FastAPI, Request
from pydantic import BaseModel, Field
from langchain_core.tools import tool
from langchain_openai import ChatOpenAI
from langgraph.prebuilt import create_react_agent
import sqlite3
from langgraph.checkpoint.sqlite import SqliteSaver
import contextvars

# ==========================================
# 0. 定义上下文变量 (用于在工具中安全获取前端传来的 Token)
# ==========================================
auth_token_var = contextvars.ContextVar("auth_token", default="")

# ==========================================
# 1. 定义大模型工具 (Tool) 
# ==========================================
@tool
def get_student_schedule(semester: str) -> str:
    """
    获取学生某个学期的全部课表。
    参数 semester 是学期名称，必须符合学校规范，例如 "2026-2027-1"。
    """
    java_api_url = f"http://localhost:8080/api/course/semester/{semester}"
    print(f"⚡ [系统日志] 请求 Java 接口: {java_api_url}")
    
    # 从上下文变量中取出真实的 Token
    real_token = auth_token_var.get()
    
    headers = {
        # 将前端传来的真实 Token 原封不动发给 Java
        "Authorization": real_token
    }
    
    try:
        response = requests.get(java_api_url, headers=headers, timeout=5)
        response.raise_for_status()
        return response.text 
    except requests.exceptions.RequestException as e:
        return f"查询失败，系统异常: {str(e)}"

# ==========================================
# 2. 实例化记忆存储器 (保持不变)
# ==========================================
conn = sqlite3.connect("smart_campus_memory.sqlite", check_same_thread=False)
memory_saver = SqliteSaver(conn)
memory_saver.setup()

llm = ChatOpenAI(
    api_key="*********",      
    base_url="https://api.deepseek.com", 
    model="deepseek-chat",               
    temperature=0.1                      
)

agent = create_react_agent(
    model=llm, 
    tools=[get_student_schedule], 
    checkpointer=memory_saver  
)

# ==========================================
# 3. 搭建 FastAPI 极速应用
# ==========================================
app = FastAPI(title="智慧校园 AI 编排中心")

class ChatRequest(BaseModel):
    user_id: str = Field(..., description="用户唯一标识")
    user_message: str = Field(..., description="用户的提问")

# 注意这里新增了 fastapi.Request 参数，用于读取请求头
@app.post("/api/chat")
async def chat_with_ai(chat_req: ChatRequest, request: Request):
    print(f"\n🙋 [{chat_req.user_id}] 提问: {chat_req.user_message}")
    
    # 🎯 核心逻辑：从微信小程序的请求头中拿到真实 Token，并存入上下文
    # 假设微信传过来的是 "Bearer eyJhbG..."，提取不到默认给空字符串
    client_token = request.headers.get("Authorization", "")
    auth_token_var.set(client_token)
    
    current_date = datetime.now().strftime("%Y-%m-%d")
    safe_prompt = (
        f"【系统后台信息：你是一个智慧校园AI助手。今天是 {current_date}。当前和你对话的用户学号是：{chat_req.user_id}。】\n"
        f"请回答以下用户提问：\n"
        f"用户：{chat_req.user_message}"
    )

    config = {"configurable": {"thread_id": chat_req.user_id}}
    
    try:
        response = agent.invoke(
            {"messages": [("user", safe_prompt)]},
            config=config
        )
        final_answer = response["messages"][-1].content
    except Exception as e:
        final_answer = f"AI 引擎内部异常：{str(e)}"

    print(f"🤖 AI 回复: {final_answer}")
    return {"code": 200, "reply": final_answer}

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8081)