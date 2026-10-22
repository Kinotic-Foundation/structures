package org.kinotic.core.api;

import org.apache.ignite.lang.IgniteCallable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Lists the libraries the server a node runs was built with: the package URL of each, such as
 * {@code pkg:maven/io.vertx/vertx-core@5.1.6}, as the build recorded them in {@link #RESOURCE}.
 * Run on a node through Ignite compute; a node running from classes rather than a packaged jar lists
 * none.
 */
public class ServerComponentsTask implements IgniteCallable<List<String>> {

    /**
     * The classpath resource a packaged server lists its libraries in, one package URL per line.
     */
    public static final String RESOURCE = "META-INF/kinotic/components.txt";

    @Override
    public List<String> call() throws IOException {
        List<String> ret = new ArrayList<>();
        try (InputStream listing = ServerComponentsTask.class.getClassLoader().getResourceAsStream(RESOURCE)) {
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
