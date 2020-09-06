package io.holunda.decision.runtime

import io.holunda.decision.model.api.CamundaDecisionEvaluationService
import io.holunda.decision.model.api.DmnDiagramConverter
import io.holunda.decision.model.jackson.converter.JacksonDiagramConverter
import io.holunda.decision.runtime.deployment.CamundaDecisionRepositoryServiceBean
import io.holunda.decision.runtime.query.CamundaDecisionQueryServiceBean
import org.apache.commons.lang3.builder.Builder
import org.camunda.bpm.engine.ProcessEngineServices
import org.camunda.bpm.engine.RepositoryService

class CamundaDecisionRuntimeContext(
  val repositoryService: RepositoryService,
  val dmnDiagramConverter: DmnDiagramConverter
) {

  companion object {
    fun builder() = CamundaDecisionRuntimeContextBuilder()
  }

  val camundaDecisionQueryService = CamundaDecisionQueryServiceBean(
    repositoryService,
    dmnDiagramConverter
  )

  val camundaDecisionEvaluationService = object : CamundaDecisionEvaluationService {}

  val camundaDecisionDeploymentService = CamundaDecisionRepositoryServiceBean(repositoryService, dmnDiagramConverter)

  val camundaDecisionService = CamundaDecisionServiceBean(
    camundaDecisionDeploymentService, camundaDecisionQueryService, camundaDecisionEvaluationService
  )

  class CamundaDecisionRuntimeContextBuilder : Builder<CamundaDecisionRuntimeContext>{

    private lateinit var repositoryService: RepositoryService
    private var diagramConverter : DmnDiagramConverter = JacksonDiagramConverter

    fun withProcessEngineServices(processEngineServices: ProcessEngineServices) = apply {
      withRepositoryService(processEngineServices.repositoryService)
    }

    fun withRepositoryService(repositoryService: RepositoryService) = apply {
      this.repositoryService = repositoryService
    }

    override fun build(): CamundaDecisionRuntimeContext {
      return CamundaDecisionRuntimeContext(
        repositoryService = repositoryService,
        dmnDiagramConverter = diagramConverter
      )
    }
  }
}
