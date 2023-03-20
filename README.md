# Structures
Structures is an open-source framework for data storage and retrieval, supporting schema evolution, data composition, and providing a user-friendly GUI and OpenAPI interface for data management.


### Projects
* structures-core
  * Provides the core library for use in all other projects.
* structures-serverr
  * Provides Access to the core library via a REST API and a GUI.
* structures-ui
  * Provides a GUI for interacting with Structures.


### Basic Setup
1. Install Docker Desktop.
2. Install SdkMan, which makes installing/managing Java and Gradle easy.  Also can manage multiple installed versions.
   1. `curl -s "https://get.sdkman.io" | bash`
3. Install Java 11
   1. `sdk install java 11.0.17-zulu`
4. Build the Docker images (Get a cup of :coffee: this could take a bit the first time, depending on your internet connection.)
   1. `./gradlew bootBuildImage`
5. Run Docker Compose.
   1. `docker-compose up -d` (You should see the application listed under containers in docker desktop)
6. You can now view the application by going [here](http://localhost:8080/) in a browser. (This may not be available for at least a minute while the backend services finish starting.)
   1. [http://localhost:8080/](http://localhost:8080/)
