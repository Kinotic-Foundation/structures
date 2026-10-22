package org.kinotic.management.api.repositories;

import co.elastic.clients.elasticsearch._types.Conflicts;
import io.vertx.core.Future;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.crud.CursorPageable;
import org.kinotic.core.api.crud.Page;
import org.kinotic.domain.internal.api.repositories.AbstractApplicationScopedRepository;
import org.kinotic.domain.internal.api.services.CrudServiceTemplate;
import org.kinotic.management.api.model.deployment.ProjectDependencies;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Stores each project's {@link ProjectDependencies}, one document per project keyed by the project
 * id.
 */
@Component
public class ProjectDependenciesRepository extends AbstractApplicationScopedRepository<ProjectDependencies> {

    private static final String PACKAGES_FIELD = "packages";

    // The most versions of one package one listing returns; a package with more is listed page by page
    private static final int VERSIONS_PAGE_SIZE = 1_000;

    public ProjectDependenciesRepository(CrudServiceTemplate crudServiceTemplate) {
        super("kinotic_project_dependencies", ProjectDependencies.class, crudServiceTemplate);
    }

    /**
     * Deletes the project's tree when it lists the dependencies with the hash, waiting for the
     * deletion to be visible in search results.
     */
    public Future<Void> deleteIfGeneratedFrom(String projectId, String orgId, String dependencyHash) {
        Validate.notBlank(projectId, "projectId cannot be blank");
        Validate.notBlank(orgId, "orgId cannot be blank");
        Validate.notBlank(dependencyHash, "dependencyHash cannot be blank");
        // a tree rewritten while the delete runs lists newer dependencies, so it is kept rather than failing the delete
        return crudServiceTemplate.deleteByQuery(indexName, b -> b.routing(orgId)
                                                                 .refresh(true)
                                                                 .conflicts(Conflicts.Proceed)
                                                                 .query(composeOrgFilter(orgId,
                                                                                         termFilter("id", projectId),
                                                                                         termFilter("dependencyHash", dependencyHash))))
                                  .mapEmpty();
    }

    /**
     * Lists the trees of every organization, each carrying its id, organization, application and
     * package URLs; its positions in the package list are empty.
     *
     * @param pageable the page to return; sorted by {@code organizationId} then {@code id}, since an id
     *                 names one project only within its organization
     */
    public Future<Page<ProjectDependencies>> findAllOfEveryOrganization(CursorPageable pageable) {
        return findAll(pageable, b -> b.source(s -> s.filter(f -> f.includes("id", "organizationId", "applicationId", PACKAGES_FIELD))));
    }

    /**
     * Finds which of the projects, of any organization, have a tree. Each tree found carries only its
     * id and organization.
     *
     * @param projectIdsByOrganization the ids of the projects to look for, by organization id
     * @return a future emitting the trees found, in no particular order
     */
    public Future<List<ProjectDependencies>> findAllExisting(Map<String, ? extends Collection<String>> projectIdsByOrganization) {
        Validate.notNull(projectIdsByOrganization, "projectIdsByOrganization cannot be null");
        List<String> documentIds = new ArrayList<>();
        projectIdsByOrganization.forEach((orgId, ids) -> ids.forEach(id -> documentIds.add(composeDocumentId(id, orgId))));
        return crudServiceTemplate.findAllByIds(indexName, type, documentIds, "id", "organizationId");
    }

    /**
     * Lists the projects of every organization whose tree lists the package version. Each tree
     * carries only its id, organization and application; its package lists are empty.
     *
     * @param purl     a package URL with its version, as {@link ProjectDependencies#getPackages()} lists it
     * @param pageable the page to return; sorted by {@code organizationId} then {@code id}
     */
    public Future<Page<ProjectDependencies>> findAllHolding(String purl, CursorPageable pageable) {
        Validate.notBlank(purl, "purl cannot be blank");
        return findAll(pageable, b -> b.query(termFilter(PACKAGES_FIELD, purl))
                                       .source(s -> s.filter(f -> f.includes("id", "organizationId", "applicationId"))));
    }

    /**
     * Lists every version of a package the trees of every organization list, as the package URLs
     * they list, in ascending order. Can list a version no tree lists any longer.
     *
     * @param purl a package URL without a version, e.g. {@code pkg:npm/%40vitest/mocker}
     */
    public Future<List<String>> findAllVersionsOf(String purl) {
        Validate.notBlank(purl, "purl cannot be blank");
        return findAllVersionsOf(purl + "@", null, new ArrayList<>());
    }

    private Future<List<String>> findAllVersionsOf(String prefix, String after, List<String> found) {
        return crudServiceTemplate.termsEnum(indexName, PACKAGES_FIELD, prefix, after, VERSIONS_PAGE_SIZE)
                                  .compose(terms -> {
                                      found.addAll(terms);
                                      return terms.size() < VERSIONS_PAGE_SIZE
                                              ? Future.succeededFuture(found)
                                              : findAllVersionsOf(prefix, terms.getLast(), found);
                                  });
    }

}
