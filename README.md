# Project Overview

This is a full-stack application consisting of a Spring Boot backend and an Angular frontend.

**Backend (demo):** A Java Spring Boot REST API using JPA with H2 database, built with Maven and running on Java 21.

**Frontend:** An Angular 20 single-page application built with Node.js 25.

## Dependencies

### For Docker (Recommended)
- Docker
- Docker Compose

### For Local Development

#### Backend (demo)
- Java 21 (JDK)
- Maven 3.9+

#### Frontend
- Node.js 25+
- npm

## Running the Application

### Using Docker Compose (Recommended)

**⚠️ Important:** This application is designed to run with Docker Compose. The frontend is configured to communicate with the backend using Docker's internal networking. Running services individually may require additional configuration changes.

1. Build and start both services:
```bash
docker compose up --build
# Running and detaching
docker compose up -d --build
```

2. Access the applications:
   - Backend API: http://localhost:8080
   - Frontend: http://localhost:4200

3. Stop the services:
```bash
docker compose down
```

### Running Locally (Advanced)

**Note:** Local development requires manual configuration. The frontend's API URL is configured for Docker by default. You may need to modify `frontend/src/environments/environment.ts` to point to `http://localhost:8080/api` for local development.

#### Backend
```bash
cd demo
mvn spring-boot:run
```
The backend will run on http://localhost:8080

#### Frontend
```bash
cd frontend
npm install
npm start
```
The frontend will run on http://localhost:4200

## Backend API Routes

The backend provides the following REST API endpoints:

### Currency Conversion
- **POST** `/api/convert`
  - Convert currency amounts between different currencies
  - Request body:
    ```json
    {
      "from": "USD",
      "to": "EUR",
      "amount": 100
    }
    ```

### Exchange Rates
- **GET** `/api/rates/latest`
  - Get the latest exchange rates for all currencies
  - Response includes date and array of currency rates

- **GET** `/api/rates/{code}/history?from={date}&to={date}`
  - Get historical exchange rate data for a specific currency
  - Path parameter: `code` - Currency code (e.g., USD, EUR)
  - Query parameters: 
    - `from` - Start date (ISO format: YYYY-MM-DD)
    - `to` - End date (ISO format: YYYY-MM-DD)

### Debug
- **GET** `/api/debug/currencies`
  - List all available currencies in the database

## Project Structure

```
.
├── demo/              # Spring Boot backend
├── frontend/          # Angular frontend
└── docker-compose.yml # Docker orchestration
```
