package org.kinotic.structures.internal.api.services.impl.insights.blobstore;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * Represents the content and metadata of a blob stored in the insights storage system.
 */
@Data
@Builder
public class BlobContent {
    
    /**
     * The actual file content as bytes.
     */
    private final byte[] content;
    
    /**
     * MIME type of the content (e.g., "application/typescript", "application/json").
     */
    private final String contentType;
    
    /**
     * Size of the content in bytes.
     */
    private final long contentLength;
    
    /**
     * When the content was last modified.
     */
    private final Instant lastModified;
    
    /**
     * ETag or version identifier for the content (optional).
     * Useful for caching and conflict detection.
     */
    private final String etag;
    
    /**
     * Additional metadata properties (optional).
     * Can store custom properties like "generated-by", "ai-model", etc.
     */
    private final java.util.Map<String, String> metadata;
    
    /**
     * Convenience method to get content as a UTF-8 string.
     * Should only be used for text content.
     */
    public String getContentAsString() {
        return new String(content, java.nio.charset.StandardCharsets.UTF_8);
    }
    
    /**
     * Checks if the content is text-based based on content type.
     */
    public boolean isTextContent() {
        if (contentType == null) return false;
        return contentType.startsWith("text/") || 
               contentType.equals("application/json") ||
               contentType.equals("application/typescript") ||
               contentType.equals("application/javascript") ||
               contentType.equals("application/xml");
    }
}