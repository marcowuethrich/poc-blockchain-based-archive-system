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
