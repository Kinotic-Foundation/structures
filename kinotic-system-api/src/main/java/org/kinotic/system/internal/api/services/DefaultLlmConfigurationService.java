package org.kinotic.system.internal.api.services;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.domain.api.model.security.identity.Membership;
import org.kinotic.domain.api.services.SecretStorageService;
import org.kinotic.system.api.model.llm.LlmConfiguration;
import org.kinotic.system.api.services.LlmConfigurationService;
import org.kinotic.system.internal.api.repositories.LlmConfigurationRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DefaultLlmConfigurationService implements LlmConfigurationService {

    // no organization can take the platform's id, so the API keys never share a secret scope with an organization's
    private static final String SECRET_SCOPE = Membership.PLATFORM;

    private final LlmConfigurationRepository repository;
    private final SecretStorageService secretStorageService;

    @Override
    public Future<Page<LlmConfiguration>> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Future<LlmConfiguration> findById(String id) {
        Validate.notBlank(id, "id is required");
        return repository.findById(id);
    }

    @Override
    public Future<LlmConfiguration> findByName(String name) {
        Validate.notBlank(name, "name is required");
        return repository.findByName(name);
    }

    @Override
    public Future<LlmConfiguration> save(LlmConfiguration configuration, String apiKey) {
        Validate.notNull(configuration, "configuration is required");
        Validate.notBlank(configuration.getName(), "name is required");
        Validate.notNull(configuration.getProvider(), "provider is required");
        Validate.notBlank(configuration.getModel(), "model is required");
        boolean created = configuration.getId() == null;
        Date now = new Date();
        if (created) {
            configuration.setId(UUID.randomUUID().toString())
                         .setCreated(now);
        }
        configuration.setUpdated(now);
        // the row is written once the vault holds the key, so it never claims a key the vault refused
        return repository.findByName(configuration.getName())
                         .compose(named -> {
                             Validate.isTrue(named == null || named.getId().equals(configuration.getId()),
                                             "A configuration named '%s' already exists", configuration.getName());
                             Future<Boolean> held;
                             if (StringUtils.isNotBlank(apiKey)) {
                                 held = secretStorageService.setSecret(SECRET_SCOPE, configuration.getId(), apiKey).map(true);
                             } else if (created) {
                                 held = Future.succeededFuture(false);
                             } else {
                                 held = repository.findById(configuration.getId())
                                                  .map(existing -> existing != null && existing.isApiKeyHeld());
                             }
                             return held;
                         })
                         // refreshed before returning, since the console's table reads the configurations right after a save
                         .compose(held -> repository.saveSync(configuration.setApiKeyHeld(held)));
    }

    @Override
    public Future<String> findApiKey(String id) {
        return findById(id).compose(configuration -> configuration != null && configuration.isApiKeyHeld()
                ? secretStorageService.getSecret(SECRET_SCOPE, id)
                : Future.succeededFuture());
    }

    @Override
    public Future<Void> deleteById(String id) {
        return findById(id).compose(configuration -> {
            Future<Void> ret;
            if (configuration == null) {
                ret = Future.succeededFuture();
            } else {
                Future<Void> keyDeleted = configuration.isApiKeyHeld()
                        ? secretStorageService.deleteSecret(SECRET_SCOPE, id)
                        : Future.succeededFuture();
                ret = keyDeleted.compose(v -> repository.deleteByIdSync(id));
            }
            return ret;
        });
    }
}
