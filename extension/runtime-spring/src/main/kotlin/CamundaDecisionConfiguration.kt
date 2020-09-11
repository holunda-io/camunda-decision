package io.holunda.decision.runtime.spring

import io.holunda.decision.model.api.CamundaDecisionRepositoryService
import io.holunda.decision.model.api.CamundaDecisionService
import io.holunda.decision.runtime.CamundaDecisionRuntimeContext
import io.holunda.decision.runtime.cache.DmnDiagramEvaluationModelInMemoryRepository
import io.holunda.decision.runtime.cache.DmnDiagramEvaluationModelRepository
import org.camunda.bpm.engine.RepositoryService
import org.springframework.context.annotation.Bean

class CamundaDecisionConfiguration {

  @Bean
  fun camundaDecisionRuntimeContext(
    repositoryService: RepositoryService,
    dmnDiagramEvaluationModelRepository: DmnDiagramEvaluationModelRepository) = CamundaDecisionRuntimeContext.builder()
    .withRepositoryService(repositoryService)
    .withDmnDiagramEvaluationModelRepository(dmnDiagramEvaluationModelRepository)
    .build()


  @Bean
  fun DmnDiagramEvaluationModelRepository() = DmnDiagramEvaluationModelInMemoryRepository()

  @Bean
  fun camundaDecisionDiagramConverter(runtimeContext: CamundaDecisionRuntimeContext) = runtimeContext.dmnDiagramConverter

  @Bean
  fun camundaDecisionDeploymentService(runtimeContext: CamundaDecisionRuntimeContext): CamundaDecisionRepositoryService = runtimeContext.camundaDecisionDeploymentService

  @Bean
  fun camundaDecisionQueryService(runtimeContext: CamundaDecisionRuntimeContext) = runtimeContext.camundaDecisionQueryService

  @Bean
  fun camundaDecisionService(runtimeContext: CamundaDecisionRuntimeContext): CamundaDecisionService = runtimeContext.camundaDecisionService

}
