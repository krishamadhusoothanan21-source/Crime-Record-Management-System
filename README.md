# Crime Record Management System

Java + MySQL desktop project for managing crime records.

## Features
- Admin login
- Dashboard statistics
- Add crime record
- View all records
- Search records
- Update records
- Delete records
- MySQL database using JDBC
- PreparedStatement for database operations

## Requirements
- JDK 17 or newer
- MySQL 8+
- Maven 3.9+
- IntelliJ IDEA, Eclipse, or NetBeans

## Setup
1. Start MySQL.
2. Execute `database.sql` in MySQL Workbench.
3. Open `src/main/java/com/crimerecord/DBConnection.java`.
4. Replace `YOUR_MYSQL_PASSWORD` with your MySQL password.
5. Open the project as a Maven project.
6. Run `mvn clean compile`.
7. Run `mvn exec:java`.

## Default login
Username: admin
Password: admin123

## Main project modules
Login -> Dashboard -> Crime CRUD -> Search -> Database

For a production system, passwords should be securely hashed and role-based authorization should be strengthened.
