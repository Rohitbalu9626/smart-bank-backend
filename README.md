# SmartBank Core Node x Google Pay Terminal 🏦✨

A responsive, high-fidelity Full-Stack Internet Retail Banking terminal application built using **Spring Boot (Java)**, **Vite + React (JavaScript)**, and **MySQL**. The system replicates a sleek corporate fintech ecosystem, allowing users to securely manage asset holdings, deposit/withdraw funds, transfer capital between accounts seamlessly, and track a persistent, real-time historical transaction passbook.

---

## 🚀 System Architecture Overview

The system runs on a classic decoupled full-stack architecture separated into distinct logical layers to maintain high cohesion and clear separation of concerns:

* **Presentation Layer (Frontend):** A component-based single-page application built with React and styled with a clean, high-contrast corporate navy aesthetic. It utilizes dynamic fluid layouts that dynamically adapt between desktop and smartphone display grids.
* **API Gateway / Routing Layer (Backend Controller):** Exposes secure REST API endpoints protected via Spring Security Basic Authentication protocols.
* **Business Logic Layer (Service Tier):** Intercepts operations, validates transaction constraints (e.g., overdraft verification), and manages explicit cross-table updates.
* **Data Persistence Layer (Database Server):** Leverages Spring Data JPA and Hibernate Object-Relational Mapping (ORM) to run transaction blocks safely against a persistent MySQL relational database schema.

---

## 🛠️ Technology Stack & Dependencies

### Backend Engine
* **Java Development Kit (JDK 17+)**
* **Spring Boot 3.x** (Web, Data JPA, Security)
* **MySQL Connector/J**
* **Hibernate Core**

### Frontend Interface
* **React 18** (Functional Components & Hooks)
* **Vite** (Next-generation lightning-fast frontend tooling)
* **CSS Flexbox / Fluid Grid Media Queries**
* **gh-pages** (Production deployment engine)

---

## 📦 Database Schema Design

The persistence tier maps three primary structural tables inside the MySQL instance:

1. **`account` Table:** Stores legal user signatures, account tracking values, mobile references, and live floating-point balance counts.
2. **`transactions` Table:** Holds an immutable historical audit ledger capturing transaction categories (`DEPOSIT`, `WITHDRAWAL`, `TRANSFER_DEBIT`, `TRANSFER_CREDIT`), operational delta amounts, and automatic server-generated timestamps.
3. **`user_profiles` Table:** Houses user credential keys mapping usernames (account numbers) to encoded authorization roles for Spring Security verification.

---

## 🔒 Security & Secrets Management

To guarantee safe public deployment, this project completely isolates all production passwords, local driver strings, and database credentials from the version control system.

* Credentials are held locally inside a secure, hidden `.env` file on the developer's workspace machine.
* Values are dynamically injected into the system via Spring expression placeholders (`${DB_PASSWORD}`) within the core configuration layer.
* A robust `.gitignore` setup ensures local environment files, target build binaries, and IDE internal metadata folders (`.idea/`) are strictly filtered out from public cloud tracking.

```properties
# Sanitized production reference snippet inside application.properties
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.security.user.password=${SECURITY_PASSWORD}