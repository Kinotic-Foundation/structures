# Structures
Structures is an open-source framework for data storage and retrieval, supporting schema evolution, data management, and providing a user-friendly GUI and OpenAPI interface for data management.
![CI](https://github.com/kinotic-foundation/structures/actions/workflows/gradle-build.yml/badge.svg?branch=develop))

## Docs
* [Home](https://kinotic-foundation.github.io/structures/)

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
7. You can now view the application by going [here](http://localhost:8989/) in a browser. (This may not be available for at least a minute while the backend services finish starting.)
   1. [http://localhost:8989/](http://localhost:8989/)

### Environment Variables 
These variables are available for custom configuration, presented are the defaults.

```text
STRUCTURES_INDEX_PREFIX=struct_
STRUCTURES_ELASTIC_USE_SSL=true
STRUCTURES_ELASTIC_URIS=127.0.0.1:9200
STRUCTURES_ELASTIC_CONNECTION_TIMEOUT=1M
STRUCTURES_ELASTIC_SOCKET_TIMEOUT=1M
STRUCTURES_ELASTIC_USERNAME=
STRUCTURES_ELASTIC_PASSWORD=
```

