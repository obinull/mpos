# Mobile Order System - REST API ☕

This project is a secure, high-performance REST API for a mobile ordering system, built with Java 21 and Spring Boot. It provides a complete backend solution for browsing products, managing a personal shopping cart, and placing orders.

---

## ✨ Features

* **Product Management**: Full CRUD (Create, Read, Update, Delete) for products with paging support.
* **Secure Authentication**: JWT-based registration and login system using Spring Security.
* **User-Specific Carts**: An in-memory cart system uniquely tied to each authenticated user.
* **Order Placement**: A transactional endpoint to convert a user's cart into a persistent order in the database.
* **High Performance**: Built on Java 21 and configured to use Virtual Threads for efficient handling of concurrent requests.

---

## 💻 Technology Stack

* **Java 21**
* **Spring Boot 3.x**
* **Spring Security (JWT)**
* **Spring Data JPA**
* **H2 In-Memory Database**
* **Maven**

---

## 🚀 Getting Started

### Prerequisites

* JDK 21 or later
* Apache Maven

### Running the Application

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/obinull/mpos.git
    ```

2.  **Navigate to the project directory:**
    ```bash
    cd your-repository-name
    ```

3.  **Run the application using the Maven wrapper:**
    * On macOS/Linux:
        ```bash
        ./mvnw spring-boot:run
        ```
    * On Windows:
        ```bash
        mvnw.cmd spring-boot:run
        ```

The API will be running at `http://localhost:8080`. The H2 database is in-memory and is seeded with sample product data from `src/main/resources/data.sql` on startup.

---

##  API Endpoints

### 🔑 Authentication

| Method | Endpoint                    | Description                              | Auth Required |
| :----- | :-------------------------- | :--------------------------------------- | :------------ |
| `POST` | `/api/v1/auth/register`     | Creates a new user.                      | No            |
| `POST` | `/api/v1/auth/login`        | Authenticates a user and returns a JWT.  | No            |

### 🛍️ Products

| Method | Endpoint                    | Description                           | Auth Required |
| :----- | :-------------------------- | :------------------------------------ | :------------ |
| `GET`  | `/api/v1/products`          | Lists all products.                   | No            |
| `POST` | `/api/v1/products`          | Creates a new product.                | Yes           |
| `GET`  | `/api/v1/products/{id}`     | Retrieves a single product by its ID. | No            |
| `PUT`  | `/api/v1/products/{id}`     | Updates an existing product.          | Yes           |
| `DELETE` | `/api/v1/products/{id}`   | Deletes a product.                    | Yes           |

### 🛒 Cart & Orders

| Method | Endpoint                | Description                               | Auth Required |
| :----- | :---------------------- | :---------------------------------------- | :------------ |
| `POST` | `/api/v1/cart/items`    | Adds an item to the user's personal cart. | Yes           |
| `GET`  | `/api/v1/cart`          | Shows the contents of the user's cart.    | Yes           |
| `POST` | `/api/v1/orders`        | Places an order from the user's cart.     | Yes           |

---

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
