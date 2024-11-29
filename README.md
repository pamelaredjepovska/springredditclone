# Reddit Clone

A simple Reddit clone built using **Spring Boot**, featuring secure user authentication, a RESTful API, and database integration. The application demonstrates the use of **Spring Security with JPA Authentication**, **Spring Data JPA with MySQL**, and **Spring MVC** to create a robust backend for a social platform.

---

## Features

- **User Authentication:** Sign up, log in, and account verification via email.
- **Token-based Security:** Secure access using JWT tokens and refresh tokens.
- **Post Management:** Create, read, and interact with posts.
- **Subreddit Management:** Create and manage subreddits to organize posts.
- **Commenting:** Engage with posts via a comments section.
- **Vote System:** Upvote or downvote posts and comments.
- **Database Integration:** Persist data using MySQL with Spring Data JPA.
- **RESTful API:** Well-structured endpoints for integration with frontend applications.
- **Modular Design:** Clean architecture with services, repositories, and DTOs.

---

## Tech Stack

### Frameworks & Libraries
- **Spring Boot 3.3.5**: Core framework for building the application.
- **Spring Security**: Implements secure authentication and authorization.
- **Spring Data JPA**: Simplifies database access with a repository-based approach.
- **Spring MVC**: Implements RESTful endpoints.
- **Spring Mail**: Handles email-based account verification.
- **MapStruct**: Java bean mapping for DTOs and entities.

### Database
- **MySQL**: Relational database for data persistence.

### Utilities
- **Lombok**: Reduces boilerplate code in models and services.
- **Jakarta Validation**: Ensures input validation in DTOs.
- **TimeAgo**: Formats date-time into user-friendly strings.

### Testing
- **JUnit**: Unit testing framework.
- **Spring Security Test**: Tools for security testing.

---

## Prerequisites

1. **Java 17** or higher
2. **Maven**: For dependency management.
3. **MySQL**: Set up a local database.
4. **Postman** or any REST client: For API testing.

---

## Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/spring-reddit-clone.git
cd spring-reddit-clone
```

### 2. Configure the Database
1. Create a new MySQL database:
   ```sql
   CREATE DATABASE spring-reddit-clone;
   ```
2. Update the database configuration in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.url=jdbc:mysql://localhost:3306/spring-reddit-clone
   spring.datasource.username=your-username
   spring.datasource.password=your-password

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

### 3. Build and Run
1. Install dependencies and package the application:
   ```bash
   mvn clean install
   ```
2. Start the application:
   ```bash
   mvn spring-boot:run
   ```
3. Access the application API at `http://localhost:8080`.

---

## API Endpoints

### Authentication
- **Sign Up:** `POST /api/auth/signup`
- **Log In:** `POST /api/auth/login`
- **Account Verification:** `GET /api/auth/accountVerification/{token}`
- **Refresh Token:** `POST /api/auth/refresh/token`
- **Log Out:** `POST /api/auth/logout`

### Posts
- **Create Post:** `POST /api/post`
- **Get All Posts:** `GET /api/post`
- **Get Post by ID:** `GET /api/post/{id}`
- **Get All Posts by Subreddit:** `GET /api/post/{subredditId}`
- **Get All Posts by User:** `GET /api/post/{username}`


### Subreddits
- **Create Subreddit:** `POST /api/subreddit`
- **Get All Subreddits:** `GET /api/subreddit`
- **Get A Subreddit by ID:** `GET /api/subreddit/{id}`


### Comments
- **Add Comment:** `POST /api/comment`
- **Get Comments by Post:** `GET /api/comment/{postId}`
- **Get Comments by User:** `GET /api/comment/{userName}`


### Voting
- **Vote on Post:** `POST /api/vote`

---

## Maven Dependencies

Key dependencies used in `pom.xml`:
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>1.5.3.Final</version>
    </dependency>
</dependencies>
```

---

## Folder Structure

```plaintext
src
├── main
│   ├── java/com/example/springredditclone
│   │   ├── config       # Security configuration classes
│   │   ├── controller   # REST controllers
│   │   ├── dto          # Data Transfer Objects
│   │   ├── exceptions   # Custom exceptions
│   │   ├── mapper       # MapStruct interfaces
│   │   ├── model        # Entity classes
│   │   ├── repository   # JPA repositories
│   │   ├── security     # Security configurations
│   │   ├── service      # Business logic
│   │   └── SpringRedditCloneApplication.java  # Main application entry point
│   └── resources
│       ├── app.key  # Private RSA Key
│       ├── app.pub  # Public RSA Key
│       ├── application.properties  # Configuration file
│       └── static  # Static resources (if applicable)
└── test
    └── java/com/example/springredditclone  # Unit and integration tests
```

---

## Testing

1. Use **Postman** to test the API endpoints.
<!-- 2. Run unit tests:
   ```bash
   mvn test
   ``` -->

---

## Future Enhancements

- Add frontend integration with React or Angular.
- Implement full-text search functionality.
- Deploy the application to a cloud platform (e.g., AWS or GCP).
