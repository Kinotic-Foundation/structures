# Structures Config

Structures is designed to be embedded in any Spring Boot project. You can configure it using any standard Spring configuration mechanism, such as `application.yml`, `application.properties`, or environment variables. The reference implementation is in the `@structures-server` module, but you can use these options in your own Spring Boot applications by adding the `@EnableStructures` annotation to your main class.

## Enabling Structures in Your Project

Add the following annotation to your Spring Boot application:

```java
import org.kinotic.structures.api.annotations.EnableStructures;

@SpringBootApplication
@EnableStructures
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

## Configuration Options

All configuration options are prefixed with `structures.`. Here are the available options, their types, and default values:

### Indexing and Tenant
- **indexPrefix** (`String`, default: `struct_`):
  Prefix for all Elasticsearch indices. Must be a valid namespace (letters, numbers, `.`, `_`, `-`, max 16 chars).
- **tenantIdFieldName** (`String`, default: `tenantId`):
  Field name used for tenant identification.

### Elasticsearch Connection
- **elasticConnections** (`List<ElasticConnectionInfo>`, default: one entry with `host: localhost`, `port: 9200`, `scheme: http`):
  List of Elasticsearch connection info. Each entry has:
  - `host` (`String`, default: `localhost`)
  - `port` (`int`, default: `9200`)
  - `scheme` (`String`, default: `http`)
- **elasticUsername** (`String`, default: `null`):
  Username for Elasticsearch (optional).
- **elasticPassword** (`String`, default: `null`):
  Password for Elasticsearch (optional).
- **elasticConnectionTimeout** (`Duration`, default: `5s`):
  Connection timeout for Elasticsearch.
- **elasticSocketTimeout** (`Duration`, default: `1m`):
  Socket timeout for Elasticsearch.
- **elasticHealthCheckInterval** (`Duration`, default: `1m`):
  Interval for health checks on the Elasticsearch cluster.

#### Example:
```yaml
structures:
  elasticConnections:
    - host: "localhost"
      port: 9200
      scheme: "http"
  elasticUsername: "user"
  elasticPassword: "pass"
  elasticConnectionTimeout: 5s
  elasticSocketTimeout: 1m
  elasticHealthCheckInterval: 1m
```

### CORS (Cross-Origin Resource Sharing)
- **corsAllowedOriginPattern** (`String`, default: `http://localhost.*`):
  Java regex pattern for allowed origins. Use `*` to allow all.
- **corsAllowedHeaders** (`Set<String>`, default: `[Accept, Authorization, Content-Type]`):
  Allowed headers for CORS.
- **corsAllowCredentials** (`Boolean`, default: `null`):
  If set, controls the `Access-Control-Allow-Credentials` header. If `true`, origins must not contain `*`.

#### Example:
```yaml
structures:
  corsAllowedOriginPattern: "https://studio.apollographql.com|http://localhost:\d+"
  corsAllowedHeaders:
    - "*"
  corsAllowCredentials: true
```

### HTTP Limits
- **maxHttpHeaderSize** (`int`, default: `8192`):
  Max length of all HTTP headers in bytes.
- **maxHttpBodySize** (`long`, default: `-1`):
  Max length of HTTP body in bytes. `-1` means no limit.

### OpenAPI and GraphQL
- **openApiSecurityType** (`String`, default: `NONE`):
  Security type for OpenAPI. Possible values:
  - `NONE`: No security
  - `BASIC`: Basic authentication
  - `BEARER`: Bearer token authentication
- **openApiPort** (`int`, default: `8080`):
  Port for OpenAPI server.
- **openApiPath** (`String`, default: `/api/`):
  Path for OpenAPI endpoints.
- **openApiAdminPath** (`String`, default: `/admin/api/`):
  Path for OpenAPI admin endpoints.
- **openApiServerUrl** (`String`, default: `http://localhost:8080`):
  Server URL for OpenAPI.
- **graphqlPort** (`int`, default: `4000`):
  Port for GraphQL server.
- **graphqlPath** (`String`, default: `/graphql/`):
  Path for GraphQL endpoint.

#### Example:
```yaml
structures:
  openApiSecurityType: BASIC
  openApiPort: 8080
  openApiPath: "/api/"
  openApiAdminPath: "/admin/api/"
  openApiServerUrl: "http://localhost:8080"
  graphqlPort: 4000
  graphqlPath: "/graphql/"
```

### Web Server and Health Check
- **webServerPort** (`int`, default: `9090`):
  Port for static files and health check endpoint.
- **healthCheckPath** (`String`, default: `/health/`):
  Path for health check endpoint.
- **enableStaticFileServer** (`boolean`, default: `false`):
  If `true`, static files are served from `resources/webroot`.

### Sample Data
- **initializeWithSampleData** (`boolean`, default: `false`):
  If `true`, initializes Structures with sample data.

## Example Minimal Configuration

```yaml
structures:
  openApiSecurityType: BASIC
  enableStaticFileServer: true
```

## Example Development Configuration

```yaml
structures:
  initializeWithSampleData: true
  corsAllowedOriginPattern: "https://studio.apollographql.com|http://localhost:\d+"
  corsAllowedHeaders:
    - "*"
  corsAllowCredentials: true
```

## Notes
- All durations can be specified using the standard Spring Boot duration format (e.g., `5s`, `1m`).
- You can use any Spring Boot mechanism to supply these properties: YAML, properties files, environment variables, or command-line arguments.
- See the `@structures-server` module for a reference implementation and more configuration examples.
