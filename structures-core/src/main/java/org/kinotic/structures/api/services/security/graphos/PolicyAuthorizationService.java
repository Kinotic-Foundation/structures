package org.kinotic.structures.api.services.security.graphos;

import org.kinotic.structures.api.domain.SecurityContext;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.idl.decorators.EntityServiceDecorator;
import org.kinotic.structures.api.domain.idl.decorators.EntityServiceDecoratorsConfig;
import org.kinotic.structures.api.domain.idl.decorators.EntityServiceDecoratorsDecorator;
import org.kinotic.structures.api.domain.idl.decorators.PolicyDecorator;
import org.kinotic.structures.api.services.security.AuthorizationService;
import org.kinotic.structures.internal.api.services.impl.EntityOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class PolicyAuthorizationService implements AuthorizationService {

    private final List<String> createPolicies = new ArrayList<>();
    private final List<String> readPolicies = new ArrayList<>();
    private final List<String> updatePolicies = new ArrayList<>();
    private final List<String> deletePolicies = new ArrayList<>();

    private final Map<EntityOperation, List<String>> operationPolicies = new HashMap<>();

    public PolicyAuthorizationService(Structure structure,
                                      PolicyAuthorizer policyAuthorizer) {

        // Check if we have any policy decorators to apply to operations
        EntityServiceDecoratorsDecorator decorators = structure.getEntityDefinition()
                                                               .findDecorator(EntityServiceDecoratorsDecorator.class);

        if(decorators != null){
            EntityServiceDecoratorsConfig config = decorators.getConfig();

            createPolicies.addAll(extractPolicies(config.getAllCreate()));
            readPolicies.addAll(extractPolicies(config.getAllRead()));
            updatePolicies.addAll(extractPolicies(config.getAllUpdate()));
            deletePolicies.addAll(extractPolicies(config.getAllDelete()));

            operationPolicies.put(EntityOperation.BULK_SAVE, extractPolicies(config.getBulkSave()));
            operationPolicies.put(EntityOperation.BULK_UPDATE, extractPolicies(config.getBulkUpdate()));
            operationPolicies.put(EntityOperation.COUNT, extractPolicies(config.getCount()));
            operationPolicies.put(EntityOperation.COUNT_BY_QUERY, extractPolicies(config.getCountByQuery()));
            operationPolicies.put(EntityOperation.DELETE_BY_ID, extractPolicies(config.getDeleteById()));
            operationPolicies.put(EntityOperation.DELETE_BY_QUERY, extractPolicies(config.getDeleteByQuery()));
            operationPolicies.put(EntityOperation.FIND_ALL, extractPolicies(config.getFindAll()));
            operationPolicies.put(EntityOperation.FIND_BY_ID, extractPolicies(config.getFindById()));
            operationPolicies.put(EntityOperation.FIND_BY_IDS, extractPolicies(config.getFindByIds()));
            operationPolicies.put(EntityOperation.SYNC_INDEX, extractPolicies(config.getSyncIndex()));
            operationPolicies.put(EntityOperation.SAVE, extractPolicies(config.getSave()));
            operationPolicies.put(EntityOperation.SEARCH, extractPolicies(config.getSearch()));
            operationPolicies.put(EntityOperation.UPDATE, extractPolicies(config.getUpdate()));
        }

    }

    @Override
    public CompletableFuture<Void> authorize(String action, SecurityContext securityContext) {
        EntityOperation operation = EntityOperation.fromMethodName(action);
        List<String> policies = operationPolicies.get(operation);
        List<PolicyAuthorizationRequest> requests = new ArrayList<>();
        for (String policy : policies) {

        }

        return null;
    }

    private List<String> extractPolicies(List<EntityServiceDecorator> decorators){
        List<String> policies = new ArrayList<>();
        for (EntityServiceDecorator decorator : decorators) {
            if(decorator instanceof PolicyDecorator policyDecorator) {
                policies.addAll(extractLists(policyDecorator.getPolicies()));
            }
        }
        return policies;
    }

    private List<String> extractLists(List<List<String>> policies){
        return policies.stream()
                       .flatMap(List::stream)
                       .toList();
    }

}
