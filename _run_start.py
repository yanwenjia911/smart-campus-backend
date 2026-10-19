import subprocess
import os
import sys
import socket
import time

# ================= 配置区 =================
# 自动填充你提供的路径
JAR_PATH = r"D:\Develop\VScode Project\Java\smart-campus\campus-start\target\campus-server.jar"
PORT = 8080
# ==========================================

def check_port(port):
    """检查端口是否被占用"""
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        return s.connect_ex(('localhost', port)) == 0

def start_backend():
    # 1. 验证 JAR 文件是否存在
    if not os.path.exists(JAR_PATH):
        print(f"❌ 错误: 找不到 JAR 文件!")
        print(f"路径: {JAR_PATH}")
        return

    # 2. 检查端口占用情况
    if check_port(PORT):
        print(f"⚠️  警告: 端口 {PORT} 已被占用！")
        print(f"请先关闭占用该端口的进程，或在 application.yml 中修改端口。")
        return

    # 3. 构建启动命令
    # -Dfile.encoding=UTF-8 确保控制台中文输出不乱码
    cmd = [
        "java",
        "-Dfile.encoding=UTF-8",
        "-jar",
        JAR_PATH
    ]

    print(f"🚀 正在启动校园服务平台...")
    print(f"📂 运行路径: {JAR_PATH}")
    print(f"------------------------------------------------------------")

    try:
        # 4. 启动进程
        # 使用 subprocess.Popen 可以实时获取控制台输出
        process = subprocess.Popen(
            cmd,
            stdout=sys.stdout,
            stderr=sys.stderr,
            shell=False
        )

        # 保持 Python 进程不退出，直到 Java 进程结束
        process.wait()

    except KeyboardInterrupt:
        print("\n\n🛑 检测到 Ctrl+C，正在关闭后端服务...")
        process.terminate()
        try:
            process.wait(timeout=5)
        except subprocess.TimeoutExpired:
            process.kill()
        print("✅ 服务已安全停止。")

if __name__ == "__main__":
    start_backend()