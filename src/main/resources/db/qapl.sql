CREATE DATABASE IF NOT EXISTS qapl
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE qapl;

DROP VIEW IF EXISTS qapl_combined;

DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS question_type;
DROP TABLE IF EXISTS user;


CREATE TABLE IF NOT EXISTS user
(
    user_id     INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    email       VARCHAR(255),
    phone       VARCHAR(20),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_admin    BOOLEAN   DEFAULT FALSE,
    is_deleted  BOOLEAN   DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS question_type
(
    question_type_id INT AUTO_INCREMENT PRIMARY KEY,
    type_name        VARCHAR(255) NOT NULL,
    is_deleted       BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS question
(
    question_id      INT AUTO_INCREMENT PRIMARY KEY,
    question_title   TEXT NOT NULL,
    question_content TEXT NOT NULL,
    question_type_id INT  NOT NULL,
    user_id          INT  NOT NULL,
    create_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    answer           TEXT,
    answer_time      TIMESTAMP,
    is_deleted       BOOLEAN   DEFAULT FALSE,
    FOREIGN KEY (question_type_id) REFERENCES question_type (question_type_id)
);

CREATE VIEW qapl_combined AS
SELECT u.user_id           AS user_id,
       u.username          AS username,
       u.email             AS user_email,
       u.phone             AS user_phone,
       u.create_time       AS user_create_time,
       u.is_admin          AS user_is_admin,
       u.is_deleted        AS user_is_deleted,
       qt.question_type_id AS question_type_id,
       qt.type_name        AS question_type_name,
       q.question_id       AS question_id,
       q.question_title    AS question_title,
       q.question_content  AS question_content,
       q.create_time       AS question_create_time,
       q.update_time       AS question_update_time,
       q.answer            AS question_answer,
       q.answer_time       AS question_answer_time,
       q.is_deleted        AS question_is_deleted
FROM user AS u
         INNER JOIN
     question AS q ON u.user_id = q.user_id
         INNER JOIN
     question_type AS qt ON q.question_type_id = qt.question_type_id;


INSERT INTO user (username, password, email, phone, is_admin)
VALUES
    ('admin001', 'adminpwd', 'admin001@example.com', '134-0000-0000', TRUE),
    ('zhangsan', 'password123', 'zhangsan@example.com', '131-0000-0000', FALSE),
    ('lisi', 'password456', 'lisi@example.com', '132-0000-0000', FALSE),
    ('wangwu', 'password789', 'wangwu@example.com', '133-0000-0000', FALSE);



INSERT INTO question_type (type_name, is_deleted)
VALUES ('缺证书', FALSE),
       ('税号失效', FALSE),
       ('未激活', FALSE),
       ('公司注册', FALSE),
       ('季报月报调整', FALSE),
       ('修改公司名', FALSE),
       ('注销', FALSE),
       ('重启', FALSE),
       ('修改生效日期', FALSE),
       ('其他', FALSE);


INSERT INTO question (question_title, question_content, question_type_id, user_id)
VALUES
    ('公司注册流程是怎样的？', '想了解公司注册的具体流程和所需材料。', 4, 2),
    ('如何调整季报和月报？', '请问季报和月报的调整流程是怎样的？有哪些注意事项？', 5, 2),
    ('公司名称更改的步骤', '我们公司计划更改名称，应该怎样操作？', 6, 2),
    ('公司账户未激活的解决办法', '公司账户未激活，我们应该怎么处理？', 3, 2),
    ('公司注销流程及注意事项', '请问公司注销的流程是什么？有哪些法律事项需要注意？', 7, 3),
    ('公司重启的条件和流程', '公司若要重启，需要满足哪些条件？流程是怎样的？', 8, 3),
    ('修改公司生效日期的步骤', '怎样修改公司法律文件中的生效日期？', 9, 4),
    ('缺少必要证书的处理方法', '如果发现公司缺少某些必要的经营证书，该如何补办？', 1, 4),
    ('应对税号失效的措施', '公司的税号失效了，我们应该怎么处理？', 2, 4);
