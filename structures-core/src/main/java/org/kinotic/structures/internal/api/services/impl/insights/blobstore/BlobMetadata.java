package org.kinotic.structures.internal.api.services.impl.insights.blobstore;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

/**
 * Metadata information about a blob without its actual content.
 * Used for listing files and getting file information efficiently.
 */
@Data
@Builder
public class BlobMetadata {
    
    /**
     * The hierarchical path of the file within the project.
     */
    private final String path;
    
    /**
     * MIME type of the content.
     */
    private final String contentType;
    
    /**
     * Size of the content in bytes.
     */
    private final long contentLength;
    
    /**
     * When the content was created.
     */
    private final Instant createdAt;
    
    /**
     * When the content was last modified.
     */
    private final Instant lastModified;
    
    /**
     * ETag or version identifier for the content (optional).
     */
    private final String etag;
    
    /**
     * Whether this represents a directory (true) or file (false).
     */
    private final boolean isDirectory;
    
    /**
     * Additional metadata properties (optional).
     */
    private final Map<String, String> metadata;
    
    /**
     * Convenience method to get the filename from the path.
     */
    public String getFileName() {
        if (path == null || path.isEmpty()) return "";
        int lastSlash = path.lastIndexOf('/');
        return lastSlash >= 0 ? path.substring(lastSlash + 1) : path;
    }
    
    /**
     * Gets the parent directory path.
     */
    public String getParentPath() {
        if (path == null || path.isEmpty()) return "";
        int lastSlash = path.lastIndexOf('/');
        return lastSlash > 0 ? path.substring(0, lastSlash) : "";
    }
    
    /**
     * Gets the file extension (without the dot).
     */
    public String getFileExtension() {
        String fileName = getFileName();
        int lastDot = fileName.lastIndexOf('.');
        return lastDot > 0 ? fileName.substring(lastDot + 1) : "";
    }
}