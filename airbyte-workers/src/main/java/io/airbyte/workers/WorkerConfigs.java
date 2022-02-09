/*
 * Copyright (c) 2021 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.workers;

import io.airbyte.config.Configs;
import io.airbyte.config.ResourceRequirements;
import io.airbyte.config.TolerationPOJO;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WorkerConfigs {

  private final Configs.WorkerEnvironment workerEnvironment;
  private final ResourceRequirements resourceRequirements;
  private final List<TolerationPOJO> workerKubeTolerations;
  private final Map<String, String> workerKubeNodeSelectors;
  private final String jobImagePullSecret;
  private final String jobImagePullPolicy;
  private final String jobSocatImage;
  private final String jobBusyboxImage;
  private final String jobCurlImage;
  private final Map<String, String> envMap;
  private final Duration workerStatusCheckInterval;

  private static final Duration DEFAULT_WORKER_STATUS_CHECK_INTERVAL = Duration.ofSeconds(30);
  private static final Duration CHECK_WORKER_STATUS_CHECK_INTERVAL = Duration.ofSeconds(1);

  /**
   * Constructs a job-type-agnostic WorkerConfigs. For WorkerConfigs customized for specific
   * job-types, use static `build*JOBTYPE*WorkerConfigs` method if one exists.
   */
  public WorkerConfigs(final Configs configs) {
    this(configs.getWorkerEnvironment(),
        new ResourceRequirements()
            .withCpuRequest(configs.getJobMainContainerCpuRequest())
            .withCpuLimit(configs.getJobMainContainerCpuLimit())
            .withMemoryRequest(configs.getJobMainContainerMemoryRequest())
            .withMemoryLimit(configs.getJobMainContainerMemoryLimit()),
        configs.getJobKubeTolerations(),
        configs.getJobKubeNodeSelectors(),
        configs.getJobKubeMainContainerImagePullSecret(),
        configs.getJobKubeMainContainerImagePullPolicy(),
        configs.getJobKubeSocatImage(),
        configs.getJobKubeBusyboxImage(),
        configs.getJobKubeCurlImage(),
        configs.getJobDefaultEnvMap(),
        DEFAULT_WORKER_STATUS_CHECK_INTERVAL);
  }

  /**
   * Builds a WorkerConfigs with some configs that are specific to the Check job type.
   */
  public static WorkerConfigs buildCheckWorkerConfigs(final Configs configs) {
    return new WorkerConfigs(
        configs.getWorkerEnvironment(),
        new ResourceRequirements()
            .withCpuRequest(configs.getJobMainContainerCpuRequest())
            .withCpuLimit(configs.getJobMainContainerCpuLimit())
            .withMemoryRequest(configs.getJobMainContainerMemoryRequest())
            .withMemoryLimit(configs.getJobMainContainerMemoryLimit()),
        configs.getJobKubeTolerations(),
        configs.getCheckJobKubeNodeSelectors(),
        configs.getJobKubeMainContainerImagePullSecret(),
        configs.getJobKubeMainContainerImagePullPolicy(),
        configs.getJobKubeSocatImage(),
        configs.getJobKubeBusyboxImage(),
        configs.getJobKubeCurlImage(),
        configs.getJobDefaultEnvMap(),
        CHECK_WORKER_STATUS_CHECK_INTERVAL);
  }

  public Configs.WorkerEnvironment getWorkerEnvironment() {
    return workerEnvironment;
  }

  public ResourceRequirements getResourceRequirements() {
    return resourceRequirements;
  }

  public List<TolerationPOJO> getWorkerKubeTolerations() {
    return workerKubeTolerations;
  }

  public Map<String, String> getworkerKubeNodeSelectors() {
    return workerKubeNodeSelectors;
  }

  public String getJobImagePullSecret() {
    return jobImagePullSecret;
  }

  public String getJobImagePullPolicy() {
    return jobImagePullPolicy;
  }

  public String getJobSocatImage() {
    return jobSocatImage;
  }

  public String getJobBusyboxImage() {
    return jobBusyboxImage;
  }

  public String getJobCurlImage() {
    return jobCurlImage;
  }

  public Map<String, String> getEnvMap() {
    return envMap;
  }

  public Duration getWorkerStatusCheckInterval() {
    return workerStatusCheckInterval;
  }

}
