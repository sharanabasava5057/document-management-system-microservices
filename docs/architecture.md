# Architecture Diagram (Text Description)

- discovery-service (Eureka) - service registry
- api-gateway - single entry point, forwards to:
  - document-metadata-service (CRUD + MySQL)
  - file-storage-service (chunked upload/download, local file system)

Frontend (Angular) talks only to api-gateway (`http://localhost:8080`).

Security: Basic Auth at gateway and services.
