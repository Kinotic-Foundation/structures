# Structures Auth Library

A comprehensive JWT authentication and authorization library for the Structures framework.

## Overview

The Structures Auth library provides JWT token management, OIDC integration, and authorization services for Spring Boot applications. It follows the standard Structures library conventions and integrates seamlessly with the broader Structures ecosystem.

## Features

- **JWT Token Management**: Create, validate, and parse JWT tokens
- **OIDC Integration**: OpenID Connect authentication support
- **Authorization Services**: Role-based access control and permission management
- **Spring Boot Auto-Configuration**: Automatic configuration for Spring Boot applications
- **Security Best Practices**: Secure token handling and validation

## Dependencies

- **JJWT**: JWT token creation and validation
- **Jackson**: JSON processing for token claims
- **Continuum Core**: Framework integration
- **Spring Boot**: Auto-configuration and dependency injection

## Quick Start

### 1. Add the dependency

```gradle
implementation project(':structures-auth')
```

### 2. Enable auto-configuration

```java
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

### 3. Use the services

```java
@Service
public class MyService {
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private AuthorizationService authorizationService;
    
    public void processToken(String token) {
        Map<String, Object> claims = jwtService.validateToken(token);
        String userId = (String) claims.get("userId");
        
        if (authorizationService.hasRole(userId, "admin")) {
            // Perform admin operations
        }
    }
}
```

## Configuration

The library provides sensible defaults but can be customized through Spring Boot configuration properties:

```yaml
structures:
  auth:
    jwt:
      issuer: "https://your-issuer.com"
      audience: "your-audience"
      signing-key: "your-signing-key"
    oidc:
      providers:
        - name: "okta"
          issuer: "https://your-okta-domain.okta.com"
          client-id: "your-client-id"
```

## API Reference

### JwtService

- `createToken(Map<String, Object> claims)`: Creates a JWT token with the specified claims
- `validateToken(String token)`: Validates a JWT token and returns the claims
- `parseToken(String token)`: Parses a JWT token without validation

### AuthorizationService

- `hasRole(String userId, String role)`: Checks if a user has a specific role
- `hasAnyRole(String userId, Set<String> roles)`: Checks if a user has any of the specified roles
- `getUserRoles(String userId)`: Gets all roles for a user
- `hasPermission(String userId, String permission)`: Checks if a user has a specific permission

## Security Considerations

- Never log sensitive information like tokens or keys
- Always validate tokens before processing
- Use secure random for token generation
- Implement proper key management
- Follow OWASP security guidelines

## Testing

The library includes comprehensive tests:

```bash
./gradlew :structures-auth:test
```

## Contributing

1. Follow the existing code conventions
2. Add tests for new functionality
3. Update documentation as needed
4. Ensure security best practices are followed

## License

This library is part of the Structures framework and follows the same licensing terms. 