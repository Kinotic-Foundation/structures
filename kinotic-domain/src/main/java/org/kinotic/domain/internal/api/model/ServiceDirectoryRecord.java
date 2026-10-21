package org.kinotic.domain.internal.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.core.api.directory.ServiceDirectoryEntry;

/**
 * A directory entry as the Elasticsearch strategy stores it: the contract the {@link ServiceDirectoryEntry}
 * describes, and the hash an unchanged contract write is told apart by.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class ServiceDirectoryRecord extends ServiceDirectoryEntry {
    /**
     * The hash of the contract fields as last published.
     */
    private String contractHash;
}
