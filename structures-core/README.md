# Structures Core Library

The core library that provides the foundational data storage, retrieval, and management capabilities for the Structures framework.

## Overview

Structures Core is the heart of the Structures framework, providing:
- **Data Storage Engine**: Flexible schema-based data storage with support for multiple backends
- **Schema Management**: Dynamic schema evolution and versioning
- **Query Engine**: Powerful querying capabilities with GraphQL and REST support
- **Security Framework**: Authentication and authorization services
- **Plugin System**: Extensible architecture for custom functionality

## Features

- **Schema Evolution**: Dynamic schema changes without data migration
- **Multi-tenant Support**: Built-in tenant isolation and management
- **GraphQL API**: Native GraphQL support with schema introspection
- **REST API**: Comprehensive REST endpoints for all operations
- **Plugin Architecture**: Extensible system for custom data types and operations
- **Audit Trail**: Complete audit logging for all data operations
- **Search Integration**: Full-text search capabilities with Elasticsearch

## Dependencies

- **Spring Boot**: Core framework and dependency injection
- **GraphQL**: Query language and execution engine
- **Elasticsearch**: Search and indexing backend
- **Jackson**: JSON processing and serialization
- **Spring Security**: Authentication and authorization

## Quick Start

### 1. Add the dependency

```gradle
implementation project(':structures-core')
```

### 2. Enable auto-configuration

```java
@SpringBootApplication
@EnableStructuresCore
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

### 3. Configure your data source

```yaml
structures:
  core:
    elasticsearch:
      connections:
        - scheme: http
          host: localhost
          port: 9200
      index-prefix: struct_
      tenant-id-field: structuresTenantId
```

## Core Components

### Data Management
- **StructureService**: Core data operations (create, read, update, delete)
- **SchemaService**: Schema definition and evolution management
- **QueryService**: Advanced querying and filtering capabilities

### Security
- **SecurityService**: Authentication and authorization
- **TenantService**: Multi-tenant isolation and management

### Extensions
- **PluginManager**: Plugin system for custom functionality
- **DecoratorRegistry**: Custom decorators for data transformation

## API Reference

### StructureService
- `createStructure(String tenantId, String schemaId, Map<String, Object> data)`
- `getStructure(String tenantId, String structureId)`
- `updateStructure(String tenantId, String structureId, Map<String, Object> data)`
- `deleteStructure(String tenantId, String structureId)`
- `queryStructures(String tenantId, String schemaId, QueryCriteria criteria)`

### SchemaService
- `createSchema(String tenantId, SchemaDefinition schema)`
- `getSchema(String tenantId, String schemaId)`
- `updateSchema(String tenantId, String schemaId, SchemaDefinition schema)`
- `deleteSchema(String tenantId, String schemaId)`

## Configuration

### Elasticsearch Configuration
```yaml
structures:
  core:
    elasticsearch:
      connections:
        - scheme: http
          host: elasticsearch
          port: 9200
          username: elastic
          password: changeme
      connection-timeout: 5s
      socket-timeout: 60s
      index-prefix: struct_
      tenant-id-field: structuresTenantId
```

### GraphQL Configuration
```yaml
structures:
  core:
    graphql:
      port: 4000
      path: /graphql/
      cors:
        allowed-origins: "*"
```

### Security Configuration
```yaml
structures:
  core:
    security:
      openapi:
        security-type: BASIC
        port: 8080
        path: /api/
```

## Testing

The library includes comprehensive tests:

```bash
./gradlew :structures-core:test
```

## Contributing

1. Follow the existing code conventions
2. Add tests for new functionality
3. Update documentation as needed
4. Ensure backward compatibility for schema changes

## License

This library is part of the Structures framework and follows the same licensing terms.
