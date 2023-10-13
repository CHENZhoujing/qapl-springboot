CREATE DATABASE IF NOT EXISTS qapl;
USE qapl;

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
    question_name    VARCHAR(255) NOT NULL,
    question_content TEXT         NOT NULL,
    question_type_id INT          NOT NULL,
    user_id          INT          NOT NULL,
    create_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_deleted       BOOLEAN   DEFAULT FALSE,
    FOREIGN KEY (question_type_id) REFERENCES question_type (question_type_id)
);

INSERT INTO user (username, password, email, phone, is_admin)
VALUES ('user1', 'password1', 'user1@example.com', '123-456-7890', FALSE),
       ('user2', 'password2', 'user2@example.com', '987-654-3210', FALSE),
       ('admin', 'admin', 'user3@example.com', '555-555-5555', TRUE);

INSERT INTO question_type (type_name)
VALUES ('Multiple Choice'),
       ('True/False'),
       ('Short Answer');

INSERT INTO question (question_name, question_content, question_type_id, user_id)
VALUES ('Question 1', 'What is the capital of France?', 1, 1),
       ('Question 2', 'Is the Earth round?', 2, 1),
       ('Question 3', 'Solve for x: 2x + 3 = 7', 3, 2);
