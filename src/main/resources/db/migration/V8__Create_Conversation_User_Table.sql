CREATE TABLE conversation_user(
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY ,
    user_id INT NOT NULL REFERENCES users(id),
    conversation_id INT NOT NULL REFERENCES conversation (id)
)