-- 사용자 및 권한 설정
CREATE USER IF NOT EXISTS 'root'@'localhost' IDENTIFIED BY 'springproject';
CREATE USER IF NOT EXISTS 'root'@'%' IDENTIFIED BY 'springproject';
CREATE USER IF NOT EXISTS 'root'@'172.18.0.8' IDENTIFIED BY 'springproject';

GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'172.18.0.8';
FLUSH PRIVILEGES;

-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS caring_user CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS caring_manager CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- caring_manager DB 구조 정의
USE caring_manager;

CREATE TABLE authority (
    authority_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_date DATETIME(6),
    last_modified_date DATETIME(6),
    manager_role VARCHAR(20),
    CONSTRAINT unique_manager_role UNIQUE (manager_role)
);

CREATE TABLE manager (
    manager_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_date DATETIME(6),
    last_modified_date DATETIME(6),
    manager_uuid VARCHAR(255),
    member_code VARCHAR(255),
    name VARCHAR(255),
    password VARCHAR(255),
    shelter_uuid VARCHAR(255),
    CONSTRAINT unique_manager_uuid UNIQUE (manager_uuid),
    CONSTRAINT unique_member_code UNIQUE (member_code)
);

CREATE TABLE manager_authority (
    manager_authority_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    authority_id BIGINT,
    manager_id BIGINT,
    CONSTRAINT fk_manager_authority_authority FOREIGN KEY (authority_id) REFERENCES authority(authority_id),
    CONSTRAINT fk_manager_authority_manager FOREIGN KEY (manager_id) REFERENCES manager(manager_id)
);

CREATE TABLE manager_group (
    manager_group_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_date DATETIME(6),
    last_modified_date DATETIME(6),
    user_uuid VARCHAR(255),
    manager_id BIGINT,
    CONSTRAINT fk_manager_group_manager FOREIGN KEY (manager_id) REFERENCES manager(manager_id)
);

CREATE TABLE shelter (
    shelter_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_date DATETIME(6),
    last_modified_date DATETIME(6),
    location VARCHAR(255),
    name VARCHAR(255),
    shelter_uuid VARCHAR(255),
    CONSTRAINT unique_shelter_uuid UNIQUE (shelter_uuid)
);

CREATE TABLE shelter_user (
    shelter_user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_date DATETIME(6),
    last_modified_date DATETIME(6),
    shelter_uuid VARCHAR(255),
    user_uuid VARCHAR(255)
);

CREATE TABLE submission (
    submission_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_date DATETIME(6),
    last_modified_date DATETIME(6),
    name VARCHAR(255),
    password VARCHAR(255),
    shelter_uuid VARCHAR(255),
    status ENUM ('APPLY', 'PERMIT', 'REJECTED'),
    submission_uuid VARCHAR(255),
    CONSTRAINT unique_submission_uuid UNIQUE (submission_uuid)
);

-- caring_user DB 구조 정의
USE caring_user;

CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_date DATETIME(6),
    last_modified_date DATETIME(6),
    member_code VARCHAR(255),
    name VARCHAR(255),
    password VARCHAR(255),
    role ENUM ('NOT_ALLOWED', 'USER'),
    shelter_uuid VARCHAR(255),
    user_uuid VARCHAR(255),
    CONSTRAINT unique_user_uuid UNIQUE (user_uuid),
    CONSTRAINT unique_user_member_code UNIQUE (member_code)
);
