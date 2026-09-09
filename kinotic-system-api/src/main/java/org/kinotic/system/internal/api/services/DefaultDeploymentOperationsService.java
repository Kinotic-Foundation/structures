package org.kinotic.system.internal.api.services;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.kinotic.domain.api.model.Organization;
import org.kinotic.domain.api.services.OrganizationService;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.grind.api.model.JobDefinition;
import org.kinotic.grind.api.model.JobOwner;
import org.kinotic.grind.api.model.JobRunHandle;
import org.kinotic.grind.api.model.Tasks;
import org.kinotic.grind.api.services.JobService;
import org.kinotic.management.api.model.MicroserviceDeployment;
import org.kinotic.management.api.model.UiDeployment;
import org.kinotic.management.api.repositories.MicroserviceDeploymentRepository;
import org.kinotic.management.api.repositories.ProjectDeploymentRepository;
import org.kinotic.management.api.repositories.UiDeploymentRepository;
import org.kinotic.management.api.model.workload.WorkloadStatus;
import org.kinotic.system.api.services.DeploymentOperationsService;
import org.kinotic.system.api.services.OrganizationStorageProvisioner;
import org.kinotic.system.api.config.KinoticSystemApiProperties;
import org.kinotic.system.api.config.UiDeploymentProperties;
import org.kinotic.system.api.services.SiteStorageService;
import org.kinotic.system.api.services.UiDeploymentProvisioner;
import org.kinotic.system.api.services.WorkloadOrchestrationService;
import org.kinotic.system.api.services.WorkloadService;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultDeploymentOperationsService implements DeploymentOperationsService {

    /** Longer than deleting a site takes, and short enough that a leaked URL is soon worthless. */
    private static final Duration REMOVAL_URL_TTL = Duration.ofMinutes(15);

    private final MicroserviceDeploymentRepository microserviceDeploymentRepository;
    private final UiDeploymentRepository uiDeploymentRepository;
    private final WorkloadService workloadService;
    private final WorkloadOrchestrationService workloadOrchestrationService;
    private final ParticipantIdentityService participantIdentityService;
    private final UiDeploymentProvisioner uiDeploymentProvisioner;
    private final SiteStorageService siteStorageService;
    private final SiteWorkloadFactory siteWorkloadFactory;
    private final ProjectDeploymentRepository projectDeploymentRepository;
    private final KinoticSystemApiProperties properties;
    private final OrganizationStorageProvisioner organizationStorageProvisioner;
    private final OrganizationService organizationService;
    private final JobService jobService;

    @Override
    public Future<Void> restartMicroservice(String deploymentId) {
        return loadMicroservice(deploymentId)
                .compose(deployment -> {
                    if (deployment.getWorkloadId() == null) {
                        throw new IllegalStateException("Microservice " + deployment.getName()
                                + " has no workload to restart; deploy the project again");
                    }
                    return workloadService.findById(deployment.getWorkloadId())
                            .compose(workload -> {
                                if (workload == null) {
                                    throw new IllegalStateException("The workload of microservice " + deployment.getName()
                                            + " no longer exists; deploy the project again");
                                }
                                // restartWorkload only boots a stopped VM, so a running one is stopped first
                                Future<Void> stopped = workload.getStatus().isComplete()
                                        ? Future.succeededFuture()
                                        : workloadOrchestrationService.stopWorkload(workload.getId());
                                return stopped.compose(v -> workloadOrchestrationService.restartWorkload(workload.getId()));
                            });
                })
                .mapEmpty();
    }

    @Override
    public Future<Void> removeMicroservice(String deploymentId) {
        return loadMicroservice(deploymentId)
                .compose(deployment -> destroyWorkload(deployment)
                        .compose(v -> removeMachine(deployment))
                        // sync so the console's immediate re-query no longer lists it
                        .compose(v -> microserviceDeploymentRepository.deleteByIdSync(deployment.getId())));
    }

    // The workload may already be gone: destroyed with its node, or never created
    private Future<Void> destroyWorkload(MicroserviceDeployment deployment) {
        Future<Void> ret;
        if (deployment.getWorkloadId() == null) {
            ret = Future.succeededFuture();
        } else {
            ret = workloadOrchestrationService.destroyWorkload(deployment.getWorkloadId())
                    .recover(error -> {
                        log.warn("Workload {} of microservice {} could not be destroyed: {}",
                                 deployment.getWorkloadId(), deployment.getName(), error.getMessage());
                        return Future.succeededFuture();
                    });
        }
        return ret;
    }

    // An org member may already have removed the machine from the console
    private Future<Void> removeMachine(MicroserviceDeployment deployment) {
        Future<Void> ret;
        if (deployment.getMachineIdentityId() == null) {
            ret = Future.succeededFuture();
        } else {
            ret = participantIdentityService.deleteById(deployment.getMachineIdentityId())
                    .recover(error -> {
                        log.warn("Machine {} of microservice {} could not be removed: {}",
                                 deployment.getMachineIdentityId(), deployment.getName(), error.getMessage());
                        return Future.succeededFuture();
                    });
        }
        return ret;
    }

    @Override
    public Future<UiDeployment> checkUiSite(String deploymentId) {
        return loadUi(deploymentId)
                .compose(uiDeploymentProvisioner::checkProvisioning)
                .compose(checked -> uiDeploymentRepository.save(checked.setUpdated(new Date())));
    }

    @Override
    public Future<UiDeployment> provisionUiSite(String deploymentId) {
        return loadUi(deploymentId)
                .compose(uiDeploymentProvisioner::provision)
                .compose(row -> uiDeploymentRepository.save(row.setUpdated(new Date())));
    }

    @Override
    public Future<Void> removeUiSite(String deploymentId) {
        return loadUi(deploymentId)
                .compose(deployment -> deleteFiles(deployment)
                        // sync so the console's immediate re-query no longer lists it
                        .compose(v -> uiDeploymentRepository.deleteByIdSync(deployment.getId())));
    }

    /**
     * Deletes the site's directory through a removal workload on the node its project deploys
     * to, with a URL scoped to that directory. A project never deployed has no node, and its
     * site no files; a removal that fails leaves the files for a later publish of the same
     * label to adopt, and the workload for inspection.
     */
    private Future<Void> deleteFiles(UiDeployment deployment) {
        return projectDeploymentRepository.findById(deployment.getProjectId(), deployment.getOrganizationId())
                .compose(project -> {
                    Future<Void> ret;
                    if (project == null || project.getNodeId() == null) {
                        ret = Future.succeededFuture();
                    } else {
                        ret = siteStorageService.issueRemovalUrl(uiDeployment().resolveHostname(deployment.getId()), REMOVAL_URL_TTL)
                                .map(url -> siteWorkloadFactory.removal(deployment, project.getNodeId(), url))
                                .compose(workloadOrchestrationService::deployWorkload)
                                .compose(finished -> {
                                    Future<Void> removed;
                                    if (finished.getStatus() == WorkloadStatus.STOPPED && Integer.valueOf(0).equals(finished.getExitCode())) {
                                        removed = workloadOrchestrationService.destroyWorkload(finished.getId());
                                    } else {
                                        removed = Future.failedFuture(new IllegalStateException("Removal workload " + finished.getId()
                                                + " ended " + finished.getStatus() + " with exit code " + finished.getExitCode()
                                                + "; the workload is kept for log inspection"));
                                    }
                                    return removed;
                                });
                    }
                    return ret;
                })
                .recover(error -> {
                    log.warn("Files of site {} could not be deleted: {}", deployment.getId(), error.getMessage());
                    return Future.succeededFuture();
                });
    }

    /**
     * Runs the provisioning job: a task that provisions the organization's storage. It takes
     * minutes on Azure, so the job runs in the background and the task records its outcome on
     * the organization; the run itself is recorded as the organization's provisioning run,
     * where the console shows it. The task is idempotent, so a run started again does what an
     * earlier one left undone.
     */
    @Override
    public Future<Organization> provisionOrganization(String organizationId) {
        Validate.notBlank(organizationId, "organizationId is required");
        return organizationService.findById(organizationId)
                .compose(organization -> {
                    if (organization == null) {
                        throw new IllegalArgumentException("Organization not found: " + organizationId);
                    }
                    JobDefinition definition = JobDefinition.create("Provision organization " + organizationId)
                            .name("provision-organization-" + organizationId)
                            .version("1.0.0")
                            .task(Tasks.fromCallable("Provision storage",
                                                     () -> organizationStorageProvisioner.ensureStorage(organizationId)
                                                                                         .<Void>mapEmpty()
                                                                                         .toCompletionStage().toCompletableFuture()));
                    JobRunHandle handle = jobService.run(definition, JobOwner.ofOrganization(organizationId, null));
                    organization.setProvisioningJobRunId(handle.getJobRunId()).setUpdated(new Date());
                    // the run starts once its completion is subscribed, so the run id is on the
                    // record before the first task saves the organization
                    return organizationService.save(organization)
                            .onSuccess(saved -> handle.completion()
                                    .onSuccess(v -> log.info("Organization {} is provisioned", organizationId))
                                    .onFailure(error -> log.warn("Provisioning organization {} failed, see job run {}: {}",
                                                                 organizationId, handle.getJobRunId(), error.getMessage())));
                });
    }

    private Future<MicroserviceDeployment> loadMicroservice(String deploymentId) {
        Validate.notBlank(deploymentId, "deploymentId is required");
        return microserviceDeploymentRepository.findById(deploymentId)
                .map(deployment -> {
                    if (deployment == null) {
                        throw new IllegalArgumentException("Microservice deployment not found: " + deploymentId);
                    }
                    return deployment;
                });
    }

    private Future<UiDeployment> loadUi(String deploymentId) {
        Validate.notBlank(deploymentId, "deploymentId is required");
        return uiDeploymentRepository.findById(deploymentId)
                .map(deployment -> {
                    if (deployment == null) {
                        throw new IllegalArgumentException("UI deployment not found: " + deploymentId);
                    }
                    return deployment;
                });
    }

    private UiDeploymentProperties uiDeployment() {
        return properties.getSystemApi().getUiDeployment();
    }

}
