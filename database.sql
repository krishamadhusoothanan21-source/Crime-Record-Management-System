CREATE DATABASE IF NOT EXISTS crime_management;
USE crime_management;

CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(30) NOT NULL DEFAULT 'ADMIN'
);

CREATE TABLE IF NOT EXISTS crimes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    case_number VARCHAR(30) NOT NULL UNIQUE,
    crime_type VARCHAR(80) NOT NULL,
    victim_name VARCHAR(100) NOT NULL,
    suspect_name VARCHAR(100),
    location VARCHAR(200) NOT NULL,
    crime_date DATE NOT NULL,
    description TEXT,
    status VARCHAR(30) NOT NULL DEFAULT 'Open',
    officer_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (username, password, role)
SELECT 'admin', 'admin123', 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username='admin');

INSERT INTO crimes
(case_number, crime_type, victim_name, suspect_name, location, crime_date, description, status, officer_name)
SELECT 'CR-1001', 'Theft', 'Sample Victim', 'Unknown', 'Chennai Central', '2026-09-10',
       'Sample record. Replace or delete this record.', 'Open', 'Inspector Kumar'
WHERE NOT EXISTS (SELECT 1 FROM crimes WHERE case_number='CR-1001');
