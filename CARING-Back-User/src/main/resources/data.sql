---- 테이블이 존재하지 않을 경우 생성
--CREATE TABLE IF NOT EXISTS authority (
--    authority_id BIGINT PRIMARY KEY,
--    manager_role VARCHAR(255) UNIQUE NOT NULL,
--    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
--);
--
---- 데이터 삽입 (중복 방지)
--INSERT INTO authority (authority_id, manager_role, created_date, last_modified_date)
--VALUES
--    (1, 'ROLE_MANAGE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--    (2, 'ROLE_SUPER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
--ON DUPLICATE KEY UPDATE
--    manager_role = VALUES(manager_role);
