# XPense - Personal Finance Management API

[![Java](https://img.shields.io/badge/Java-19-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-42.7.3-blue.svg)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-Build-red.svg)](https://maven.apache.org/)

## 📋 Overview

XPense is a personal finance management REST API built with Spring Boot that allows users to track their income, expenses, and categorize their financial transactions. The application provides comprehensive CRUD operations for managing financial data with proper validation and database persistence.

## 🏗️ Architecture

The application follows a layered architecture pattern:

```
┌─────────────────┐
│   Controllers   │  ← REST API Layer
├─────────────────┤
│    Services     │  ← Business Logic Layer
├─────────────────┤
│  Repositories   │  ← Data Access Layer
├─────────────────┤
│   Database      │  ← PostgreSQL Database
└─────────────────┘
```

## 🚀 Features

### Core Functionality
- **Income Management**: Track and manage income transactions
- **Expense Management**: Track and manage expense transactions (outputs)
- **Category Management**: Organize transactions by categories
- **Data Validation**: Comprehensive input validation using Bean Validation
- **Database Migrations**: Automated database schema management with Flyway

### Technical Features
- RESTful API design
- OpenAPI/Swagger documentation
- JPA/Hibernate for ORM
- PostgreSQL database with H2 for testing
- Comprehensive unit and integration tests
- Lombok for reducing boilerplate code

## 🛠️ Technology Stack

| Component | Technology | Version |
|-----------|------------|---------|
| **Framework** | Spring Boot | 3.2.4 |
| **Language** | Java | 19 |
| **Build Tool** | Maven | 3.x |
| **Database** | PostgreSQL | 42.7.3 |
| **Test Database** | H2 | Runtime |
| **ORM** | JPA/Hibernate | 6.x |
| **Migration** | Flyway | 10.0.0 |
| **Documentation** | SpringDoc OpenAPI | 2.3.0 |
| **Utilities** | Lombok | Latest |

## 📊 Data Model

### Entities

#### Category
```java
- id: Long (Primary Key)
- name: String (Not Blank)
```

#### BaseTransactionEntity (Abstract)
```java
- id: Long (Primary Key)
- amount: Double (Not Null)
- description: String (Not Blank)
- date: LocalDate (Not Null)
- createdAt: Timestamp
```

#### Income (extends BaseTransactionEntity)
```java
- Inherits all fields from BaseTransactionEntity
```

#### Output (extends BaseTransactionEntity)
```java
- Inherits all fields from BaseTransactionEntity
- category: Category (One-to-One relationship)
```

## 🔌 API Endpoints

### Income Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/incomes/create` | Create a new income |
| `GET` | `/incomes` | Get all incomes |
| `GET` | `/incomes/{id}` | Get income by ID |
| `PUT` | `/incomes/{id}` | Update income |
| `DELETE` | `/incomes/{id}` | Delete income |

### Expense Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/outputs/create` | Create a new expense |
| `GET` | `/outputs` | Get all expenses |
| `GET` | `/outputs/{id}` | Get expense by ID |
| `PUT` | `/outputs/{id}` | Update expense |
| `DELETE` | `/outputs/{id}` | Delete expense |

### Category Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/categories/create` | Create a new category |
| `GET` | `/categories` | Get all categories |
| `GET` | `/categories/{id}` | Get category by ID |
| `PUT` | `/categories/{id}` | Update category |
| `DELETE` | `/categories/{id}` | Delete category |

## 📝 Data Transfer Objects (DTOs)

### IncomeDTO
```json
{
  "description": "string (required)",
  "category": "string",
  "amount": "double (required, positive)",
  "date": "string (required, YYYY-MM-DD format)"
}
```

### OutputDTO
```json
{
  "description": "string (required)",
  "category": "string (required)",
  "amount": "double (required, positive)",
  "date": "string (required, YYYY-MM-DD format)"
}
```

### CategoryDTO
```json
{
  "name": "string (required, not blank)"
}
```

## 🔧 Setup and Installation

### Prerequisites
- Java 19 or higher
- Maven 3.6+
- PostgreSQL 12+
- Git

### Database Setup
1. Create a PostgreSQL database:
```sql
CREATE DATABASE "xpense-api";
```

2. Update database configuration in `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/xpense-api
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### Running the Application

1. **Clone the repository:**
```bash
git clone <repository-url>
cd xpense-app
```

2. **Build the project:**
```bash
mvn clean compile
```

3. **Run database migrations:**
```bash
mvn flyway:migrate
```

4. **Run the application:**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test classes
mvn test -Dtest=CategoryServiceTest,OutputServiceTest
```

## 📚 Documentation

### API Documentation
Once the application is running, you can access:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

### Database Console (Development)
- **H2 Console** (for tests): `http://localhost:8080/h2-console`

## 🧪 Testing

The application includes comprehensive testing:

### Test Coverage
- **Unit Tests**: Model classes, Service layer logic
- **Integration Tests**: Service layer with database integration
- **Controller Tests**: REST API endpoint testing

### Test Configuration
- Uses H2 in-memory database for tests
- Separate `application-test.properties` for test configuration
- Mock-based testing with Mockito for isolated unit tests

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/xpense/xpensedemo/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST Controllers
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── enums/          # Enumeration classes
│   │   ├── model/          # JPA Entities
│   │   │   ├── category/   # Category entity
│   │   │   └── transaction/ # Transaction entities
│   │   ├── repository/     # JPA Repositories
│   │   ├── service/        # Business Logic Services
│   │   └── XpensedemoApplication.java
│   └── resources/
│       ├── application.properties
│       └── db/migration/   # Flyway migration scripts
└── test/
    ├── java/               # Test classes
    └── resources/
        └── application-test.properties
```

## 🔍 Key Features Explained

### Category Management
- Categories help organize expenses
- Categories have unique names (duplicates are prevented)
- Categories can be associated with expense transactions

### Transaction Management
- **Income**: Represents money coming in (salary, freelance, etc.)
- **Output**: Represents money going out (expenses, purchases, etc.)
- All transactions include amount, description, and date
- Outputs can be categorized for better organization

### Validation
- Bean Validation ensures data integrity
- Custom business logic validation in services
- Proper error handling and response messages

## 🚦 Development Guidelines

### Code Style
- Uses Lombok to reduce boilerplate code
- Follows Spring Boot best practices
- Clear separation of concerns across layers

### Database Management
- Flyway handles all database schema changes
- Version-controlled migration scripts
- Baseline migration support for existing databases

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For support and questions, please open an issue in the GitHub repository.

---

**Built with ❤️ using Spring Boot and Java**
