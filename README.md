# Financial System – Java & Spring

Financial system built in **Java with Spring**.  
The project is developed locally first and later will be evolved to run on **AWS**.

---

## Overview

This project simulates basic financial operations, focusing on:
- clean code
- domain modeling
- backend best practices
- cloud readiness

---

## Architecture

- DDD (Domain-Driven Design)
- SOLID principles
- Clean / Hexagonal architecture
- Separation between domain, service and infrastructure
- Repositories defined by interfaces

---

## Current Features

### Auth
- Authentication and authorization
- Client login
- Transactions create only with client token

### Client
- Client creation
- CPF and required field validation
- Client status (`ACTIVE`, `INACTIVE`, `BLOCKED`)
- Balance control
- Domain methods for credit and debit

### Transaction
- Transaction creation
- Transaction statuses (`PENDING`, `COMPLETED`, `FAILED`)
- Balance validation
- Transactions update client balance
- Timestamps (`createdAt`, `updatedAt`)

---

## Validation and Error Handling
- DTOs for input and output
- Centralized exception handling
- Custom business exceptions

---

## Observability
- Application logs
- Spring Boot Actuator

---

## Testing
- Unit tests for service layer
- Integration tests for controllers (planned)

---

## Planned Features

- AWS deployment
- Infrastructure as Code with Terraform
- Asynchronous processing with SQS and Lambda
- Persistence with DynamoDB
- Containerization with ECS
- Monitoring with CloudWatch
- CI/CD with GitHub Actions

---

## Tech Stack

- Java
- Spring Boot
- AWS (planned)
- DynamoDB (planned)
- Terraform (planned)
- Git & GitHub
- GitHub Actions
