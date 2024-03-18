# StudentDBManager
This program serves as a basic CRUD (Create, Read, Update, Delete) application for managing student records in a PostgreSQL database.

Name: Ryan Johnson
Student #: 101217600
Youtube Link: https://youtu.be/d6rAcfF0rEc
Source File: StudentDatabase.java
Other Files: README.txt, pom.xml
Setup Instructions: Setup your postgresql Database and gather your Database Host Name/Address, Port, Username, and Password.
                    Input all information into the url, user, and password variables in StudentDatabase.java.
                    Create the intial table and insert the inital data into your postgresql Database via the SQL below:
                    
CREATE TABLE IF NOT EXISTS students (
    student_id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL,
    enrollment_date DATE
);

INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES
('John', 'Doe', 'john.doe@example.com', '2023-09-01'),
('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),
('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');

Compile & Run Instructions: Compile and run via your IDE Software.  
