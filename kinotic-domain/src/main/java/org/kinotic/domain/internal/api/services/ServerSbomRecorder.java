package org.kinotic.domain.internal.api.services;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.cluster.ClusterNode;
import org.kinotic.core.api.KinoticNodeAttributes;
import org.kinotic.domain.api.model.ServerSbom;
import org.kinotic.domain.api.repositories.ServerSbomRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Records the node's {@link ServerSbom} when it starts, and deletes it when the node shuts down.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ServerSbomRecorder {

    // Written into each server jar by the serverComponents task of the server convention, one package URL per line
    private static final String COMPONENTS_RESOURCE = "META-INF/kinotic/components.txt";

    private static final long DELETE_TIMEOUT_SECONDS = 10;

    private final Ignite ignite;
    private final ServerSbomRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    public void recordSbom() {
        ClusterNode node = ignite.cluster().localNode();
        Date started = new Date();
        List<String> packages;
        try {
            packages = readComponents();
        } catch (IOException e) {
            log.error("Could not read {}; the node records no SBOM", COMPONENTS_RESOURCE, e);
            return;
        }
        ServerSbom sbom = new ServerSbom().setId(node.id().toString())
                                          .setServerName(node.attribute(KinoticNodeAttributes.SERVER_NAME))
                                          .setVersion(node.attribute(KinoticNodeAttributes.VERSION))
                                          .setPackages(packages)
                                          .setStarted(started);
        repository.save(sbom)
                  .onFailure(error -> log.error("Could not record the node's SBOM", error));
    }

    @PreDestroy
    void deleteSbom() {
        // only the node itself deletes its row, so no node can delete the row of one that is still running;
        // a node that crashes leaves its row behind, which readers skip since they look up running nodes only
        try {
            repository.deleteById(ignite.cluster().localNode().id().toString())
                      .toCompletionStage()
                      .toCompletableFuture()
                      .get(DELETE_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (ExecutionException | TimeoutException e) {
            log.warn("Could not delete the node's SBOM; its row stays behind", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Interrupted while deleting the node's SBOM; its row stays behind");
        }
    }

    // The package URLs the classpath's COMPONENTS_RESOURCE lists, empty when it holds none
    private static List<String> readComponents() throws IOException {
        List<String> ret = new ArrayList<>();
        try (InputStream listing = ServerSbomRecorder.class.getClassLoader().getResourceAsStream(COMPONENTS_RESOURCE)) {
            if (listing != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(listing, StandardCharsets.UTF_8));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.isBlank()) {
                        ret.add(line.trim());
                    }
                }
            }
        }
        return ret;
    }

}
