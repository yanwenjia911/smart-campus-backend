-- ============================================================
-- 智慧校园平台 - 完整数据库初始化脚本
-- 数据库: campus_db_dev
-- 说明: 包含所有表结构及测试数据
-- ============================================================

CREATE DATABASE IF NOT EXISTS campus_db_dev
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE campus_db_dev;

-- 禁用外键检查（方便 drop）
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================
-- 1. 用户表 sys_user
-- ============================================================
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
  id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  openid      VARCHAR(64)  NOT NULL                COMMENT '微信 openid',
  student_id  VARCHAR(20)  DEFAULT NULL            COMMENT '学号/工号',
  nickname    VARCHAR(50)  DEFAULT NULL            COMMENT '用户昵称/姓名',
  role        VARCHAR(20)  NOT NULL DEFAULT 'STUDENT' COMMENT '角色: STUDENT/TEACHER/ADMIN',
  avatar_url  VARCHAR(255) DEFAULT NULL            COMMENT '头像URL',
  phone       VARCHAR(20)  DEFAULT NULL            COMMENT '手机号',
  email       VARCHAR(100) DEFAULT NULL            COMMENT '邮箱',
  create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_openid (openid),
  UNIQUE KEY uk_student_id (student_id)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ============================================================
-- 2. 校园卡表 campus_card
-- ============================================================
DROP TABLE IF EXISTS campus_card;
CREATE TABLE campus_card (
  id                  BIGINT         NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  user_id             BIGINT         NOT NULL                COMMENT '关联用户ID',
  card_number         VARCHAR(20)    NOT NULL                COMMENT '卡号（唯一）',
  balance             DECIMAL(10,2)  NOT NULL DEFAULT 0.00   COMMENT '余额',
  status              VARCHAR(20)    NOT NULL DEFAULT 'ACTIVE' COMMENT '状态: ACTIVE/LOST/DISABLED/EXPIRED',
  card_type           VARCHAR(20)    NOT NULL DEFAULT 'STUDENT' COMMENT '卡类型: STUDENT/TEACHER/STAFF',
  activate_time       DATETIME       DEFAULT NULL            COMMENT '激活时间',
  expire_time         DATETIME       DEFAULT NULL            COMMENT '过期时间',
  reported_lost_time  DATETIME       DEFAULT NULL            COMMENT '挂失时间',
  lost_remark         VARCHAR(255)   DEFAULT NULL            COMMENT '挂失备注',
  create_time         DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time         DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_card_number (card_number),
  KEY idx_user_id (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=2000 DEFAULT CHARSET=utf8mb4 COMMENT='校园卡表';

-- ============================================================
-- 3. 校园卡交易记录表 card_transaction
-- ============================================================
DROP TABLE IF EXISTS card_transaction;
CREATE TABLE card_transaction (
  id               BIGINT         NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  card_id          BIGINT         NOT NULL                COMMENT '关联校园卡ID',
  transaction_type VARCHAR(20)    NOT NULL                COMMENT '交易类型: CONSUME/RECHARGE/TRANSFER_IN/TRANSFER_OUT/FINE',
  amount           DECIMAL(10,2)  NOT NULL                COMMENT '交易金额（正数）',
  balance_after    DECIMAL(10,2)  NOT NULL                COMMENT '交易后余额',
  description      VARCHAR(255)   DEFAULT NULL            COMMENT '交易描述',
  location         VARCHAR(100)   DEFAULT NULL            COMMENT '交易地点/商户',
  transaction_time DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易时间',
  create_time      DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY idx_card_id (card_id),
  KEY idx_transaction_time (transaction_time)
) ENGINE=InnoDB AUTO_INCREMENT=3000 DEFAULT CHARSET=utf8mb4 COMMENT='校园卡交易记录表';

-- ============================================================
-- 4. 课程表 course_schedule
-- ============================================================
DROP TABLE IF EXISTS course_schedule;
CREATE TABLE course_schedule (
  id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  user_id     BIGINT       NOT NULL                COMMENT '关联用户ID（学生）',
  semester    VARCHAR(20)  NOT NULL                COMMENT '学期（如: 2024-2025-2）',
  course_name VARCHAR(100) NOT NULL                COMMENT '课程名称',
  teacher_name VARCHAR(50) DEFAULT NULL            COMMENT '教师姓名',
  location    VARCHAR(100) DEFAULT NULL            COMMENT '上课地点',
  week_day    TINYINT      NOT NULL                COMMENT '星期几（1-7）',
  start_class TINYINT      NOT NULL                COMMENT '第几节课开始（1-12）',
  period      TINYINT      NOT NULL DEFAULT 2      COMMENT '课程持续节数',
  week_range  VARCHAR(50)  DEFAULT NULL            COMMENT '周次范围（如: 1-16）',
  type        VARCHAR(20)  DEFAULT '必修'          COMMENT '课程类型：必修/选修',
  PRIMARY KEY (id),
  KEY idx_user_semester (user_id, semester)
) ENGINE=InnoDB AUTO_INCREMENT=4000 DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- ============================================================
-- 5. 成绩表 grade
-- ============================================================
DROP TABLE IF EXISTS grade;
CREATE TABLE grade (
  id           BIGINT         NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  user_id      BIGINT         NOT NULL                COMMENT '关联用户ID（学生）',
  semester     VARCHAR(20)    NOT NULL                COMMENT '学期',
  course_name  VARCHAR(100)   NOT NULL                COMMENT '课程名称',
  course_code  VARCHAR(30)    DEFAULT NULL            COMMENT '课程代码',
  teacher_name VARCHAR(50)    DEFAULT NULL            COMMENT '任课教师',
  credit       DECIMAL(4,1)   DEFAULT NULL            COMMENT '学分',
  regular_score DECIMAL(5,2)  DEFAULT NULL            COMMENT '平时成绩',
  final_score  DECIMAL(5,2)   DEFAULT NULL            COMMENT '期末成绩',
  total_score  DECIMAL(5,2)   DEFAULT NULL            COMMENT '总成绩',
  gpa          DECIMAL(4,2)   DEFAULT NULL            COMMENT '绩点',
  grade_level  VARCHAR(10)    DEFAULT NULL            COMMENT '成绩等级（A+/B/C-等）',
  exam_type    VARCHAR(20)    DEFAULT '正考'          COMMENT '考试类型：正考/补考/重修',
  record_time  DATETIME       DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间',
  PRIMARY KEY (id),
  KEY idx_user_semester (user_id, semester)
) ENGINE=InnoDB AUTO_INCREMENT=5000 DEFAULT CHARSET=utf8mb4 COMMENT='成绩表';

-- ============================================================
-- 6. 通知公告表 notice
-- ============================================================
DROP TABLE IF EXISTS notice;
CREATE TABLE notice (
  id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  title           VARCHAR(200) NOT NULL                COMMENT '通知标题',
  content         TEXT         NOT NULL                COMMENT '通知内容',
  publisher       VARCHAR(100) DEFAULT NULL            COMMENT '发布部门/发布人',
  type            VARCHAR(30)  DEFAULT '系统公告'      COMMENT '类型：系统公告/教务通知/活动通知/失物招领',
  priority        VARCHAR(10)  DEFAULT '中'            COMMENT '优先级：高/中/低',
  publish_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  effective_time  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生效时间',
  expire_time     DATETIME     DEFAULT NULL            COMMENT '过期时间（NULL表示永久有效）',
  is_top          TINYINT      NOT NULL DEFAULT 0      COMMENT '是否置顶：1是/0否',
  view_count      BIGINT       NOT NULL DEFAULT 0      COMMENT '浏览量',
  attachment_urls TEXT         DEFAULT NULL            COMMENT '附件URL（逗号分隔）',
  creator_id      BIGINT       DEFAULT NULL            COMMENT '发布者用户ID',
  update_time     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY idx_type (type),
  KEY idx_is_top (is_top),
  KEY idx_publish_time (publish_time)
) ENGINE=InnoDB AUTO_INCREMENT=6000 DEFAULT CHARSET=utf8mb4 COMMENT='通知公告表';

-- ============================================================
-- 7. 报修工单表 repair_ticket
-- ============================================================
DROP TABLE IF EXISTS repair_ticket;
CREATE TABLE repair_ticket (
  id                  BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  user_id             BIGINT       NOT NULL                COMMENT '提交报修的用户ID',
  reporter_name       VARCHAR(50)  DEFAULT NULL            COMMENT '报修人姓名',
  contact_phone       VARCHAR(20)  DEFAULT NULL            COMMENT '联系电话',
  repair_location     VARCHAR(200) NOT NULL                COMMENT '报修地点',
  title               VARCHAR(200) NOT NULL                COMMENT '报修标题',
  description         TEXT         DEFAULT NULL            COMMENT '详细描述',
  attachment_urls     TEXT         DEFAULT NULL            COMMENT '附件图片URL（逗号分隔）',
  category            VARCHAR(30)  DEFAULT '其他'          COMMENT '类别：水电/家具/网络/其他',
  status              VARCHAR(20)  NOT NULL DEFAULT 'SUBMITTED' COMMENT '状态: SUBMITTED/ASSIGNED/IN_PROGRESS/COMPLETED/CANCELLED',
  assigned_staff_id   BIGINT       DEFAULT NULL            COMMENT '指派维修员ID',
  assigned_staff_name VARCHAR(50)  DEFAULT NULL            COMMENT '指派维修员姓名',
  completed_time      DATETIME     DEFAULT NULL            COMMENT '完成时间',
  create_time         DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  update_time         DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY idx_user_id (user_id),
  KEY idx_status (status),
  KEY idx_create_time (create_time)
) ENGINE=InnoDB AUTO_INCREMENT=7000 DEFAULT CHARSET=utf8mb4 COMMENT='报修工单表';

-- ============================================================
-- 重新启用外键检查
-- ============================================================
SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================
-- 测试数据
-- ============================================================

-- 测试用户（openid 对应开发模式 mock_openid_xxx）
INSERT INTO sys_user (id, openid, student_id, nickname, role, phone, create_time, update_time) VALUES
(1001, 'mock_openid_test_student', '2021001001', '张三', 'STUDENT', '13800000001', NOW(), NOW()),
(1002, 'mock_openid_test_student2', '2021001002', '李四', 'STUDENT', '13800000002', NOW(), NOW()),
(1003, 'mock_openid_test_teacher', 'T20001', '王老师', 'TEACHER', '13800000003', NOW(), NOW()),
(1004, 'mock_openid_test_admin', 'ADMIN001', '管理员', 'ADMIN', '13800000004', NOW(), NOW());

-- 校园卡数据（对应以上用户）
INSERT INTO campus_card (id, user_id, card_number, balance, status, card_type, activate_time, expire_time, create_time, update_time) VALUES
(2001, 1001, 'SC20210010001', 128.50, 'ACTIVE', 'STUDENT', '2021-09-01 08:00:00', '2025-07-01 00:00:00', NOW(), NOW()),
(2002, 1002, 'SC20210010002', 56.00, 'ACTIVE', 'STUDENT', '2021-09-01 08:00:00', '2025-07-01 00:00:00', NOW(), NOW()),
(2003, 1003, 'TC20010001', 500.00, 'ACTIVE', 'TEACHER', '2020-09-01 08:00:00', '2026-07-01 00:00:00', NOW(), NOW());

-- 交易记录
INSERT INTO card_transaction (card_id, transaction_type, amount, balance_after, description, location, transaction_time, create_time) VALUES
(2001, 'RECHARGE', 100.00, 200.00, '在线充值', '微信支付', DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY)),
(2001, 'CONSUME', 12.50, 187.50, '午餐消费', '第一食堂', DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_SUB(NOW(), INTERVAL 4 DAY)),
(2001, 'CONSUME', 8.00, 179.50, '早餐消费', '第二食堂', DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY)),
(2001, 'CONSUME', 25.00, 154.50, '超市购物', '校内超市', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),
(2001, 'CONSUME', 6.00, 148.50, '打印费用', '图书馆打印', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),
(2001, 'CONSUME', 20.00, 128.50, '晚餐消费', '第一食堂', NOW(), NOW());

