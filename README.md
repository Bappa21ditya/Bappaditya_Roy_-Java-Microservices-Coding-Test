# Bappaditya_Roy_-Java-Microservices-Coding-Test


Approach: 

This microservice is designed using a layered architecture where the controller handles API requests, the service layer contains business logic, and the repository manages data operations. DTOs are used for data transfer between client and server, helping to decouple internal models from external APIs. Mappers convert between DTOs and entities to maintain clean separation of concerns. Validation ensures correct input data, and global exception handling provides consistent and meaningful error responses. The service layer is interface-driven, making the design flexible and extensible for future implementations.



1.Flow Diagram

        ┌───────────────────────────────┐
        │        Client (Postman)       │
        └───────────────┬───────────────┘
                        │ HTTP Request
                        ▼
        ┌───────────────────────────────┐
        │        Controller Layer       │
        │  - Handles REST APIs          │
        │  - Validates input (@Valid)   │
        └───────────────┬───────────────┘
                        │ DTO
                        ▼
        ┌───────────────────────────────┐
        │         Mapper Layer          │
        │  - DTO ↔ Entity conversion    │
        └───────────────┬───────────────┘
                        │ Entity
                        ▼
        ┌───────────────────────────────┐
        │         Service Layer         │
        │  - Business logic             │
        │  - Interface-based design     │
        │  - Multiple implementations   │
        └───────────────┬───────────────┘
                        │
                        ▼
        ┌───────────────────────────────┐
        │       Repository Layer        │
        │  - Data access (in-memory)    │
        └───────────────┬───────────────┘
                        │
                        ▼
                ┌───────────────┐
                │   Data Store  │
                │ (In-Memory)   │
                └───────────────┘







2.Request flow with validation and exception handeling:
  
Client Request
      │
Controller  ──► Validation (@Valid)
      │                │
      │                └── ❌ Validation Error → GlobalExceptionHandler (400)
Service (Business Logic)
      │
      ├── ❌ Not Found → GlobalExceptionHandler (404)
      │
      ├── ❌ Invalid Transition → GlobalExceptionHandler (400)
      │
      
Repository → Data
      
Response (DTO via Mapper)        





3.Component Relationship:

Controller
   │
   ▼
Service Interface  ◄──────────────┐
   │                              │
   ▼                              │
OnlineOrderServiceImpl     OfflineOrderServiceImpl
   │
   ▼
Repository









Future Enhancements:

1. Scalability & Architecture
 we can Introduce event-driven communication using Apache Kafka for asynchronous processing (e.g., order → payment flow)
 Add caching layer (Redis) for frequently accessed orders
 Integrate Eureka Server for service discovery and API Gateway as a single entry point for routing and security

3. Persistence
Replace in-memory h2 db  with a relational database using Spring Data JPA

5. Security
Implement authentication and authorization using Spring Security + JWT

7. Observability
Add logging (SLF4J) and monitoring using Actuator + Prometheus/Grafana

9. API Improvements
Integrate Swagger/OpenAPI for API documentation
Standardize API response format with error codes

11. Testing & DevOps
Add integration tests and improve code coverage (sonarcube)
Containerize using Docker and deploy via Kubernetes





 flow diagram  :
                 ┌──────────────────────────────┐
                │        Client (UI/Postman)   │
                └──────────────┬───────────────┘
                               │
                ┌──────────────────────────────┐
                │        API Gateway           │
                │ (Routing, Security, Logging)│
                └──────────────┬───────────────┘
                               │
        ┌──────────────────────┼──────────────────────┐
┌───────────────┐     ┌───────────────┐     ┌───────────────┐
│ Order Service │     │ Payment Service│     │ Inventory Svc │
└───────┬───────┘     └───────┬───────┘     └───────┬───────┘
        │                     │                     │
        └──────────────┬──────┴──────────────┬──────┘
               ┌─────────────────────────────────┐
               │        Eureka Server            │
               │     (Service Registry)          │
               └─────────────────────────────────┘


