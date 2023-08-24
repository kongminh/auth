-- V2__Add_Created_At_and_Updated_At.sql

-- Add 'created_at' and 'updated_at' columns to the 'user' table
ALTER TABLE user
    ADD created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
