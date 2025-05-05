![CI](https://github.com/kinotic-foundation/structures/actions/workflows/gradle-build.yml/badge.svg?branch=develop)

# Structures
Structures is an open-source framework for data storage and retrieval, supporting schema evolution, data management, and providing a user-friendly GUI, OpenAPI, and GraphQL interface for data management.

## Documentation
For comprehensive documentation, including getting started guides, examples, and API reference, visit our documentation site:
[https://kinotic-foundation.github.io/structures/](https://kinotic-foundation.github.io/structures/)

## Quick Start
1. Clone the repository:
```bash
git clone https://github.com/kinotic-foundation/structures.git
cd structures
```

2. Start the development server:
```bash
cd docker-compose
docker-compose up -d
```

3. Visit [http://localhost:9090](http://localhost:9090) to access the Structures GUI

## Next Steps
- [Getting Started Guide](https://kinotic-foundation.github.io/structures/guide/getting-started) - Complete setup instructions and prerequisites
- [New Project Setup](https://kinotic-foundation.github.io/structures/guide/getting-started#new-project-setup) - Learn how to create a new project with Structures
- [Adding to Existing Project](https://kinotic-foundation.github.io/structures/guide/getting-started#adding-structures-to-an-existing-project) - Integrate Structures into your current project
- [Entity Definitions](https://kinotic-foundation.github.io/structures/guide/getting-started#understanding-entity-definitions) - Learn how to define your data structures

### Projects
* structures-core
  * Provides the core library for use in all other projects.
* structures-frontend
  * Provides a GUI for interacting with Structures.
* structures-server
  * Provides Access to the core library via a REST API and a GUI.

### Environment Variables 
These variables are available for custom configuration, presented are the defaults.

```text
STRUCTURES_INDEX_PREFIX: struct_
STRUCTURES_TENANT_ID_FIELD_NAME: structuresTenantId
STRUCTURES_ELASTICCONNECTIONS_0_SCHEME: http
STRUCTURES_ELASTICCONNECTIONS_0_HOST: elasticsearch
STRUCTURES_ELASTICCONNECTIONS_0_PORT: 9200
STRUCTURES_ELASTIC_CONNECTION_TIMEOUT: 5s
STRUCTURES_ELASTIC_SOCKET_TIMEOUT: 60s
STRUCTURES_ELASTIC_USERNAME:
STRUCTURES_ELASTIC_PASSWORD:
STRUCTURES_OPEN_API_SECURITY_TYPE: BASIC
STRUCTURES_OPEN_API_PORT: 8080
STRUCTURES_OPEN_API_PATH: /api/
STRUCTURES_OPEN_API_SERVER_URL: http://127.0.0.1:8080
STRUCTURES_GRAPHQL_PORT: 4000
STRUCTURES_GRAPHQL_PATH: /graphql/
STRUCTURES_CORS_ALLOWED_ORIGIN_PATTERN: '*'
STRUCTURES_WEB_SERVER_PORT: 9090
STRUCTURES_HEALTH_CHECK_PATH: /health/
STRUCTURES_ENABLE_STATIC_FILE_SERVER: true
STRUCTURES_INITIALIZE_WITH_SAMPLE_DATA: false
```