-- 课程表数据（用户1001 - 2024-2025-2学期）
INSERT INTO course_schedule (user_id, semester, course_name, teacher_name, location, week_day, start_class, period, week_range, type) VALUES
(1001, '2024-2025-2', '高等数学（下）', '李教授', '教学楼A-301', 1, 1, 2, '1-16', '必修'),
(1001, '2024-2025-2', '大学英语（四）', '陈老师', '教学楼B-201', 1, 3, 2, '1-16', '必修'),
(1001, '2024-2025-2', 'Java程序设计', '王老师', '计算机楼-101', 2, 1, 2, '1-16', '必修'),
(1001, '2024-2025-2', '数据结构', '张教授', '计算机楼-102', 2, 5, 2, '1-16', '必修'),
(1001, '2024-2025-2', '线性代数', '刘老师', '教学楼A-201', 3, 1, 2, '1-16', '必修'),
(1001, '2024-2025-2', '计算机网络', '赵老师', '计算机楼-201', 3, 5, 2, '1-16', '必修'),
(1001, '2024-2025-2', '概率论与数理统计', '孙教授', '教学楼A-302', 4, 1, 2, '1-14', '必修'),
(1001, '2024-2025-2', '软件工程', '钱老师', '计算机楼-203', 4, 5, 2, '1-16', '必修'),
(1001, '2024-2025-2', '体育（篮球）', '吴老师', '体育馆', 5, 3, 2, '1-16', '必修'),
(1001, '2024-2025-2', '形势与政策', '郑老师', '教学楼C-101', 5, 7, 1, '1-8', '必修');

