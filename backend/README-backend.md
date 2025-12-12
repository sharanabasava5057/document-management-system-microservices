# Document Management System - Backend (Microservices)

Services:
- discovery-service (Eureka)
- api-gateway (Spring Cloud Gateway + Basic Auth)
- document-metadata-service (CRUD + MySQL + Swagger)
- file-storage-service (Chunked upload/download + Swagger)

## How to Run (Local)

1. Start MySQL (or run `docker-compose up mysql`).
2. Run `mysql-schema.sql` in the database.
3. From `backend` run:

```bash
mvn clean install
```

4. Start services in order:
   - DiscoveryServiceApplication (port 8761)
   - DocumentMetadataServiceApplication (port 8081)
   - FileStorageServiceApplication (port 8082)
   - ApiGatewayApplication (port 8080)

5. Access Swagger:
   - Metadata: http://localhost:8081/swagger-ui.html
   - Files: http://localhost:8082/swagger-ui.html

Gateway base URL: `http://localhost:8080`

Basic Auth:
- username: `admin`
- password: `admin123`
