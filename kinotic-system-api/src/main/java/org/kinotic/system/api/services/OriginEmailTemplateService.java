package org.kinotic.system.api.services;

import io.vertx.core.Future;
import org.kinotic.core.api.annotations.Publish;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.domain.api.model.OriginEmailTemplate;

/**
 * The emails each UI built on Kinotic sends in its own name, each an {@link OriginEmailTemplate} that system users
 * manage: the invitation and sign-up verification emails of the flows started from a page at the template's origin.
 * <p>
 * Published in the system zone, which only SYSTEM participants and the platform's own cluster members may address.
 */
@Publish
public interface OriginEmailTemplateService {

    /** Every template, a page at a time. */
    Future<Page<OriginEmailTemplate>> findAll(Pageable pageable);

    /** The template {@code id}, or {@code null} when none has that id. */
    Future<OriginEmailTemplate> findById(String id);

    /**
     * Saves a template, new or changed. Its sources must render with sample values of its kind's variables.
     *
     * @param template the template, whose origin and kind no other template has; with an id, the existing template it
     *                 replaces, keeping that one's creation time
     * @return the template as saved, with its id and its origin in lower case
     * @throws IllegalArgumentException when a field is missing, the origin is not a scheme and host with no path, or a
     *                                  source does not render
     */
    Future<OriginEmailTemplate> save(OriginEmailTemplate template);

    /** Deletes the template {@code id}, so its UI sends the portal's instead; nothing happens for an unknown id. */
    Future<Void> deleteById(String id);
}