-- 成绩数据（用户1001）
INSERT INTO grade (user_id, semester, course_name, course_code, teacher_name, credit, regular_score, final_score, total_score, gpa, grade_level, exam_type, record_time) VALUES
(1001, '2024-2025-1', '高等数学（上）', 'MATH101', '李教授', 5.0, 88.0, 85.0, 86.0, 3.7, 'A-', '正考', DATE_SUB(NOW(), INTERVAL 60 DAY)),
(1001, '2024-2025-1', '大学英语（三）', 'ENG103', '陈老师', 3.0, 92.0, 88.0, 89.6, 4.0, 'A', '正考', DATE_SUB(NOW(), INTERVAL 60 DAY)),
(1001, '2024-2025-1', '程序设计基础', 'CS101', '王老师', 4.0, 95.0, 90.0, 92.0, 4.0, 'A', '正考', DATE_SUB(NOW(), INTERVAL 60 DAY)),
(1001, '2024-2025-1', '离散数学', 'MATH201', '张教授', 3.0, 78.0, 72.0, 74.4, 3.0, 'B', '正考', DATE_SUB(NOW(), INTERVAL 60 DAY)),
(1001, '2024-2025-1', '大学物理', 'PHY101', '孙教授', 4.0, 82.0, 75.0, 77.8, 3.3, 'B+', '正考', DATE_SUB(NOW(), INTERVAL 60 DAY)),
(1001, '2024-2025-1', '马克思主义原理', 'POL101', '郑老师', 2.0, 88.0, 84.0, 85.6, 3.7, 'A-', '正考', DATE_SUB(NOW(), INTERVAL 60 DAY)),
(1001, '2023-2024-2', '高等数学导论', 'MATH001', '李教授', 4.0, 90.0, 86.0, 87.6, 4.0, 'A', '正考', DATE_SUB(NOW(), INTERVAL 200 DAY)),
(1001, '2023-2024-2', '大学英语（二）', 'ENG102', '陈老师', 3.0, 85.0, 80.0, 82.0, 3.5, 'B+', '正考', DATE_SUB(NOW(), INTERVAL 200 DAY)),
(1001, '2023-2024-2', 'C语言程序设计', 'CS001', '钱老师', 4.0, 92.0, 88.0, 89.6, 4.0, 'A', '正考', DATE_SUB(NOW(), INTERVAL 200 DAY));

