package org.kinotic.structures.internal.api.services.impl.insights.blobstore;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Hierarchical blob storage interface for storing generated code and project files.
 * Supports multiple storage providers (filesystem, Azure Blob, AWS S3) with a consistent API.
 * 
 * Storage paths are hierarchical and use forward slashes as separators (e.g., "project/src/components/Chart.tsx").
 * 
 * Key concepts:
 * - Project: A logical container for related files (e.g., a TypeScript project)
 * - Path: Hierarchical file path within a project
 * - Content: File content with metadata (MIME type, size, modified date)
 */
public interface InsightsBlobStorage {

    /**
     * Stores content at the specified path within a project.
     * Creates parent directories as needed.
     * 
     * @param projectId Unique identifier for the project
     * @param path Hierarchical path within the project (e.g., "src/components/Chart.tsx")
     * @param content File content as bytes
     * @param contentType MIME type of the content (e.g., "application/typescript", "application/json")
     * @return CompletableFuture that completes when storage is successful
     */
    CompletableFuture<Void> store(String projectId, String path, byte[] content, String contentType);

    /**
     * Stores content from an InputStream at the specified path.
     * 
     * @param projectId Unique identifier for the project
     * @param path Hierarchical path within the project
     * @param content InputStream containing the file content
     * @param contentType MIME type of the content
     * @param contentLength Expected content length in bytes (optional, -1 if unknown)
     * @return CompletableFuture that completes when storage is successful
     */
    CompletableFuture<Void> store(String projectId, String path, InputStream content, String contentType, long contentLength);

    /**
     * Retrieves content from the specified path.
     * 
     * @param projectId Unique identifier for the project
     * @param path Hierarchical path within the project
     * @return CompletableFuture containing the blob (metadata + content), or empty if not found
     */
    CompletableFuture<Optional<Blob>> retrieve(String projectId, String path);

    /**
     * Retrieves content as a stream for large files.
     * 
     * @param projectId Unique identifier for the project
     * @param path Hierarchical path within the project
     * @return CompletableFuture containing the content stream, or empty if not found
     */
    CompletableFuture<Optional<InputStream>> retrieveStream(String projectId, String path);

    /**
     * Lists all files within a project directory (non-recursive).
     * 
     * @param projectId Unique identifier for the project
     * @param directoryPath Directory path within the project (empty string for root)
     * @return CompletableFuture containing list of file metadata
     */
    CompletableFuture<List<BlobMetadata>> listFiles(String projectId, String directoryPath);

    /**
     * Lists all files within a project directory recursively.
     * 
     * @param projectId Unique identifier for the project
     * @param directoryPath Directory path within the project (empty string for root)
     * @return CompletableFuture containing list of file metadata
     */
    CompletableFuture<List<BlobMetadata>> listFilesRecursive(String projectId, String directoryPath);

    /**
     * Checks if a file exists at the specified path.
     * 
     * @param projectId Unique identifier for the project
     * @param path Hierarchical path within the project
     * @return CompletableFuture containing true if the file exists
     */
    CompletableFuture<Boolean> exists(String projectId, String path);

    /**
     * Deletes a file at the specified path.
     * 
     * @param projectId Unique identifier for the project
     * @param path Hierarchical path within the project
     * @return CompletableFuture that completes when deletion is successful
     */
    CompletableFuture<Void> delete(String projectId, String path);

    /**
     * Deletes an entire project and all its files.
     * 
     * @param projectId Unique identifier for the project
     * @return CompletableFuture that completes when deletion is successful
     */
    CompletableFuture<Void> deleteProject(String projectId);

    /**
     * Gets metadata for a specific file without retrieving its content.
     * 
     * @param projectId Unique identifier for the project
     * @param path Hierarchical path within the project
     * @return CompletableFuture containing file metadata, or empty if not found
     */
    CompletableFuture<Optional<BlobMetadata>> getMetadata(String projectId, String path);

    /**
     * Creates a project directory structure and initializes it with template files.
     * This is used to bootstrap new TypeScript projects with default files.
     * 
     * @param projectId Unique identifier for the project
     * @param projectTemplate Template to use for initialization (e.g., "typescript-react")
     * @return CompletableFuture that completes when project is initialized
     */
    CompletableFuture<Void> initializeProject(String projectId, String projectTemplate);

    /**
     * Copies files from one project to another.
     * Useful for creating project variants or backups.
     * 
     * @param sourceProjectId Source project identifier
     * @param targetProjectId Target project identifier
     * @param overwrite Whether to overwrite existing files in target
     * @return CompletableFuture that completes when copy is successful
     */
    CompletableFuture<Void> copyProject(String sourceProjectId, String targetProjectId, boolean overwrite);
}