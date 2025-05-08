# Furniture Hub Application

## Database Setup Instructions

This application uses SQLite for storing user login data. Follow these instructions to set up the database connection:

### 1. Download SQLite JDBC Driver

You need to add the SQLite JDBC driver to your project. There are two ways to do this:

#### Option 1: Using Maven (Recommended)

If your project uses Maven, add the following dependency to your `pom.xml` file:

```xml
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.36.0.3</version>
</dependency>
```

#### Option 2: Manual Download

If your project doesn't use Maven:

1. Download the SQLite JDBC driver from [https://github.com/xerial/sqlite-jdbc/releases](https://github.com/xerial/sqlite-jdbc/releases)
2. Add the JAR file to your project's classpath:
   - In IntelliJ IDEA: File > Project Structure > Libraries > + > Java > Select the downloaded JAR file
   - In Eclipse: Right-click on your project > Build Path > Configure Build Path > Libraries > Add External JARs > Select the downloaded JAR file

### 2. Database Structure

The application automatically creates a SQLite database file named `furniture_hub.db` in the root directory of your project when you run the application for the first time. The database contains a `users` table with the following structure:

```sql
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 3. Database Connection

The application uses the `DatabaseConnection` class to handle database operations. This class provides methods for:

- Connecting to the database
- Creating the necessary tables
- Registering a new user
- Authenticating a user
- Checking if a username or email already exists

### 4. Security Considerations

In a production environment, you should consider the following security enhancements:

- Password hashing: Currently, passwords are stored in plain text. In a real application, you should hash passwords using a secure algorithm like BCrypt.
- Connection pooling: For better performance, consider implementing connection pooling.
- Input validation: Additional input validation should be implemented to prevent SQL injection attacks.

### 5. Troubleshooting

If you encounter any issues with the database connection:

1. Make sure the SQLite JDBC driver is correctly added to your project's classpath.
2. Check the console for error messages related to the database connection.
3. Verify that the application has write permissions in the directory where the database file is created.
4. If the database file exists but is corrupted, delete it and restart the application to create a new one.