-- 通知公告数据
INSERT INTO notice (title, content, publisher, type, priority, publish_time, effective_time, expire_time, is_top, view_count, creator_id) VALUES
('【重要】关于2025年春季学期选课通知', '各位同学，2025年春季学期选课将于2025年2月10日开始，请登录教务系统进行选课操作。选课分两轮进行，第一轮：2025年2月10日-14日；第二轮：2025年2月16日-20日。请同学们合理安排时间，按时完成选课。如有疑问，请联系教务处。', '教务处', '教务通知', '高', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 30 DAY), 1, 328, 1004),
('2025年五四青年节校园文化节活动预告', '为庆祝五四青年节，学校将于2025年5月4日举办校园文化节活动，包括文艺演出、社团展览、创新创业大赛等精彩内容。欢迎全体同学积极参与！活动地点：学校大操场及各学院楼。详细安排请关注学校官方公众号。', '学生工作处', '活动通知', '中', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 7 DAY), 0, 156, 1004),
('图书馆春季开放时间调整通知', '自2025年3月1日起，图书馆开放时间调整为：周一至周五 07:30-22:00，周六周日 08:00-21:00。地下自习室24小时开放（凭校园卡刷卡进入）。寒假期间开放时间另行通知。', '图书馆', '系统公告', '低', DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY), NULL, 0, 89, 1004),
('关于期末考试安排的通知', '2024-2025学年第二学期期末考试将于2025年6月23日开始，请同学们提前做好复习准备。考试时间表将在6月1日前公布。缺考处理规定：无故缺考按0分处理，如有特殊情况请提前向辅导员申请。', '教务处', '教务通知', '高', NOW(), NOW(), DATE_ADD(NOW(), INTERVAL 90 DAY), 1, 512, 1004),
('【失物招领】图书馆一楼发现学生证一张', '在图书馆一楼B区阅览室发现一张学生证，请失主携带本人证件前往图书馆服务台认领。联系时间：周一至周五 9:00-17:00。物品将保存至2025年5月30日，逾期无人认领将上交学校保卫处。', '图书馆', '失物招领', '低', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 14 DAY), 0, 45, 1004),
('校园网升级维护通知', '学校计划于2025年4月30日 00:00-06:00 对校园网核心设备进行升级维护，届时校园网将短暂中断。请同学们提前做好工作保存，避免数据丢失。维护完成后网络速度将大幅提升。给同学们带来的不便，敬请谅解。', '网络信息中心', '系统公告', '中', DATE_SUB(NOW(), INTERVAL 12 HOUR), DATE_SUB(NOW(), INTERVAL 12 HOUR), DATE_ADD(NOW(), INTERVAL 5 DAY), 0, 234, 1004);

-- 报修工单数据
INSERT INTO repair_ticket (user_id, reporter_name, contact_phone, repair_location, title, description, category, status, assigned_staff_name, completed_time, create_time, update_time) VALUES
(1001, '张三', '13800000001', '3号宿舍楼 306室', '宿舍热水器故障', '宿舍热水器无法正常加热，已使用冷水3天，请尽快维修。', '水电', 'COMPLETED', '李维修员', DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY)),
(1001, '张三', '13800000001', '计算机楼 101教室', '教室投影仪无法连接', '第5、6节课使用的计算机楼101教室投影仪无法通过HDMI连接笔记本电脑，影响上课。', '其他', 'IN_PROGRESS', '赵维修员', NULL, DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),
(1001, '张三', '13800000001', '3号宿舍楼 306室', '宿舍网络断网', '宿舍306室网络从昨晚开始无法连接，重启路由器后依然无效。影响学习。', '网络', 'SUBMITTED', NULL, NULL, DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY));
