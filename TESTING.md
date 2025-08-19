# Testing Guide for Structures

This document outlines the testing requirements and configuration needed for the Structures project, particularly for CI/CD environments.

## Overview

The Structures project uses TestContainers for integration testing with Elasticsearch. This ensures that tests run against real Elasticsearch instances in isolated containers, providing reliable and consistent test results.

## Prerequisites

- Docker and Docker Compose running
- Java 17+ for backend tests
- Node.js 22+ for frontend tests

## Environment Variables

### Required Environment Variable

```bash
export TESTCONTAINERS_RYUK_DISABLED=true
```

**This environment variable is mandatory for all test execution.**

### Why This is Required

TestContainers uses a Ryuk container for resource cleanup and management. However, this container can cause connectivity issues in certain environments:

- **macOS with Docker Desktop**: Common connectivity issues
- **CI/CD environments**: Network restrictions and container isolation
- **Systems with strict firewalls**: Ryuk container may be blocked
- **Corporate networks**: Proxy and security configurations

Disabling Ryuk ensures reliable test execution by skipping the cleanup container. While this means containers won't be automatically cleaned up, TestContainers will still manage the lifecycle of test containers.

## Running Tests Locally

### Backend Tests (Java)

```bash
# Set required environment variable
export TESTCONTAINERS_RYUK_DISABLED=true

# Run all tests
./gradlew test

# Run specific module tests
./gradlew :structures-core:test
./gradlew :structures-server:test
./gradlew :structures-sql:test

# Run specific test class
./gradlew :structures-core:test --tests "*StructureCrudTests*"
```

### Frontend Tests (TypeScript/JavaScript)

```bash
# Set required environment variable
export TESTCONTAINERS_RYUK_DISABLED=true

# Run E2E tests
cd structures-js/structures-e2e
./gradlew pnpmTest

# Run API tests
cd structures-js/structures-api
./gradlew test
```

## CI/CD Configuration

### GitHub Actions

Add the environment variable to your workflow:

```yaml
name: Build and Test
on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v4
      
      - name: Set up JDK
        uses: actions/setup-java@v4
        with:
          distribution: 'zulu'
          java-version: 21
      
      - name: Build with Gradle
        env:
          TESTCONTAINERS_RYUK_DISABLED: true
        run: ./gradlew build
```

### GitLab CI

```yaml
variables:
  TESTCONTAINERS_RYUK_DISABLED: "true"

test:
  script:
    - ./gradlew build
```

### Jenkins

```groovy
pipeline {
    agent any
    environment {
        TESTCONTAINERS_RYUK_DISABLED = 'true'
    }
    stages {
        stage('Test') {
            steps {
                sh './gradlew build'
            }
        }
    }
}
```

### Azure DevOps

```yaml
variables:
  TESTCONTAINERS_RYUK_DISABLED: 'true'

steps:
- script: ./gradlew build
```

### CircleCI

```yaml
jobs:
  test:
    docker:
      - image: openjdk:21
    environment:
      TESTCONTAINERS_RYUK_DISABLED: true
    steps:
      - run: ./gradlew build
```

## Test Architecture

### TestContainers Usage

The project uses TestContainers in several modules:

1. **structures-core**: `ElasticsearchTestBase` for core functionality tests
2. **structures-server**: `ElasticsearchTestContainer` for server integration tests
3. **structures-sql**: `ElasticsearchSqlTestBase` for SQL parsing and execution tests

### Elasticsearch Configuration

All test containers use Elasticsearch version `8.18.1` with the following configuration:

```java
.withEnv("discovery.type", "single-node")
.withEnv("xpack.security.enabled", "false")
.withEnv("xpack.ml.enabled", "false")
.withEnv("xpack.watcher.enabled", "false")
.withEnv("xpack.monitoring.enabled", "false")
.withEnv("xpack.security.enrollment.enabled", "false")
.withEnv("xpack.security.http.ssl.enabled", "false")
.withEnv("xpack.security.transport.ssl.enabled", "false")
```

## Troubleshooting

### Common Issues

1. **Ryuk Connection Failed**
   ```
   Could not connect to Ryuk at localhost:XXXXX
   ```
   **Solution**: Set `TESTCONTAINERS_RYUK_DISABLED=true`

2. **Elasticsearch Container Won't Start**
   ```
   Elasticsearch container failed to start
   ```
   **Solution**: Ensure Docker has sufficient resources (at least 4GB RAM)

3. **Port Conflicts**
   ```
   Port XXXX is already in use
   ```
   **Solution**: Stop other services using the same ports or use different ports

4. **Permission Denied**
   ```
   Permission denied when starting containers
   ```
   **Solution**: Ensure Docker daemon is running and user has proper permissions

### Debug Mode

To enable TestContainers debug logging, add this environment variable:

```bash
export TESTCONTAINERS_DEBUG=true
```

This will provide detailed information about container startup, networking, and cleanup processes.

## Performance Considerations

### Resource Requirements

- **Minimum RAM**: 4GB for running tests with Elasticsearch
- **Recommended RAM**: 8GB+ for optimal performance
- **Disk Space**: At least 2GB free space for container images

### Test Execution Time

- **Unit Tests**: 1-2 minutes
- **Integration Tests**: 5-10 minutes (includes container startup)
- **Full Test Suite**: 15-30 minutes depending on system resources

### Optimization Tips

1. **Parallel Execution**: Tests run in parallel by default
2. **Container Reuse**: TestContainers reuses containers when possible
3. **Resource Limits**: Consider setting Docker resource limits for CI/CD environments

## Security Considerations

### Container Isolation

- Test containers run in isolated networks
- No persistent data between test runs
- Containers are automatically removed after tests complete

### Network Access

- Test containers only have access to the test network
- No external network access required
- All communication is internal to the test environment

## Support

If you encounter issues with testing:

1. Check that `TESTCONTAINERS_RYUK_DISABLED=true` is set
2. Ensure Docker is running and accessible
3. Verify sufficient system resources
4. Check the troubleshooting section above
5. Open an issue in the project repository with:
   - Error messages
   - Environment details (OS, Docker version, Java version)
   - Test command that failed
