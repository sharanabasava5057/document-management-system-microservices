CREATE DATABASE IF NOT EXISTS dms;
USE dms;

CREATE TABLE IF NOT EXISTS documents (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    file_name VARCHAR(255) NOT NULL,
    file_size BIGINT,
    upload_date DATETIME,
    uploaded_by VARCHAR(255)
);

CREATE USER IF NOT EXISTS 'dms_user'@'%' IDENTIFIED BY 'dms_password';
GRANT ALL PRIVILEGES ON dms.* TO 'dms_user'@'%';
FLUSH PRIVILEGES;
