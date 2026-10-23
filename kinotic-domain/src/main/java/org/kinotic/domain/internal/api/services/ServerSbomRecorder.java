package org.kinotic.domain.internal.api.services;

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

/**
 * Records the node's {@link ServerSbom} when it starts, and deletes those of nodes that have left
 * the cluster.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ServerSbomRecorder {

    // Written into each server jar by the serverComponents task of the server convention, one package URL per line
    private static final String COMPONENTS_RESOURCE = "META-INF/kinotic/components.txt";

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
        // a node missing from members joined after started was taken, so it records its SBOM later than
        // started and the deletion leaves it
        List<String> members = ignite.cluster().forServers().nodes().stream().map(member -> member.id().toString()).toList();
        repository.save(sbom)
                  .compose(saved -> repository.deleteAllOfOtherNodes(members, started))
                  .onFailure(error -> log.error("Could not record the node's SBOM", error));
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
