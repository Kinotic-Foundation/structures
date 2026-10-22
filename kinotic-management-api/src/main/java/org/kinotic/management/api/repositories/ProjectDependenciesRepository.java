package org.kinotic.management.api.repositories;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import io.vertx.core.Future;
import jakarta.json.JsonString;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.crud.CursorPageable;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.domain.internal.api.repositories.AbstractApplicationScopedRepository;
import org.kinotic.domain.internal.api.services.CrudServiceTemplate;
import org.kinotic.management.api.model.deployment.ProjectDependencies;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores each project's {@link ProjectDependencies}, one document per project keyed by the project
 * id.
 */
@Component
public class ProjectDependenciesRepository extends AbstractApplicationScopedRepository<ProjectDependencies> {

    private static final String PACKAGES_FIELD = "packages";

    private static final String HELD_FIELD = "held";

    // The packages the tree lists among params.versions
    private static final String HELD_SCRIPT = """
            List held = new ArrayList();
            for (String purl : doc['packages']) {
                if (params.versions.containsKey(purl)) {
                    held.add(purl);
                }
            }
            return held;
            """;

    // The most versions of one package one listing returns; a package with more is listed page by page
    private static final int VERSIONS_PAGE_SIZE = 1_000;

    public ProjectDependenciesRepository(CrudServiceTemplate crudServiceTemplate) {
        super("kinotic_project_dependencies", ProjectDependencies.class, crudServiceTemplate);
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
     * Lists the trees of every organization that list any of the package versions. Each tree carries
     * its id, organization and application, and of its packages only the given versions it lists.
     *
     * @param purls    package URLs with their versions, as {@link ProjectDependencies#getPackages()} lists them
     * @param pageable the page to return; sorted by {@code organizationId} then {@code id}
     */
    public Future<Page<ProjectDependencies>> findAllHoldingAny(Collection<String> purls, CursorPageable pageable) {
        Validate.notEmpty(purls, "purls cannot be empty");
        // a map, so the script's lookup of each package the tree lists is a containsKey
        Map<String, Boolean> versions = new HashMap<>();
        purls.forEach(purl -> versions.put(purl, true));
        return crudServiceTemplate.search(indexName,
                                          pageable,
                                          type,
                                          b -> b.query(holdingAny(purls))
                                                .source(s -> s.filter(f -> f.includes("id", "organizationId", "applicationId")))
                                                .scriptFields(HELD_FIELD, sf -> sf.script(sc -> sc.source(src -> src.scriptString(HELD_SCRIPT))
                                                                                                    .params("versions", JsonData.of(versions)))),
                                          hit -> {
                                              ProjectDependencies tree = hit.source();
                                              JsonData held = hit.fields().get(HELD_FIELD);
                                              if (held != null) {
                                                  tree.setPackages(held.toJson().asJsonArray().getValuesAs(JsonString::getString));
                                              }
                                              return tree;
                                          });
    }

    /**
     * Finds which of the projects, of any organization, have a tree listing any of the package
     * versions. Each tree found carries only its id and organization.
     *
     * @param projectIdsByOrganization the ids of the projects to look for, by organization id
     * @param purls                    package URLs with their versions, as {@link ProjectDependencies#getPackages()} lists them
     * @return a future emitting the trees found, in no particular order
     */
    public Future<List<ProjectDependencies>> findAllHoldingAny(Map<String, ? extends Collection<String>> projectIdsByOrganization,
                                                               Collection<String> purls) {
        Validate.notNull(projectIdsByOrganization, "projectIdsByOrganization cannot be null");
        Validate.notEmpty(purls, "purls cannot be empty");
        List<String> documentIds = new ArrayList<>();
        projectIdsByOrganization.forEach((orgId, ids) -> ids.forEach(id -> documentIds.add(composeDocumentId(id, orgId))));
        Future<List<ProjectDependencies>> ret;
        if (documentIds.isEmpty()) {
            ret = Future.succeededFuture(List.of());
        } else {
            ret = crudServiceTemplate.search(indexName,
                                             Pageable.ofSize(documentIds.size()),
                                             type,
                                             b -> b.query(q -> q.bool(bool -> bool.filter(f -> f.ids(i -> i.values(documentIds)))
                                                                                  .filter(holdingAny(purls))))
                                                   .source(s -> s.filter(f -> f.includes("id", "organizationId"))))
                                     .map(Page::getContent);
        }
        return ret;
    }

    private static Query holdingAny(Collection<String> purls) {
        List<FieldValue> values = purls.stream().map(FieldValue::of).toList();
        return Query.of(q -> q.terms(t -> t.field(PACKAGES_FIELD).terms(v -> v.value(values))));
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
