package io.holunda.decision.runtime.spring

import io.holunda.decision.model.api.CamundaDecisionRepositoryService
import io.holunda.decision.model.api.CamundaDecisionService
import io.holunda.decision.model.api.evaluation.DmnDiagramEvaluationModelRepository
import io.holunda.decision.model.jackson.converter.JacksonDiagramConverter
import io.holunda.decision.runtime.CamundaDecisionProcessEnginePlugin
import io.holunda.decision.runtime.CamundaDecisionRuntimeContext
import io.holunda.decision.runtime.cache.DmnDiagramEvaluationModelInMemoryRepository
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl
import org.springframework.context.annotation.Bean

class CamundaDecisionConfiguration {

  @Bean
  fun camundaDecisionRuntimeContext(
    processEngineConfiguration: ProcessEngineConfigurationImpl,
    dmnDiagramEvaluationModelRepository: DmnDiagramEvaluationModelRepository) = CamundaDecisionRuntimeContext.builder()
    .withProcessEngineConfiguration(processEngineConfiguration)
    .withDmnDiagramEvaluationModelRepository(dmnDiagramEvaluationModelRepository)
    .build()

  @Bean
  fun dmnDiagramEvaluationModelRepository() = DmnDiagramEvaluationModelInMemoryRepository()

  @Bean
  fun camundaDecisionDiagramConverter(runtimeContext: CamundaDecisionRuntimeContext) = runtimeContext.dmnDiagramConverter

  @Bean
  fun camundaDecisionDeploymentService(runtimeContext: CamundaDecisionRuntimeContext): CamundaDecisionRepositoryService = runtimeContext.camundaDecisionRepositoryService

  @Bean
  fun camundaDecisionQueryService(runtimeContext: CamundaDecisionRuntimeContext) = runtimeContext.camundaDecisionQueryService

  @Bean
  fun camundaDecisionService(runtimeContext: CamundaDecisionRuntimeContext): CamundaDecisionService = runtimeContext.camundaDecisionService

  @Bean
  fun camundaDecisionPlugin() = CamundaDecisionProcessEnginePlugin(
    dmnDiagramEvaluationModelRepository(),
    JacksonDiagramConverter
  )
}
