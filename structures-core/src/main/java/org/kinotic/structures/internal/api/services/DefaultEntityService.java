package org.kinotic.structures.internal.api.services;

import org.kinotic.structures.api.services.EntityService;
import org.springframework.core.codec.ByteArrayDecoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 5/2/23.
 */
public class DefaultEntityService implements EntityService {

    @Override
    public CompletableFuture<DataBuffer> save(DataBuffer entity) {
        return null;
    }

    @Override
    public CompletableFuture<DataBuffer> findById(String s) {
        ByteArrayDecoder
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
    public CompletableFuture<Page<DataBuffer>> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public CompletableFuture<Page<DataBuffer>> search(String searchText, Pageable pageable) {
        return null;
    }

}
