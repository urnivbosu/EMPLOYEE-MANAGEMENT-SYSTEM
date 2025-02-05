# **Thapasya Infopark Employee Management System**  

## **Overview**  
The Thapasya Infopark Employee Management System is a **Spring Boot REST API** designed to manage employees, managers, and projects across multiple companies. The system provides a structured way to handle authentication, role-based access control, and CRUD operations for employees and projects. The three companies included in this project are **Verteil, IBS, and TCS**. The application ensures secure access using **JWT authentication** and provides functionalities for **managers to assign employees to projects**.  

## **Technology Stack**  
The project is built using **Spring Boot** for the backend, with **Spring Security and JWT** for authentication and authorization. **PostgreSQL** is used as the database, and **Spring Data JPA** handles the database operations. The project is managed using **Maven**, and API interactions are tested using **Postman**.  

## **Project Structure**  
The project follows a layered architecture, including controllers for handling API requests, services for business logic, repositories for database interactions, and models for defining entities. Additionally, there are configurations for **security and JWT authentication** to ensure only authorized users can access the API.  

## **Features and Functionalities**  
The system supports **user authentication and authorization** using **JWT tokens**. New users can register, and existing users can log in to obtain a **JWT token**. The application follows **role-based access control (RBAC)**, where employees and managers have different permissions. Managers can **assign employees to projects** and perform **CRUD operations on projects**, while employees can view their assigned projects.  

The **employee management** module provides functionalities to **create, update, fetch, and delete employees**. Employees belong to a company and may report to a manager. The **project management** module allows managers to create and assign employees to projects based on their company.  

## **Setup and Installation**  
To run the project, ensure you have **Java 17+, PostgreSQL, Maven, and Postman** installed. Clone the repository and configure the database by updating the **application.properties** file with your database credentials. Once the configuration is complete, run the project using Maven.  

## **API Endpoints**  
The application provides **authentication APIs** for user registration and login, **employee management APIs** for handling employee records, and **project management APIs** for managing projects and employee assignments. Authentication is handled using **JWT tokens**, which must be included in API requests for secure access.  

## **JWT Authentication Flow**  
When a user logs in, the server generates a **JWT token**, which is returned to the client. The client must store this token and include it in the `Authorization` header when making API requests. **Spring Security** validates the token and grants access to protected resources based on the user's role.  

## **Future Enhancements**  
Future improvements to the system may include a **password reset feature, a web-based employee dashboard**, and **logging mechanisms** for monitoring system activity. These enhancements would improve usability and security while making the system more efficient.  

## **Conclusion**  
The **Thapasya Infopark Employee Management System** provides a **secure and scalable** solution for managing employees, managers, and projects. The use of **Spring Boot, JWT authentication, and PostgreSQL** ensures reliability, while the **RBAC model** enforces proper access control. This system serves as a foundation for a fully functional employee management platform with room for future enhancements.
