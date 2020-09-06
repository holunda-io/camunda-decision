package io.holunda.decision.runtime.spring

import io.holunda.decision.model.api.CamundaDecisionRepositoryService
import io.holunda.decision.model.api.CamundaDecisionService
import io.holunda.decision.model.jackson.converter.JacksonDiagramConverter
import io.holunda.decision.runtime.CamundaDecisionRuntimeContext
import org.camunda.bpm.engine.RepositoryService
import org.springframework.context.annotation.Bean

class CamundaDecisionConfiguration {

  @Bean
  fun camundaDecisionRuntimeContext(repositoryService: RepositoryService) = CamundaDecisionRuntimeContext(
    repositoryService = repositoryService,
    dmnDiagramConverter = JacksonDiagramConverter
  )

  @Bean
  fun camundaDecisionDiagramConverter(runtimeContext: CamundaDecisionRuntimeContext) = runtimeContext.dmnDiagramConverter

  @Bean
  fun camundaDecisionDeploymentService(runtimeContext: CamundaDecisionRuntimeContext) : CamundaDecisionRepositoryService = runtimeContext.camundaDecisionDeploymentService

  @Bean
  fun camundaDecisionQueryService(runtimeContext: CamundaDecisionRuntimeContext) = runtimeContext.camundaDecisionQueryService

  @Bean
  fun camundaDecisionService(runtimeContext: CamundaDecisionRuntimeContext): CamundaDecisionService = runtimeContext.camundaDecisionService

}
