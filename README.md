# 🚂 Indian Railway Crossing Management System

A full-stack Spring Boot web application designed to digitize and manage railway crossing operations. This system provides a secure portal for Gatemen to punch into their scheduled shifts, operate crossing gates, and log real-time train movements, while allowing administrators to monitor schedules and track crossing activity.

## ✨ Key Features

### 👮 Gateman Portal
* **Shift Management:** Gatemen can view their upcoming schedules and securely "Punch In" to activate their shift.
* **Smart Dashboard:** The dashboard dynamically switches views. It hides the punch-in option once a shift starts and reveals real-time gate controls.
* **Real-time Logging:** Active Gatemen can enter train numbers and log precise gate closing and opening times.
* **Security:** Prevents Gatemen from punching into shifts 24 hours early and restricts logging access exclusively to the active, on-duty Gateman.

### 🏢 Administrative & Core Features
* **Role-Based Access Control:** Distinct roles and routing for ROLE_ADMIN and ROLE_GATEMAN secured by Spring Security.
* **Crossing Dashboards:** View specific crossing details, expected train schedules, and today's real-time activity logs.
* **RESTful Architecture:** Dynamic dashboard updates via asynchronous Fetch API calls mapping to backend Data Transfer Objects (DTOs).

## 🛠️ Tech Stack

* **Backend:** Java, Spring Boot, Spring Security, Spring Data JPA, Hibernate
* **Frontend:** Thymeleaf, HTML5, CSS3, Vanilla JavaScript (Fetch API)
* **Database:** MySQL
* **Tools:** Maven, Lombok, ModelMapper

## 🗄️ Database Table Mappings (Entity Relationships)

The application relies on a highly relational MySQL database architecture managed by Hibernate/JPA:

* **user**: Stores both Admins and Gatemen. 
  * Relationships: One-to-Many with duty_assign and daily_crossing_logs_real_time.
* **railway_crossing**: Represents physical crossing locations (address, pincode, status).
  * Relationships: One-to-Many with duty_assign, crossing_log, and daily_crossing_logs_real_time.
* **train**: Stores train identities (train_number as primary key, train_name).
  * Relationships: One-to-Many with crossing_log and daily_crossing_logs_real_time.
* **duty_assign**: Tracks Gateman shifts.
  * Relationships: Many-to-One mapping linking a specific user (Gateman) to a railway_crossing for a specific date and shift. Tracks UPCOMING, ONGOING, or COMPLETED statuses.
* **crossing_log** (Schedules): Stores the expected schedule for a train passing a crossing.
  * Relationships: Many-to-One with train and railway_crossing. 
  * Collections: Uses @ElementCollection to map active days of the week into a separate join table called crossing_log_days.
* **daily_crossing_logs_real_time**: The transactional ledger of actual gate operations.
  * Relationships: Many-to-One mapping to user, railway_crossing, and train. Stores precise closedFrom and closedTo timestamps.

## 🚀 Installation & Database Configuration

Follow these steps to configure the database and run the application on your local machine:

**1. Clone the repository**

    git clone https://github.com/Impawanyadav/RailwayCrossingManagement.git
    cd RailwayCrossingManagement

**2. Configure the MySQL Database**

* Open your MySQL Workbench or CLI and create a new database:

    CREATE DATABASE railway_db;

* Open the src/main/resources/application.properties file in the project.
* Update the database URL, username, and password to match your local MySQL setup:

    spring.datasource.url=jdbc:mysql://localhost:3306/railway_db?createDatabaseIfNotExist=true
    spring.datasource.username=root
    spring.datasource.password=your_mysql_password
    spring.jpa.hibernate.ddl-auto=update

**3. Run the Application**

Run the application using Maven:

    mvn spring-boot:run

**4. Access the Web Portal**

Open your web browser and navigate to:

    http://localhost:8080

## 🔑 Default Login Credentials

Upon the first launch, the application automatically seeds the database. Use the following credentials to log in:

**System Administrator:**
* Email: admin@railway.in
* Password: admin

**Gateman Accounts:**
*(There are 50 Gateman accounts. Replace 'X' with any number from 2 to 51)*
* Email: gatemanX@railway.com (Example: gateman2@railway.com)
* Password: X (Example: 2)

---
Developed as a modern solution for digitizing manual railway crossing logs.
