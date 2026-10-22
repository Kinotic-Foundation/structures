package org.kinotic.system.internal.api.services;

/**
 * One build of a Kinotic server, as the attributes of the nodes running it name it.
 *
 * @param serverName     the server's name
 * @param version        the server's version
 * @param componentsHash the hash of the libraries the build runs, or {@code null} when its nodes hold no
 *                       library list
 */
record ServerBuild(String serverName, String version, String componentsHash) {}
