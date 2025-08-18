# Docker Compose Services

This directory contains Docker Compose configurations for running the Structures framework and its dependencies in containerized environments.

## Overview

The Docker Compose files provide different service configurations for:
- **Development**: Local development environment with all services
- **Testing**: Isolated testing environment
- **Monitoring**: Observability stack with metrics and logging
- **Authentication**: Identity providers for testing and development

## Available Compose Files

### Core Services

#### `compose.yml` - Main Development Stack
- **Purpose**: Complete development environment
- **Services**: Elasticsearch, Kibana, Structures Server
- **Ports**: 
  - Elasticsearch: 9200
  - Kibana: 5601
  - Structures Server: 9090
- **Usage**: `docker-compose up -d`

#### `compose.test.yml` - Testing Environment
- **Purpose**: Isolated testing environment
- **Services**: Elasticsearch, Structures Server (test profile)
- **Ports**: 
  - Elasticsearch: 9201
  - Structures Server: 9091
- **Usage**: `docker-compose -f compose.test.yml up -d`

### Authentication Services

#### `compose.keycloak.yml` - Keycloak Identity Provider
- **Purpose**: OIDC authentication provider for development
- **Services**: PostgreSQL, Keycloak, Initialization container
- **Ports**: 
  - Keycloak: 8888
  - PostgreSQL: 5432
- **Usage**: `docker-compose -f compose.keycloak.yml up -d`
- **Documentation**: [Keycloak Setup Guide](KEYCLOAK_README.md)

### Monitoring & Observability

#### `compose-otel.yml` - OpenTelemetry Stack
- **Purpose**: Distributed tracing and metrics collection
- **Services**: OpenTelemetry Collector, Jaeger
- **Ports**: 
  - Collector: 4317, 4318
  - Jaeger: 16686
- **Usage**: `docker-compose -f compose-otel.yml up -d`

#### `compose.ek-stack.yml` - Elastic Stack
- **Purpose**: Log aggregation and analysis
- **Services**: Elasticsearch, Logstash, Kibana
- **Ports**: 
  - Elasticsearch: 9200
  - Logstash: 5044
  - Kibana: 5601
- **Usage**: `docker-compose -f compose.ek-stack.yml up -d`

### Utility Services

#### `compose.gen-schemas.yml` - Schema Generation
- **Purpose**: Generate GraphQL schemas from Elasticsearch mappings
- **Services**: Schema generator utility
- **Usage**: `docker-compose -f compose.gen-schemas.yml up`

### Override Files

#### `compose.test.override.yml` - Test Overrides
- **Purpose**: Override default settings for testing
- **Usage**: `docker-compose -f compose.yml -f compose.test.override.yml up -d`

#### `compose.ek-m4.override.yml` - M4 Mac Overrides
- **Purpose**: Optimize for Apple Silicon Macs
- **Usage**: `docker-compose -f compose.ek-stack.yml -f compose.ek-m4.override.yml up -d`

#### `compose.ek-transient.override.yml` - Transient Storage
- **Purpose**: Use temporary storage for development
- **Usage**: `docker-compose -f compose.ek-stack.yml -f compose.ek-transient.override.yml up -d`

## Quick Start

### 1. Start Basic Development Environment

```bash
# Start core services
docker-compose up -d

# Check service status
docker-compose ps

# View logs
docker-compose logs -f
```

### 2. **Recommended: Full Development Environment (M4 Mac)**

For developers on Apple Silicon Macs who want the complete setup with authentication and data seeding:

```bash
# Start all services optimized for M4 Mac with Keycloak and schema generation
docker-compose -f compose.yml -f compose.ek-m4.override.yml -f compose.gen-schemas.yml -f compose.keycloak.yml up -d
```

This command:
- Uses M4-optimized settings for better performance
- Includes Keycloak for OIDC authentication testing
- Generates test structures and data for them
- Starts all core services (Elasticsearch, Kibana, Structures Server)

### 3. Start with Authentication Only

