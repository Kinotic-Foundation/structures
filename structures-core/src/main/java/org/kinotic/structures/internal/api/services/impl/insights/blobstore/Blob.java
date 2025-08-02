package org.kinotic.structures.internal.api.services.impl.insights.blobstore;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a complete blob with both metadata and content.
 * Combines BlobMetadata with the actual file content.
 */
@Data
@Builder
public class Blob {
    
    /**
     * Metadata information about the blob.
     */
    private final BlobMetadata metadata;
    
    /**
     * The actual file content as bytes.
     */
    private final byte[] content;
    
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
        if (metadata == null || metadata.getContentType() == null) return false;
        String contentType = metadata.getContentType();
        return contentType.startsWith("text/") || 
               contentType.equals("application/json") ||
               contentType.equals("application/typescript") ||
               contentType.equals("application/javascript") ||
               contentType.equals("application/xml");
    }
    
    /**
     * Gets the content length from metadata.
     */
    public long getContentLength() {
        return metadata != null ? metadata.getContentLength() : content.length;
    }
    
    /**
     * Gets the content type from metadata.
     */
    public String getContentType() {
        return metadata != null ? metadata.getContentType() : "application/octet-stream";
    }
}