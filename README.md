# FIAP Security System API

## Overview

The FIAP Security System API provides a set of endpoints to manage employees, handle user authentication, and report security incidents. The API is intended for use in security systems where incidents need to be logged, users managed, and role-based access is enforced.

## Features

- **Employee Management**: Create, update, and retrieve employee records.
- **User Authentication**: Register new users and handle login sessions.
- **Incident Management**: Create, update, delete, and retrieve security incidents.

## API Endpoints

### 1. Employee Management

- **Create User**: `POST /api/employees`
  - Adds a new employee to the system.
- **Get All Users**: `GET /api/employees`
  - Retrieves all employee records.
- **Get Employee by ID**: `GET /api/employees/{id}`
  - Retrieves the details of a specific employee.
- **Get Employees by Role**: `GET /api/employees/role/{role}`
  - Retrieves employees based on their role.

### 2. Authentication

- **Register**: `POST /api/auth/register`
  - Registers a new user in the system.
- **Login**: `POST /api/auth/login`
  - Authenticates an existing user and provides a token.

### 3. Incident Management

- **Get All Incidents**: `GET /api/incidents`
  - Retrieves all reported incidents.
- **Create Incident**: `POST /api/incidents`
  - Reports a new incident.
- **Get Incident by ID**: `GET /api/incidents/{id}`
  - Retrieves a specific incident by its ID.
- **Get Incidents by Status**: `GET /api/incidents/status/{status}`
  - Retrieves incidents filtered by their status.
- **Update Incident Status**: `PATCH /api/incidents/{id}/status`
  - Updates the status of a specific incident.
- **Delete Incident**: `DELETE /api/incidents/{id}`
  - Deletes a specific incident from the system.

## Getting Started

### Prerequisites

- **Java 11+**: The backend is built using Java, requiring version 11 or later.
- **Docker**: Used for containerization and easy deployment.

### Running the API

1. Clone the repository.
2. Ensure Docker is installed and running.
3. Use Docker Compose to build and start the application:
   ```bash
   docker-compose up --build
   ```
4. The API will be available at `http://localhost:8080`.

## Usage

You can use Postman to interact with the endpoints. A collection (`FIAP.postman_collection.json`) is provided for easy testing. Import it into Postman and run the requests directly.

## Technologies Used

- **Spring Boot**: For developing the REST API.
- **PostgreSQL**: As the primary database for storing user and incident data.
- **Docker**: For containerization.
- **Postman**: To test and document API endpoints.

