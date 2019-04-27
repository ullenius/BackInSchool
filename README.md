# Back In School
:school: School Management project. Java EE, JPA and MySQL/mariaDB. Contains 5 entities with bi-directional relationships

This is a school project (duration q4 2018-jan 2019). We were instructed to make a School Management System using SQL, JPA, RDBMS (relational database management system) and Java.
             

Please note that this is unfortunately **not** a Java EE project.
               
As it stands you are required to add external 
dependencies manually (Java EE, EclipseLink). However, a demo Meta-inf 
persistence file is included.
                  
## Requirements
The School management system consists of:

1. Students
1. Teachers
1. Courses
1. Educations

- Each teacher has a group of courses that they are responsible for.
- Each course consists of a list of students and teachers associated with it.
- Each student can register for an education.
- Each education consists of several courses.

### The system should support
1. Registering students
1. Setting up new courses, educations and teachers
1. Suitable crud should be provided for **each** entity.

## Technologies Used
- Java 8
- MySQL server
- Java Persistence API (JPA)
- Object Relational Mapping (ORM)
- EclipseLink
- NetBeans IDE
- DAO-pattern (data access object)
- Git
