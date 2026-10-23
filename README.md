# Fountain Verify Java

**A Certificate Verification System Built with Spring Boot**

Fountain Verify Java is a web-based certificate management and verification application developed as a Fountain Information Service project. It demonstrates practical Java backend development, database integration, web application security, and QR-code-based certificate verification.

## Project Overview

The application provides certificate management and verification features through a web interface built with Spring Boot, Thymeleaf, and MySQL.

The project demonstrates how a database-backed web application can organize certificate records, manage user access, and support certificate verification workflows.

## Key Features

- **Certificate Management:** Create, view, edit, and manage certificate records.
- **Certificate Verification:** Provide a verification page for checking certificate information.
- **QR Code Generation:** Generate QR codes associated with certificates to support verification workflows.
- **User Authentication:** Support user registration, login, and logout.
- **User Dashboard:** Provide dashboard and certificate-related pages.
- **Profile Management:** Allow users to view and manage profile information.
- **Password Management:** Provide password-related management features.
- **Database Integration:** Use Spring Data JPA repositories and services to organize certificate and user data.

## Technologies Used

- Java 25
- Spring Boot 4.1.1
- Spring Security
- Spring Data JPA
- MySQL
- Maven
- Thymeleaf
- HTML and CSS
- ZXing for QR code generation

## Project Structure

The application follows a layered structure:

- `controller` — handles web requests.
- `service` — contains application business logic.
- `repository` — provides database access.
- `entity` — defines database entities.
- `dto` — contains data-transfer objects.
- `config` — contains application configuration, including security.
- `src/main/resources/templates` — contains Thymeleaf templates.
- `src/main/resources/static` — contains static assets.
- `src/test` — contains application test code.

## Requirements

To run the project locally, you will need:

- Java 25 JDK.
- MySQL Server.
- Git.
- Spring Tool Suite or another compatible Java IDE, or a terminal for Maven commands.

## Running the Project Locally

### 1. Clone the Repository

```bash
git clone https://github.com/davidmalik2019/fountainverify-java.git
```

### 2. Open the Project

Open the cloned project in Spring Tool Suite or another compatible Java IDE.

### 3. Configure the Database

Create a MySQL database named `fountainverify`, or configure the application to use your own database.

Configure the database connection and other required settings in your local Spring Boot configuration. Use your own database credentials and keep private configuration files out of GitHub.

### 4. Run the Application

On Windows:

```cmd
mvnw.cmd spring-boot:run
```

On Linux or macOS:

```bash
./mvnw spring-boot:run
```

You can also run the application from your IDE.

### 5. Open the Application

Once the application has started, open:

`http://localhost:8080`

The available pages depend on the application's configured routes and access permissions.

## Testing

The project includes automated tests using the Spring Boot testing framework.

Ensure MySQL is running and the local database configuration is correct before running the tests.

On Windows, run:

```cmd
mvnw.cmd test
```

To perform a clean build and package the application:

```cmd
mvnw.cmd clean package
```

**Local verification:** The automated test command and clean package build completed successfully in the development environment with MySQL running.

## Security

- Do not commit database passwords, secret keys, or other private credentials.
- Keep environment-specific configuration outside the public repository.
- Use appropriate credentials and database permissions for each environment.
- Review authentication, authorization, and database configuration before deploying the application to a public server.

## Developer Portfolio

Fountain Verify Java is part of my Java backend development portfolio. It demonstrates practical experience with Java, Spring Boot, Spring Security, database-backed applications, user authentication, and certificate-verification workflows.

## Repository

GitHub: https://github.com/davidmalik2019/fountainverify-java

---

*Developed as a Fountain Information Service project.*