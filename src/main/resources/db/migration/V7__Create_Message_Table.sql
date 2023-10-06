CREATE TABLE message
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    message VARCHAR(255) NOT NULL DEFAULT '',
    type    INT          NOT NUll DEFAULT 0,
    conversation_id INT NOT NULL REFERENCES conversation (id)
)