CREATE TABLE report_photo
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    report_id  INT          NOT NULL REFERENCES report (id),
    photo_name VARCHAR(255) NOT NULL
);
