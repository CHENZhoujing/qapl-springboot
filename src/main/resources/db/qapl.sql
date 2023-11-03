CREATE DATABASE IF NOT EXISTS qapl;
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
       qt.question_type_id AS question_type_id,
       qt.type_name        AS question_type_name,
       q.question_id       AS question_id,
       q.question_content  AS question_content,
       q.create_time       AS question_create_time,
       q.update_time       AS question_update_time,
       q.answer            AS question_answer,
       q.answer_time       AS question_answer_time
FROM user AS u
         INNER JOIN
     question AS q ON u.user_id = q.user_id
         INNER JOIN
     question_type AS qt ON q.question_type_id = qt.question_type_id;


INSERT INTO user (username, password, email, phone, is_admin)
VALUES ('user1', 'password1', 'user1@example.com', '123-456-7890', FALSE),
       ('user2', 'password2', 'user2@example.com', '987-654-3210', FALSE),
       ('admin', 'admin', 'user3@example.com', '555-555-5555', TRUE);

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


INSERT INTO question (question_content, question_type_id, user_id)
VALUES ('Question 1?', 1, 1),
       ('Question 2?', 2, 1),
       ('Question 3?', 3, 2);
