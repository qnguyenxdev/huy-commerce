```text
  __    __   __    __  ____    ____           ______   ______   .___  ___. .___  ___.  _______ .______        ______  _______ 
|  |  |  | |  |  |  | \   \  /   /          /      | /  __  \  |   \/   | |   \/   | |   ____||   _  \      /      ||   ____|
|  |__|  | |  |  |  |  \   \/   /   ______ |  ,----'|  |  |  | |  \  /  | |  \  /  | |  |__   |  |_)  |    |  ,----'|  |__   
|   __   | |  |  |  |   \_    _/   |______||  |     |  |  |  | |  |\/|  | |  |\/|  | |   __|  |      /     |  |     |   __|  
|  |  |  | |  `--'  |     |  |             |  `----.|  `--'  | |  |  |  | |  |  |  | |  |____ |  |\  \----.|  `----.|  |____ 
|__|  |__|  \______/      |__|              \______| \______/  |__|  |__| |__|  |__| |_______|| _| `._____| \______||_______|                                                                                                                                                                                                                             
```
# 🛒 HuyCommerce – Microservices E-Commerce Platform

**HuyCommerce** is a full-stack, cloud-ready e-commerce platform built with a microservices architecture. It leverages Spring Boot for backend services and Angular for the frontend. Each service is independently deployable and communicates via REST APIs or Kafka events, with a centralized API Gateway for routing and security.

---

## 🔧 Tech Stack

### Frontend
- **Angular** – Responsive SPA (Single Page Application)

### Backend Microservices (Spring Boot)
- **API Gateway** – Entry point for all client requests
- **Identity-Service** – Authentication & authorization (JWT-based)
- **Product-Service** – Product management with PostgreSQL & Cloudinary
- **Order-Service** – Order processing and cart handling
- **Payment-Service** – Payment integration (Stripe API)
- **Notification-Service** – Email/notification handling via Kafka
- **Search-Service** – Product search using Elasticsearch

### Databases
- **PostgreSQL** – Product, user, and order persistence
- **MongoDB** – Notification logs
- **Redis** (planned) – Caching/session store

### Others
- **Kafka** – Asynchronous messaging between services
- **Cloudinary** – Image hosting for product thumbnails
- **Elasticsearch** – Full-text search support for products
- **Stripe API** – Payment processing

---

## 📌 Features

- 🔐 User authentication & role-based authorization
- 📦 Product management with image upload
- 🛒 Cart and order processing
- 💸 Payment integration (Stripe)
- 📬 Notification service using Kafka
- 🔍 Full-text product search via Elasticsearch
- 📁 Clean architecture with DTOs, service, repository layers
- 📊 Microservice communication via REST and Kafka
- 🐳 Containerized for Docker deployment

---

## 🗺️ Architecture Diagram

![Architecture](./path-to-your-diagram.png)

---

## 🚀 Getting Started

### Prerequisites
- Java 21+
- Node.js & Angular CLI
- Docker & Docker Compose
- PostgreSQL, MongoDB, Kafka, Elasticsearch running (via Docker or locally)

### Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/qnguyenxdev/huy-commerce.git
   cd huy-commerce
   ```

2. **Start services using Docker Compose:**
   ```bash
   docker-compose up -d
   ```

3. **Run Backend Microservices:**
   Each microservice is a Spring Boot app. Navigate to its directory and run:
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Run Frontend App:**
   ```bash
   cd web-app
   npm install
   ng serve
   ```

---

## 🧪 API Endpoints

| Service           | Endpoint                         | Description                       |
|------------------|----------------------------------|-----------------------------------|
| Identity          | `/api/auth/**`                   | Auth, login, JWT                  |
| Product           | `/api/products/**`               | CRUD for products                 |
| Order             | `/api/orders/**`                 | Place & manage orders             |
| Payment           | `/api/payments/**`               | Stripe payment endpoints          |
| Notification      | `/api/notifications/**`          | Send and track notifications      |
| Search            | `/api/search/**`                 | Full-text search (Elastic)        |

---

## 📬 Contact

Created with ❤️ by **Quang Huy Nguyen**  
📧 quang.huy165@gmail.com

---

## 📄 License

MIT License. See `LICENSE` for details.
