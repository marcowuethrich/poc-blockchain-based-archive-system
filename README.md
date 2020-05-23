# Proof of Concept
## Blockchain based archive system


### Modules
| Name | Description | Port |
|---|---|---|
| Access | REST API for the consumer to get information from the archive | 8001 |
| Archival Storage | Manages storage of the original data | 8002 |
| Data Management | Manages storage of the meta information about the information | 8003 |
| Ingest | REST API for the consumer, to upload new data into the archival | 8004 |


### Requirements
- Java 11 (Kotlin 1.3.72")


### API Documentation (OpenAPI)

| Name | URL |
|---|---|
| Access | http://localhost:8001/swagger-ui.html |
| Archival Storage | http://localhost:8002/swagger-ui.html |
| Data Management | http://localhost:8003/swagger-ui.html |
| Ingest | http://localhost:8004/swagger-ui.html |

To generate the openAPI json files run:     
`gradle -p access clean generateOpenApiDocs && 
gradle -p archival-storage clean generateOpenApiDocs && 
gradle -p datamanagement clean generateOpenApiDocs && 
gradle -p ingest clean generateOpenApiDocs`    
Output directory: `docs/open-api`
