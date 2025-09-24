package org.kinotic.structures.api.services;

import java.util.concurrent.CompletableFuture;

import org.kinotic.continuum.api.annotations.Proxy;
import org.kinotic.continuum.api.annotations.Publish;
import org.kinotic.structures.api.domain.MigrationRequest;
import org.kinotic.structures.api.domain.MigrationResult;

/**
 * Service for executing project-specific migrations through the Structures API.
 * This service allows external clients to apply their own migrations to projects.
 */
@Publish
@Proxy
public interface MigrationService {

    /**
     * Executes migrations for a specific project.
     * 
     * @param migrationRequest the request containing migrations and project information
     * @return a future that completes with the migration result
     */
    CompletableFuture<MigrationResult> executeMigrations(MigrationRequest migrationRequest);

    /**
     * Gets the highest migration version that has been applied to a project.
     * This allows clients to determine where to start applying new migrations.
     * 
     * @param projectId the project identifier
     * @return a future that completes with the highest applied migration version, or null if no migrations have been applied
     */
    CompletableFuture<Integer> getLastAppliedMigrationVersion(String projectId);

    /**
     * Checks if a specific migration version has been applied to a project.
     * 
     * @param projectId the project identifier
     * @param version the migration version to check
     * @return a future that completes with true if the migration has been applied, false otherwise
     */
    CompletableFuture<Boolean> isMigrationApplied(String projectId, String version);
}
