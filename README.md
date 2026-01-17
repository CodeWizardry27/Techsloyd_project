# TRANSACTION AND PAYMENT MANAGEMENT

A comprehensive Spring Boot-based payment processing system that handles various payment methods including cash, credit cards, and digital wallets. The application provides RESTful APIs for transaction management, receipt generation, discount processing, and payment verification.

## Features

- **Multi-Payment Method Support**: Cash, credit/debit cards, and digital wallets (Apple Pay, Google Pay, PayPal)
- **Transaction Management**: Create, void, and refund transactions with detailed item tracking
- **Receipt Generation**: Generate and send receipts via email, SMS, or print with QR codes
- **Discount System**: Apply and validate discounts on transactions
- **Payment Verification**: Verify payment status and details
- **PDF Receipt Generation**: Generate professional PDF receipts with QR codes
- **Email Integration**: Send receipts and notifications via SMTP
- **Database Integration**: PostgreSQL with JPA/Hibernate

## Technologies Used

- **Framework**: Spring Boot 3.2.1
- **Language**: Java 17
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA with Hibernate
- **Web Framework**: Spring Web (REST APIs)
- **Validation**: Jakarta Bean Validation
- **Template Engine**: Thymeleaf
- **PDF Generation**: OpenPDF
- **QR Code Generation**: ZXing
- **Email**: Spring Boot Mail Starter
- **Build Tool**: Maven
- **Testing**: Spring Boot Test

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- SMTP server for email functionality (optional)

## Installation and Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd payments
```

### 2. Database Setup

Create a PostgreSQL database named `db` and update the connection details in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/db
spring.datasource.username=postgres
spring.datasource.password=Shubham@123
```

The application will automatically create the required tables using the schema defined in `src/main/resources/schema.sql`.

### 3. Build the Application

```bash
./mvnw clean install
```

### 4. Run the Application

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`.

## API Endpoints

### Payment Controller (`/api/payments`)

- `GET /api/payments/methods` - Get available payment methods
- `POST /api/payments/process` - Process a payment
- `POST /api/payments/refund` - Refund a payment
- `GET /api/payments/{id}/status` - Get payment status
- `GET /api/payments/fee` - Calculate processing fee
- `POST /api/payments/verify` - Verify payment

### Transaction Controller (`/api/transactions`)

- `GET /api/transactions` - Get all transactions
- `GET /api/transactions/:id` - Get transaction by ID
- `POST /api/transactions` - Create transaction
- `POST /api/transactions/:id/void` - Void transaction
- `GET /api/transactions/search` - Search transactions
- `GET /api/transactions/summary` - Get summary stats
- `GET /api/transactions/closeout` - Daily closeout report

### Receipt Controller (`/api/receipts`)

- `POST /api/receipts/generate` - Generate receipt
- `GET /api/receipts/:transactionId` - Get receipt data
- `POST /api/receipts/email` - Email receipt
- `POST /api/receipts/sms` - SMS receipt link
- `POST /api/receipts/print` - Print receipt
- `GET /api/receipts/:transactionId/pdf` - Get PDF receipt
- `GET /api/receipts/:transactionId/html` - Get HTML receipt

### Discount Controller (`/api/discounts`)

- `POST /api/discounts` - Create a discount
- `POST /api/discounts` - Get discount
- `POST /api/discounts/apply` - Apply discount to transaction
- `POST /api/discounts/validate` - Validate discount code

## Configuration

Key configuration properties in `application.properties`:

- **Database**: PostgreSQL connection settings
- **JPA/Hibernate**: DDL auto-validation, SQL logging
- **Mail**: SMTP configuration for email receipts
- **Server**: Error handling and logging levels

## Testing

Run the tests using Maven:

```bash
./mvnw test
```

## Project Structure

```
payments/
├── src/
│   ├── main/
│   │   ├── java/com/techsloyd/payments/
│   │   │   ├── PaymentsApplication.java
│   │   │   ├── controller/          # REST controllers
│   │   │   ├── dto/                 # Data transfer objects
│   │   │   ├── entity/              # JPA entities
│   │   │   ├── enumtype/            # Enums
│   │   │   ├── repository/          # JPA repositories
│   │   │   ├── service/             # Business logic
│   │   │   └── util/                # Utility classes
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── schema.sql
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/com/techsloyd/payments/
│           └── PaymentsApplicationTests.java
├── target/                          # Build output
├── pom.xml                          # Maven configuration
├── mvnw                             # Maven wrapper
├── mvnw.cmd                         # Maven wrapper (Windows)
├── HELP.md                          # Additional documentation
└── README.md                        # This file
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions, please open an issue in the GitHub repository.