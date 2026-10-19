"""
后端 Maven 编译验证脚本
"""
import subprocess, sys, os

BACKEND_DIR = r"d:\Develop\VScode Project\Java\smart-campus"
MVN_CMD = r"D:\Develop\apache-maven-3.9.12\bin\mvn.cmd"

def run():
    print("=" * 50)
    print("开始 Maven 编译验证...")
    print("=" * 50)
    result = subprocess.run(
        [MVN_CMD, 'compile', '--no-transfer-progress'],
        check=False,
        cwd=BACKEND_DIR
    )
    if result.returncode == 0:
        print("\n✅ Maven 编译成功！后端代码无错误。")
    else:
        print(f"\n❌ Maven 编译失败，退出码: {result.returncode}")
    return result.returncode

if __name__ == "__main__":
    sys.exit(run())
