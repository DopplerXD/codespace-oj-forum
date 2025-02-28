import random

# 随机描述列表
descriptions = [
    "这是一个简单的测试问题。",
    "该问题涉及基本的算法知识。",
    "此问题需要一些逻辑思维。",
    "这是一个考验编程能力的问题。",
    "解决这个问题需要一定的技巧。"
]

# 随机标签列表
tags = [
    "test", "algorithm", "programming", "logic", "basic"
]

# 生成 SQL 插入语句的函数
def generate_sql_insert_statements():
    sql_statements = []
    for i in range(11, 75):
        problem_id = f"TEST-{1000 + i}"
        title = str(1000 + i)
        description = random.choice(descriptions)
        tag = [random.choice(tags)]

        # 构建插入语句
        sql = f"INSERT INTO your_table_name (problemId, title, type, timeLimit, memoryLimit, stackLimit, description, input, output, examples, tags, isRemote, source, difficulty, hint, auth, ioScore, codeShare, judgeMode, judgeCaseMode, userExtraFile, judgeExtraFile, spjCode, spjLanguage, isRemoveEndBlank, openCaseResult, isUploadCase) "
        sql += f"VALUES ('{problem_id}', '{title}', 0, 100, 100, 100, '{description}', '', '', '', '{tag[0]}', 0, '', 1, '', 1, 0, 0, '', '', '', '', '', '', 0, 0, 0);"
        sql_statements.append(sql)

    return sql_statements

# 生成 SQL 语句
sql_statements = generate_sql_insert_statements()

# 打印 SQL 语句
for sql in sql_statements:
    print(sql)