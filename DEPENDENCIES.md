# Dependencies — Hospital Appointment Booking & Management System

## Backend (Maven — `backend/pom.xml`)

| Dependency | Version | Purpose |
|---|---|---|
| spring-boot-starter-parent | 3.2.5 | Base Spring Boot parent POM |
| spring-boot-starter-web | (managed) | REST controllers, embedded Tomcat |
| spring-boot-starter-data-jpa | (managed) | Hibernate/JPA for MySQL persistence |
| spring-boot-starter-security | (managed) | Role-based auth (PATIENT/DOCTOR/ADMIN), filter chain |
| spring-boot-starter-validation | (managed) | Request validation |
| mysql-connector-j | (managed) | JDBC driver for MySQL |
| jjwt-api / jjwt-impl / jjwt-jackson | 0.11.5 | JWT creation & parsing (with role claim) |
| lombok | (managed) | Boilerplate reduction |
| spring-boot-starter-test | (managed) | Testing |

Java version: **17**. Build tool: **Maven**.

## Frontend (npm — `frontend/package.json`)

| Dependency | Version | Purpose |
|---|---|---|
| react / react-dom | ^18.2.0 | UI library |
| react-router-dom | ^6.23.0 | Routing |
| axios | ^1.6.8 | HTTP client with JWT interceptor |
| react-scripts | 5.0.1 | CRA build tooling |

Run with `npm install && npm start`.
