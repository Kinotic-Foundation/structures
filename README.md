![CI](https://github.com/kinotic-foundation/structures/actions/workflows/gradle-build.yml/badge.svg?branch=develop)

# Structures
Structures is an open-source framework for data storage and retrieval, supporting schema evolution, data management, and providing a user-friendly GUI and OpenAPI interface for data management.

## Docs
* [https://kinotic-foundation.github.io/structures/](https://kinotic-foundation.github.io/structures/)

### Projects
* structures-core
  * Provides the core library for use in all other projects.
* structures-frontend
  * Provides a GUI for interacting with Structures.
* structures-server
  * Provides Access to the core library via a REST API and a GUI.



### Basic Setup
1. Install Docker Desktop.
2. Install SdkMan, which makes installing/managing Java and Gradle easy.  Also can manage multiple installed versions.
   1. `curl -s "https://get.sdkman.io" | bash`
3. Install Java 11
   1. `sdk install java 11.0.17-zulu`
4. Build the Docker images (Get a cup of :coffee: this could take a bit the first time, depending on your internet connection.)
   1. `./gradlew bootBuildImage`
5. Publish the image to docker 
   ```shell
      export DOCKER_HUB_USERNAME=<your docker hub username>
      export DOCKER_HUB_PASSWORD=<your docker hub password>
   
      ./gradlew bootBuildImage --publishImage
    ```
6. Run Docker Compose.
   1. `docker-compose up -d` (You should see the application listed under containers in docker desktop)
7. You can now view the application by going [here](http://localhost:9090/) in a browser. (This may not be available for at least a minute while the backend services finish starting.)
   1. [http://localhost:9090/](http://localhost:9090/)

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

