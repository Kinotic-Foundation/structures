package org.kinotic.structures.internal.api.services.impl.insights.blobstore;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kinotic.structures.api.config.StructuresProperties;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Filesystem-based implementation of InsightsBlobStorage.
 * Used primarily for testing and development environments.
 * 
 * Storage structure:
 * - Base directory: configured via application.yml (insights.storage.filesystem.base-path)
 * - Project structure: {base-path}/{projectId}/{path}
 * 
 * Example:
 * - /tmp/insights-storage/project-123/src/components/Chart.tsx
 * - /tmp/insights-storage/project-123/package.json
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class FileSystemBlobStorage implements InsightsBlobStorage {

    private final StructuresProperties structuresProperties;

    @Override
    public CompletableFuture<Void> store(String projectId, String path, byte[] content, String contentType) {
        return CompletableFuture.runAsync(() -> {
            try {
                Path filePath = getProjectFilePath(projectId, path);
                
                // Create parent directories if they don't exist
                Files.createDirectories(filePath.getParent());
                
                // Write content to file
                Files.write(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                
                // Store metadata as extended attributes (if supported) or in a companion file
                storeMetadata(filePath, contentType, content.length);
                
                log.debug("Stored file: {} ({} bytes)", filePath, content.length);
                
            } catch (IOException e) {
                log.error("Failed to store file: {}/{}", projectId, path, e);
                throw new RuntimeException("Failed to store file", e);
            }
        });
    }

    @Override
    public CompletableFuture<Void> store(String projectId, String path, InputStream content, String contentType, long contentLength) {
        return CompletableFuture.runAsync(() -> {
            try {
                Path filePath = getProjectFilePath(projectId, path);
                
                // Create parent directories if they don't exist
                Files.createDirectories(filePath.getParent());
                
                // Copy from InputStream to file
                long bytesWritten = Files.copy(content, filePath, StandardCopyOption.REPLACE_EXISTING);
                
                // Store metadata
                storeMetadata(filePath, contentType, bytesWritten);
                
                log.debug("Stored file from stream: {} ({} bytes)", filePath, bytesWritten);
                
            } catch (IOException e) {
                log.error("Failed to store file from stream: {}/{}", projectId, path, e);
                throw new RuntimeException("Failed to store file from stream", e);
            }
        });
    }

    @Override
    public CompletableFuture<Optional<Blob>> retrieve(String projectId, String path) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Path filePath = getProjectFilePath(projectId, path);
                
                if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
                    return Optional.empty();
                }
                
                byte[] content = Files.readAllBytes(filePath);
                Optional<BlobMetadata> metadataOpt = createBlobMetadata(projectId, filePath);
                
                if (metadataOpt.isEmpty()) {
                    return Optional.empty();
                }
                
                return Optional.of(Blob.builder()
                    .metadata(metadataOpt.get())
                    .content(content)
                    .build());
                    
            } catch (IOException e) {
                log.error("Failed to retrieve file: {}/{}", projectId, path, e);
                return Optional.empty();
            }
        });
    }

    @Override
    public CompletableFuture<Optional<InputStream>> retrieveStream(String projectId, String path) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Path filePath = getProjectFilePath(projectId, path);
                
                if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
                    return Optional.empty();
                }
                
                return Optional.of(Files.newInputStream(filePath));
                
            } catch (IOException e) {
                log.error("Failed to retrieve file stream: {}/{}", projectId, path, e);
                return Optional.empty();
            }
        });
    }

    @Override
    public CompletableFuture<List<BlobMetadata>> listFiles(String projectId, String directoryPath) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Path dirPath = getProjectFilePath(projectId, directoryPath);
                
                if (!Files.exists(dirPath) || !Files.isDirectory(dirPath)) {
                    return Collections.emptyList();
                }
                
                return Files.list(dirPath)
                    .map(filePath -> createBlobMetadata(projectId, filePath))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
                    
            } catch (IOException e) {
                log.error("Failed to list files in: {}/{}", projectId, directoryPath, e);
                return Collections.emptyList();
            }
        });
    }

    @Override
    public CompletableFuture<List<BlobMetadata>> listFilesRecursive(String projectId, String directoryPath) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Path dirPath = getProjectFilePath(projectId, directoryPath);
                
                if (!Files.exists(dirPath) || !Files.isDirectory(dirPath)) {
                    return Collections.emptyList();
                }
                
                List<BlobMetadata> files = new ArrayList<>();
                Files.walkFileTree(dirPath, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        createBlobMetadata(projectId, file).ifPresent(files::add);
                        return FileVisitResult.CONTINUE;
                    }
                    
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                        if (!dir.equals(dirPath)) {
                            createBlobMetadata(projectId, dir).ifPresent(files::add);
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
                
                return files;
                
            } catch (IOException e) {
                log.error("Failed to list files recursively in: {}/{}", projectId, directoryPath, e);
                return Collections.emptyList();
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> exists(String projectId, String path) {
        return CompletableFuture.supplyAsync(() -> {
            Path filePath = getProjectFilePath(projectId, path);
            return Files.exists(filePath);
        });
    }

    @Override
    public CompletableFuture<Void> delete(String projectId, String path) {
        return CompletableFuture.runAsync(() -> {
            try {
                Path filePath = getProjectFilePath(projectId, path);
                
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                    // Also delete metadata file if it exists
                    Path metadataPath = getMetadataPath(filePath);
                    Files.deleteIfExists(metadataPath);
                    
                    log.debug("Deleted file: {}", filePath);
                }
                
            } catch (IOException e) {
                log.error("Failed to delete file: {}/{}", projectId, path, e);
                throw new RuntimeException("Failed to delete file", e);
            }
        });
    }

    @Override
    public CompletableFuture<Void> deleteProject(String projectId) {
        return CompletableFuture.runAsync(() -> {
            try {
                Path projectPath = getProjectPath(projectId);
                
                if (Files.exists(projectPath)) {
                    Files.walkFileTree(projectPath, new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            Files.delete(file);
                            return FileVisitResult.CONTINUE;
                        }
                        
                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            Files.delete(dir);
                            return FileVisitResult.CONTINUE;
                        }
                    });
                    
                    log.info("Deleted project: {}", projectId);
                }
                
            } catch (IOException e) {
                log.error("Failed to delete project: {}", projectId, e);
                throw new RuntimeException("Failed to delete project", e);
            }
        });
    }

    @Override
    public CompletableFuture<Optional<BlobMetadata>> getMetadata(String projectId, String path) {
        return CompletableFuture.supplyAsync(() -> {
            Path filePath = getProjectFilePath(projectId, path);
            return createBlobMetadata(projectId, filePath);
        });
    }

    @Override
    public CompletableFuture<Void> initializeProject(String projectId, String projectTemplate) {
        return CompletableFuture.runAsync(() -> {
            try {
                Path projectPath = getProjectPath(projectId);
                Files.createDirectories(projectPath);
                
                // Create basic TypeScript React project structure
                createProjectTemplate(projectPath, projectTemplate);
                
                log.info("Initialized project: {} with template: {}", projectId, projectTemplate);
                
            } catch (IOException e) {
                log.error("Failed to initialize project: {}", projectId, e);
                throw new RuntimeException("Failed to initialize project", e);
            }
        });
    }

    @Override
    public CompletableFuture<Void> copyProject(String sourceProjectId, String targetProjectId, boolean overwrite) {
        return CompletableFuture.runAsync(() -> {
            try {
                Path sourcePath = getProjectPath(sourceProjectId);
                Path targetPath = getProjectPath(targetProjectId);
                
                if (!Files.exists(sourcePath)) {
                    throw new IllegalArgumentException("Source project does not exist: " + sourceProjectId);
                }
                
                if (Files.exists(targetPath) && !overwrite) {
                    throw new IllegalArgumentException("Target project already exists: " + targetProjectId);
                }
                
                // Copy the entire directory tree
                Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        Path targetDir = targetPath.resolve(sourcePath.relativize(dir));
                        Files.createDirectories(targetDir);
                        return FileVisitResult.CONTINUE;
                    }
                    
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Path targetFile = targetPath.resolve(sourcePath.relativize(file));
                        Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                        return FileVisitResult.CONTINUE;
                    }
                });
                
                log.info("Copied project from {} to {}", sourceProjectId, targetProjectId);
                
            } catch (IOException e) {
                log.error("Failed to copy project from {} to {}", sourceProjectId, targetProjectId, e);
                throw new RuntimeException("Failed to copy project", e);
            }
        });
    }

    // Helper methods

    private Path getProjectPath(String projectId) {
        return Paths.get(structuresProperties.getBlobStoreRoot(), projectId);
    }

    private Path getProjectFilePath(String projectId, String path) {
        // Create the expected project base path
        Path projectBasePath = Path.of(structuresProperties.getBlobStoreRoot(), projectId).toAbsolutePath().normalize();
        
        // Build the target path using Path.of() which handles path components safely
        Path targetPath = Path.of(structuresProperties.getBlobStoreRoot(), projectId, path).toAbsolutePath().normalize();
        
        // Security check: ensure the final path is still within the project directory
        if (!targetPath.startsWith(projectBasePath)) {
            throw new SecurityException("Path traversal attempt detected: " + path);
        }
        
        return targetPath;
    }

    private Path getMetadataPath(Path filePath) {
        return filePath.resolveSibling(filePath.getFileName() + ".metadata");
    }

    private void storeMetadata(Path filePath, String contentType, long contentLength) {
        try {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("content-type", contentType);
            metadata.put("content-length", String.valueOf(contentLength));
            metadata.put("stored-at", Instant.now().toString());
            
            Path metadataPath = getMetadataPath(filePath);
            Properties props = new Properties();
            props.putAll(metadata);
            
            try (OutputStream out = Files.newOutputStream(metadataPath)) {
                props.store(out, "File metadata");
            }
            
        } catch (IOException e) {
            log.warn("Failed to store metadata for: {}", filePath, e);
        }
    }

    private Map<String, String> loadMetadata(Path filePath) {
        try {
            Path metadataPath = getMetadataPath(filePath);
            
            if (!Files.exists(metadataPath)) {
                return Collections.emptyMap();
            }
            
            Properties props = new Properties();
            try (InputStream in = Files.newInputStream(metadataPath)) {
                props.load(in);
            }
            
            Map<String, String> metadata = new HashMap<>();
            for (String key : props.stringPropertyNames()) {
                metadata.put(key, props.getProperty(key));
            }
            return metadata;
            
        } catch (IOException e) {
            log.warn("Failed to load metadata for: {}", filePath, e);
            return Collections.emptyMap();
        }
    }

    private String generateETag(Path filePath, BasicFileAttributes attrs) {
        // Simple ETag based on file path, size, and modification time
        String data = filePath.toString() + attrs.size() + attrs.lastModifiedTime();
        return Integer.toHexString(data.hashCode());
    }

    private Optional<BlobMetadata> createBlobMetadata(String projectId, Path filePath) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
            Path projectPath = getProjectPath(projectId);
            
            // Calculate relative path within project
            String relativePath = projectPath.relativize(filePath).toString().replace('\\', '/');
            
            Map<String, String> metadata = loadMetadata(filePath);
            String contentType = metadata.getOrDefault("content-type", guessContentType(filePath));
            
            return Optional.of(BlobMetadata.builder()
                .path(relativePath)
                .contentType(contentType)
                .contentLength(attrs.size())
                .createdAt(attrs.creationTime().toInstant())
                .lastModified(attrs.lastModifiedTime().toInstant())
                .etag(generateETag(filePath, attrs))
                .isDirectory(attrs.isDirectory())
                .metadata(metadata)
                .build());
                
        } catch (IOException e) {
            log.warn("Failed to create metadata for: {}", filePath, e);
            return Optional.empty();
        }
    }

    private String guessContentType(Path filePath) {
        String fileName = filePath.getFileName().toString().toLowerCase();
        
        if (fileName.endsWith(".ts") || fileName.endsWith(".tsx")) {
            return "application/typescript";
        } else if (fileName.endsWith(".js") || fileName.endsWith(".jsx")) {
            return "application/javascript";
        } else if (fileName.endsWith(".json")) {
            return "application/json";
        } else if (fileName.endsWith(".css")) {
            return "text/css";
        } else if (fileName.endsWith(".html")) {
            return "text/html";
        } else if (fileName.endsWith(".md")) {
            return "text/markdown";
        } else if (fileName.endsWith(".txt")) {
            return "text/plain";
        } else if (fileName.endsWith(".xml")) {
            return "application/xml";
        } else if (fileName.endsWith(".yml") || fileName.endsWith(".yaml")) {
            return "application/yaml";
        }
        
        return "application/octet-stream";
    }

    private void createProjectTemplate(Path projectPath, String template) throws IOException {
        switch (template) {
            case "typescript-react":
                createTypeScriptReactTemplate(projectPath);
                break;
            default:
                createBasicTemplate(projectPath);
        }
    }

    private void createTypeScriptReactTemplate(Path projectPath) throws IOException {
        // Create basic TypeScript React project structure
        
        // package.json
        String packageJson = """
        {
          "name": "insights-visualization-project",
          "version": "1.0.0",
          "description": "Generated visualization project from Structures AI",
          "main": "src/index.tsx",
          "scripts": {
            "start": "react-scripts start",
            "build": "react-scripts build",
            "test": "react-scripts test",
            "eject": "react-scripts eject"
          },
          "dependencies": {
            "@types/react": "^18.0.0",
            "@types/react-dom": "^18.0.0",
            "react": "^18.0.0",
            "react-dom": "^18.0.0",
            "react-scripts": "5.0.1",
            "recharts": "^2.8.0",
            "typescript": "^4.9.0"
          },
          "browserslist": {
            "production": [
              ">0.2%",
              "not dead",
              "not op_mini all"
            ],
            "development": [
              "last 1 chrome version",
              "last 1 firefox version",
              "last 1 safari version"
            ]
          }
        }
        """;
        
        // tsconfig.json
        String tsConfig = """
        {
          "compilerOptions": {
            "target": "es5",
            "lib": [
              "dom",
              "dom.iterable",
              "es6"
            ],
            "allowJs": true,
            "skipLibCheck": true,
            "esModuleInterop": true,
            "allowSyntheticDefaultImports": true,
            "strict": true,
            "forceConsistentCasingInFileNames": true,
            "noFallthroughCasesInSwitch": true,
            "module": "esnext",
            "moduleResolution": "node",
            "resolveJsonModule": true,
            "isolatedModules": true,
            "noEmit": true,
            "jsx": "react-jsx"
          },
          "include": [
            "src"
          ]
        }
        """;
        
        // README.md
        String readme = """
        # Insights Visualization Project
        
        This project contains generated visualization components from Structures AI.
        
        ## Getting Started
        
        1. Install dependencies:
        ```bash
        npm install
        ```
        
        2. Start the development server:
        ```bash
        npm start
        ```
        
        3. Open [http://localhost:3000](http://localhost:3000) to view it in the browser.
        
        ## Generated Components
        
        Generated visualization components will be placed in the `src/components/` directory.
        
        ## Structures API Integration
        
        This project includes the structures-api library for data access. Generated components
        will use `Structures.createEntityService<T>()` for data fetching.
        """;
        
        // Create directories
        Files.createDirectories(projectPath.resolve("src/components"));
        Files.createDirectories(projectPath.resolve("public"));
        
        // Write files
        Files.write(projectPath.resolve("package.json"), packageJson.getBytes());
        Files.write(projectPath.resolve("tsconfig.json"), tsConfig.getBytes());
        Files.write(projectPath.resolve("README.md"), readme.getBytes());
        
        // Create basic src/index.tsx
        String indexTsx = """
        import React from 'react';
        import ReactDOM from 'react-dom/client';
        import './index.css';
        import App from './App';
        
        const root = ReactDOM.createRoot(
          document.getElementById('root') as HTMLElement
        );
        root.render(
          <React.StrictMode>
            <App />
          </React.StrictMode>
        );
        """;
        
        // Create basic App.tsx
        String appTsx = """
        import React from 'react';
        import './App.css';
        
        function App() {
          return (
            <div className="App">
              <header className="App-header">
                <h1>Structures Insights Visualization</h1>
                <p>Generated components will appear here.</p>
              </header>
            </div>
          );
        }
        
        export default App;
        """;
        
        // Create basic CSS files
        String indexCss = """
        body {
          margin: 0;
          font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen',
            'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue',
            sans-serif;
          -webkit-font-smoothing: antialiased;
          -moz-osx-font-smoothing: grayscale;
        }
        
        code {
          font-family: source-code-pro, Menlo, Monaco, Consolas, 'Courier New',
            monospace;
        }
        """;
        
        String appCss = """
        .App {
          text-align: center;
        }
        
        .App-header {
          background-color: #282c34;
          padding: 20px;
          color: white;
        }
        
        .chart-container {
          margin: 20px;
          padding: 20px;
          border: 1px solid #ddd;
          border-radius: 8px;
        }
        """;
        
        Files.write(projectPath.resolve("src/index.tsx"), indexTsx.getBytes());
        Files.write(projectPath.resolve("src/App.tsx"), appTsx.getBytes());
        Files.write(projectPath.resolve("src/index.css"), indexCss.getBytes());
        Files.write(projectPath.resolve("src/App.css"), appCss.getBytes());
        
        // Create public/index.html
        String indexHtml = """
        <!DOCTYPE html>
        <html lang="en">
          <head>
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1" />
            <meta name="theme-color" content="#000000" />
            <meta name="description" content="Structures AI Generated Visualizations" />
            <title>Structures Insights</title>
          </head>
          <body>
            <noscript>You need to enable JavaScript to run this app.</noscript>
            <div id="root"></div>
          </body>
        </html>
        """;
        
        Files.write(projectPath.resolve("public/index.html"), indexHtml.getBytes());
    }

    private void createBasicTemplate(Path projectPath) throws IOException {
        // Create minimal template with just a README
        String readme = """
        # Insights Project
        
        This is a basic insights project created by Structures AI.
        Generated files will be stored in this directory.
        """;
        
        Files.write(projectPath.resolve("README.md"), readme.getBytes());
    }
}