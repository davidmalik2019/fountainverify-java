# Fountain Verify Java

**A Certificate Verification System Built with Spring Boot**

Fountain Verify Java is a web-based application developed to help manage certificates and support certificate verification. The project demonstrates Java backend development, database integration, web application security, and QR-code-based verification.

## Project Overview

The application provides certificate management and user-facing features through a web interface built with Spring Boot.

## Key Features

- **Certificate Management:** Create, view, edit, and manage certificate records.
- **Certificate Verification:** Provide a verification page for checking certificate information.
- **QR Code Functionality:** Generate and manage QR codes associated with certificates.
- **User Authentication:** Support user registration, login, and logout.
- **User Dashboard:** Provide dashboard and certificate-related pages.
- **Profile Management:** Allow users to view and manage profile information.
- **Password Management:** Include password-change and password-reset functionality.
- **Database Integration:** Use repositories and services to organize certificate and user data.

## Technologies Used

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- Maven
- Thymeleaf
- HTML and CSS
- QR code generation

## Project Structure

The application follows a layered structure:

- `controller` — handles web requests.
- `service` — contains application business logic.
- `repository` — provides database access.
- `entity` — defines database entities.
- `dto` — contains data-transfer objects.
- `config` — contains application security configuration.
- `src/main/resources/templates` — contains Thymeleaf HTML templates.
- `src/main/resources/static` — contains static assets.
- `src/test` — contains application test code.

## Requirements

To run the project locally, you will need:

- A compatible Java JDK.
- MySQL Server.
- Git.
- An IDE such as Spring Tool Suite, or a terminal for Maven commands.

## Running the Project Locally

1. Clone the repository:

   ```bash
   git clone https://github.com/davidmalik2019/fountainverify-java.git
   ```

2. Open the project in Spring Tool Suite or another compatible Java IDE.

3. Create a MySQL database named `fountainverify`, or configure the application to use your own database.

4. Configure the database connection and other required settings in your local Spring Boot configuration. Use your own database credentials and keep private credentials out of GitHub.

5. Run the application using your IDE or the Maven wrapper:

   **Windows:**

   ```cmd
   mvnw.cmd spring-boot:run
   ```

   **Linux/macOS:**

   ```bash
   ./mvnw spring-boot:run
   ```

6. Open the local application at:

   `http://localhost:8080`

   The exact pages available depend on the application's configured routes.

## Security

- Do not commit database passwords, secret keys, or other private credentials.
- Configure environment-specific settings locally or through your hosting provider.
- Review security settings before deploying the application to a public server.

## Developer Portfolio

This project is part of my Java backend development portfolio, demonstrating practical work with Java, Spring Boot, database-backed applications, authentication, and certificate-verification workflows.

## Repository

GitHub: https://github.com/davidmalik2019/fountainverify-java

---

*Developed as a Fountain Information Service project.*