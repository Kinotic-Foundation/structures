package org.kinotic.structures.internal.api.services;

import co.elastic.clients.util.BinaryData;
import org.kinotic.structures.api.domain.Structure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 5/2/23.
 */
public class DefaultEntityService implements EntityService {

    private final Structure structure;

    public DefaultEntityService(Structure structure) {
        this.structure = structure;
    }

    @Override
    public CompletableFuture<BinaryData> save(BinaryData entity) {
        return null;
    }

    @Override
    public CompletableFuture<BinaryData> findById(String s) {
        return null;
    }

    @Override
    public CompletableFuture<Long> count() {
        return null;
    }

    @Override
    public CompletableFuture<Void> deleteById(String s) {
        return null;
    }

    @Override
    public CompletableFuture<Page<BinaryData>> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public CompletableFuture<Page<BinaryData>> search(String searchText, Pageable pageable) {
        return null;
    }

}
