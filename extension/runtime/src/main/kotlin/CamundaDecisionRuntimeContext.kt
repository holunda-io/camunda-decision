package io.holunda.decision.runtime

import io.holunda.decision.model.api.CamundaDecisionEvaluationService
import io.holunda.decision.model.api.DmnDiagramConverter
import io.holunda.decision.model.jackson.converter.JacksonDiagramConverter
import io.holunda.decision.runtime.cache.DmnDiagramEvaluationModelInMemoryRepository
import io.holunda.decision.runtime.cache.DmnDiagramEvaluationModelRepository
import io.holunda.decision.runtime.deployment.CamundaDecisionRepositoryServiceBean
import io.holunda.decision.runtime.query.CamundaDecisionQueryServiceBean
import org.apache.commons.lang3.builder.Builder
import org.camunda.bpm.engine.ProcessEngineServices
import org.camunda.bpm.engine.RepositoryService

open class CamundaDecisionRuntimeContext(
  val repositoryService: RepositoryService,
  val dmnDiagramConverter: DmnDiagramConverter,
  val dmnDiagramEvaluationModelRepository: DmnDiagramEvaluationModelRepository
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

  val camundaDecisionProcessEnginePlugin = CamundaDecisionProcessEnginePlugin(
    dmnDiagramEvaluationModelRepository,
    dmnDiagramConverter
  )

  class CamundaDecisionRuntimeContextBuilder : Builder<CamundaDecisionRuntimeContext>{

    private lateinit var repositoryService: RepositoryService
    private var diagramConverter : DmnDiagramConverter = JacksonDiagramConverter
    private var dmnDiagramEvaluationModelRepository: DmnDiagramEvaluationModelRepository = DmnDiagramEvaluationModelInMemoryRepository()

    fun withProcessEngineServices(processEngineServices: ProcessEngineServices) = apply {
      withRepositoryService(processEngineServices.repositoryService)
    }

    fun withRepositoryService(repositoryService: RepositoryService) = apply {
      this.repositoryService = repositoryService
    }

    fun withDmnDiagramEvaluationModelRepository(dmnDiagramEvaluationModelRepository: DmnDiagramEvaluationModelRepository) = apply {
      this.dmnDiagramEvaluationModelRepository = dmnDiagramEvaluationModelRepository;
    }

    override fun build(): CamundaDecisionRuntimeContext {
      return CamundaDecisionRuntimeContext(
        repositoryService = repositoryService,
        dmnDiagramConverter = diagramConverter,
        dmnDiagramEvaluationModelRepository = dmnDiagramEvaluationModelRepository
      )
    }
  }
}
