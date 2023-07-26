# PingPulse
PingPulse is a server management application developed with Spring Boot and Angular. The application is designed to manage servers with functionalities including listing, pinging, saving, fetching, and deleting servers, as well as displaying server images.

## Technologies Used
- Backend: Spring Boot
- Frontend: Angular

## Backend

### Packages
- Main Application: com.example.servermanagement
- Repositories: com.example.servermanagement.repository
- Models: com.example.servermanagement.model

The main Spring Boot application class, `ServerManagementApplication`, runs the application and includes a `CommandLineRunner` bean that initializes the server repository with a single server entity.

The `ServerRepository` is a JPA repository with a custom method `findByIpAddress` for finding a server based on its IP address.

The `Server` model defines the fields for a server, including an id, IP address, server name, memory, type, image URL, and status.

### Properties
The Spring Boot application is configured to use a MySQL database. Hibernate is set to create the database tables automatically based on the entity classes, and SQL statements are logged to the console.

## Frontend

The frontend is built with Angular and provides an intuitive user interface for managing servers.

### Components
- AppComponent: The main component of the application.

### Services
- ServerService: Handles the HTTP requests to the backend server.

### Models
- Server: A TypeScript interface representing the data for a server.
- EStatus: An enum representing possible server statuses.
- EDataState: An enum representing possible data states (loading, loaded, error) in the application.

The Angular frontend provides a user-friendly interface for managing servers, with options to list all servers, ping a server, save a new server, and filter servers by their status.

## Setup
### Requirements
- Java 8 or higher
- Maven
- Node.js and npm
- Angular CLI

### Installation
1. Clone the repository
```
git clone https://github.com/vyom1611/server-management.git
```

2. Navigate to the project directory
```
cd server-management
```

3. Build the Spring Boot Application
```
mvn clean install
```

4. Run the Spring Boot application (Note: you should have a connection to a local MySQL
   database, and edit the connection string in application.properties)
```
mvn spring-boot:run
```

5. Navigate to the frontend directory
```
cd angular-frontend
```

6. Install Angular dependencies
```
npm install
```

7. Run the Angular server
```
ng serve
```

You can access the application at `http://localhost:4200`.

## License
This project is licensed under the MIT License - see the LICENSE.md file for details.