```bash
# Start Keycloak for OIDC testing
docker-compose -f compose.keycloak.yml up -d

# Start main services with auth
docker-compose up -d
```

### 4. Start with Monitoring

```bash
# Start observability stack
docker-compose -f compose-otel.yml up -d

# Start main services
docker-compose up -d
```

## Service Configuration

### Elasticsearch
- **Version**: 8.x
- **Memory**: 2GB minimum
- **Storage**: Persistent volumes for data
- **Security**: Basic authentication enabled
- **Default Credentials**: elastic/changeme

### Keycloak
- **Version**: Latest
- **Database**: PostgreSQL 13
- **Realm**: Pre-configured test realm
- **Default Admin**: admin/admin
- **Test User**: testuser@example.com/password123

### Structures Server
- **Profile**: Development by default
- **Port**: 9090 (configurable)
- **Health Check**: /health endpoint
- **Logging**: Structured JSON logging

## Environment Variables

### Common Variables
```bash
# Elasticsearch
ELASTIC_PASSWORD=changeme
ELASTIC_USERNAME=elastic

# Keycloak
KEYCLOAK_ADMIN_PASSWORD=admin
POSTGRES_PASSWORD=password

# Structures
STRUCTURES_ELASTIC_HOST=elasticsearch
STRUCTURES_ELASTIC_PORT=9200
```

### Customization
Create `.env` files for environment-specific configuration:

```bash
# .env.local
ELASTIC_PASSWORD=mysecurepassword
STRUCTURES_SERVER_PORT=9091
```

## Development Workflow

### 1. Local Development
```bash
# Start all services
docker-compose up -d

# Access services
# - Structures: http://localhost:9090
# - Elasticsearch: http://localhost:9200
# - Kibana: http://localhost:5601
```

**Pro Tip for M4 Mac Users**: Use the full development command for the best experience:
```bash
docker-compose -f compose.yml -f compose.ek-m4.override.yml -f compose.gen-schemas.yml -f compose.keycloak.yml up -d
```

### 2. Testing
```bash
# Start test environment
docker-compose -f compose.test.yml up -d

# Run tests
./gradlew test

# Clean up
docker-compose -f compose.test.yml down -v
```

### 3. Debugging
```bash
# View service logs
docker-compose logs -f structures-server

# Access service shell
docker-compose exec elasticsearch bash

# Check service health
curl http://localhost:9090/health
```

## Troubleshooting

### Common Issues

1. **Port Conflicts**
   ```bash
   # Check what's using a port
   lsof -i :9200
   
   # Use different ports
   docker-compose -f compose.yml -f compose.test.override.yml up -d
   ```

2. **Service Startup Order**
   ```bash
   # Wait for dependencies
   docker-compose up -d elasticsearch
   sleep 30
   docker-compose up -d structures-server
   ```

3. **Memory Issues**
   ```bash
   # Increase Docker memory limit
   # Docker Desktop: Settings > Resources > Memory
   ```

4. **Storage Issues**
   ```bash
   # Clean up volumes
   docker-compose down -v
   docker volume prune
   ```

### Debug Commands

```bash
# Check service status
docker-compose ps

# View service logs
docker-compose logs -f [service-name]

# Access service container
docker-compose exec [service-name] bash

# Check service health
curl http://localhost:9090/health
curl http://localhost:9200/_cluster/health
```

## Production Considerations

### Security
- Change default passwords
- Use secrets management
- Enable TLS/HTTPS
- Restrict network access

### Performance
- Adjust memory limits
- Use SSD storage
- Configure resource limits
- Monitor resource usage

### Monitoring
- Enable health checks
- Set up log aggregation
- Configure metrics collection
- Implement alerting

## Related Documentation

- [Keycloak Setup](KEYCLOAK_README.md) - Detailed Keycloak configuration
- [Structures Server](../structures-server/README.md) - Backend server documentation
- [Getting Started](../../webdocs/guide/getting-started.md) - Complete setup guide

## Contributing

1. Test compose files with different environments
2. Update documentation for new services
3. Ensure backward compatibility
4. Add health checks for new services
