package org.kinotic.system.api.services;

import io.vertx.core.Future;
import org.kinotic.core.api.annotations.Publish;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.domain.api.model.SystemEmailTemplate;

/**
 * The emails UIs built on Kinotic send in their own name, each a {@link SystemEmailTemplate} that system users
 * manage: the invitation and sign-up verification emails a call names by id.
 * <p>
 * Published in the system zone, which only SYSTEM participants and the platform's own cluster members may address.
 */
@Publish
public interface SystemEmailTemplateService {

    /** Every template, a page at a time. */
    Future<Page<SystemEmailTemplate>> findAll(Pageable pageable);

    /** The template {@code id}, or {@code null} when none has that id. */
    Future<SystemEmailTemplate> findById(String id);

    /**
     * Saves a template, new or changed. Its sources must render with sample values of the invitation and
     * verification variables.
     *
     * @param template the template; when its id names an existing template, the one it replaces, keeping that one's
     *                 creation time
     * @return the template as saved, its UI base URL without a trailing slash
     * @throws IllegalArgumentException when a field is missing, the id is not lowercase letters, digits and interior
     *                                  dashes, the UI base URL is not an http or https URL, or a source does not render
     */
    Future<SystemEmailTemplate> save(SystemEmailTemplate template);

    /**
     * Deletes the template {@code id}; nothing happens for an unknown id.
     *
     * @throws IllegalArgumentException when {@code id} is {@link SystemEmailTemplate#DEFAULT_INVITATION_ID} or
     *                                  {@link SystemEmailTemplate#DEFAULT_VERIFICATION_ID}
     */
    Future<Void> deleteById(String id);
}
