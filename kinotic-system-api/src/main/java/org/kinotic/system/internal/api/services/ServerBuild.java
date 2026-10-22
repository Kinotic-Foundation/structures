package org.kinotic.system.internal.api.services;

import java.util.List;

/**
 * One build of a Kinotic server, as the nodes running it report it. A snapshot version names every
 * build made from it, so the libraries tell two builds of one version apart.
 *
 * @param serverName the server's name
 * @param version    the server's version
 * @param components the package URLs of the libraries the build runs, or {@code null} when they could
 *                   not be read
 */
record ServerBuild(String serverName, String version, List<String> components) {}
