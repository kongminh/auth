-- V1__Create_User_Table.sql
CREATE TABLE users (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(255) NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      role VARCHAR(50) NOT NULL,
                      permissions VARCHAR(255) NOT NULL,
                      last_login TIMESTAMP
);
