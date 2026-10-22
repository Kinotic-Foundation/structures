package org.kinotic.management.api.repositories;

import io.vertx.core.Future;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.crud.CursorPageable;
import org.kinotic.core.api.crud.Page;
import org.kinotic.domain.internal.api.repositories.AbstractApplicationScopedRepository;
import org.kinotic.domain.internal.api.services.CrudServiceTemplate;
import org.kinotic.management.api.model.deployment.ProjectDependencies;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
     * Lists the trees of every organization.
     *
     * @param pageable the page to return; sorted by {@code organizationId} then {@code id}, since an id
     *                 names one project only within its organization
     */
    public Future<Page<ProjectDependencies>> findAll(CursorPageable pageable) {
        return findAll(pageable, null);
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
