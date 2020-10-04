package io.holunda.decision.runtime

import io.holunda.decision.model.api.DmnDiagramConverter
import io.holunda.decision.model.jackson.converter.JacksonDiagramConverter
import io.holunda.decision.runtime.cache.DmnDiagramEvaluationModelInMemoryRepository
import io.holunda.decision.model.api.evaluation.DmnDiagramEvaluationModelRepository
import io.holunda.decision.runtime.deployment.CamundaDecisionRepositoryServiceBean
import io.holunda.decision.runtime.evaluation.CamundaDecisionEvaluationServiceBean
import io.holunda.decision.runtime.query.CamundaDecisionQueryServiceBean
import org.apache.commons.lang3.builder.Builder
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl

open class CamundaDecisionRuntimeContext(
  val processEngineConfiguration: ProcessEngineConfigurationImpl,
  val dmnDiagramConverter: DmnDiagramConverter,
  val dmnDiagramEvaluationModelRepository: DmnDiagramEvaluationModelRepository
) {

  companion object {
    fun builder() = CamundaDecisionRuntimeContextBuilder()
  }

  val camundaDecisionQueryService = CamundaDecisionQueryServiceBean(
    repositoryService = processEngineConfiguration.repositoryService,
    diagramConverter = dmnDiagramConverter
  )

  val camundaDecisionRepositoryService = CamundaDecisionRepositoryServiceBean(
    repositoryService = processEngineConfiguration.repositoryService,
    dmnDiagramEvaluationModelRepository = dmnDiagramEvaluationModelRepository,
    diagramConverter = dmnDiagramConverter
  )

  val camundaDecisionEvaluationService = CamundaDecisionEvaluationServiceBean(
    repositoryService = camundaDecisionRepositoryService,
    decisionService = processEngineConfiguration.decisionService
  )


  val camundaDecisionService = CamundaDecisionServiceBean(
    repositoryService = camundaDecisionRepositoryService,
    queryService = camundaDecisionQueryService,
    evaluationService = camundaDecisionEvaluationService
  )

  val camundaDecisionProcessEnginePlugin = CamundaDecisionProcessEnginePlugin(
    evaluationModelRepository = dmnDiagramEvaluationModelRepository,
    diagramConverter = dmnDiagramConverter
  )

  val commandExecutorAdapter by lazy {
    CamundaDecisionRuntime.CommandExecutorAdapter(processEngineConfiguration)
  }

  class CamundaDecisionRuntimeContextBuilder : Builder<CamundaDecisionRuntimeContext> {

    private lateinit var processEngineConfiguration: ProcessEngineConfigurationImpl
    private var diagramConverter: DmnDiagramConverter = JacksonDiagramConverter
    private var dmnDiagramEvaluationModelRepository: DmnDiagramEvaluationModelRepository = DmnDiagramEvaluationModelInMemoryRepository()

    fun withProcessEngineConfiguration(processEngineConfiguration: ProcessEngineConfigurationImpl) = apply {
      this.processEngineConfiguration = processEngineConfiguration
    }


    fun withDmnDiagramEvaluationModelRepository(dmnDiagramEvaluationModelRepository: DmnDiagramEvaluationModelRepository) = apply {
      this.dmnDiagramEvaluationModelRepository = dmnDiagramEvaluationModelRepository;
    }

    override fun build(): CamundaDecisionRuntimeContext {
      return CamundaDecisionRuntimeContext(
        processEngineConfiguration = processEngineConfiguration,
        dmnDiagramConverter = diagramConverter,
        dmnDiagramEvaluationModelRepository = dmnDiagramEvaluationModelRepository
      )
    }
  }
}
