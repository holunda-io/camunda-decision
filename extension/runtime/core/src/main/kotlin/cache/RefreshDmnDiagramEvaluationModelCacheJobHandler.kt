package io.holunda.decision.runtime.cache

import io.holunda.decision.model.api.DmnDiagramConverter
import io.holunda.decision.model.api.element.DmnDiagram
import io.holunda.decision.model.api.evaluation.DmnDiagramEvaluationModel
import io.holunda.decision.model.api.evaluation.DmnDiagramEvaluationModelRepository
import io.holunda.decision.runtime.CamundaDecisionRuntime
import cache.DmnModelType
import org.camunda.bpm.engine.impl.interceptor.Command
import org.camunda.bpm.engine.impl.interceptor.CommandContext
import org.camunda.bpm.engine.impl.jobexecutor.JobHandler
import org.camunda.bpm.engine.impl.jobexecutor.JobHandlerConfiguration
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity
import org.camunda.bpm.engine.impl.persistence.entity.JobEntity
import org.camunda.bpm.engine.impl.persistence.entity.MessageEntity
import org.slf4j.LoggerFactory

class RefreshDmnDiagramEvaluationModelCacheJobHandler(
  private val evaluationModelRepository: DmnDiagramEvaluationModelRepository,
  private val diagramConverter: DmnDiagramConverter
) : JobHandler<RefreshDmnDiagramEvaluationModelCacheConfiguration> {

  companion object {
    const val TYPE = "refreshDmnDiagramEvaluationModelCacheJobHandler"
    private val logger = LoggerFactory.getLogger(RefreshDmnDiagramEvaluationModelCacheJobHandler::class.java)

    fun createJob(adapter: CamundaDecisionRuntime.CommandExecutorAdapter, command: RefreshDmnDiagramEvaluationModelCacheCommand) = adapter.execute(command)
  }

  override fun execute(configuration: RefreshDmnDiagramEvaluationModelCacheConfiguration, execution: ExecutionEntity?, commandContext: CommandContext, tenantId: String?) {
    val repositoryService = commandContext.processEngineConfiguration.repositoryService

    val modelData = configuration.modelType.retrieveModelData(repositoryService, diagramConverter, configuration.key)

    evaluationModelRepository.save(DmnDiagramEvaluationModel(
      diagramId = modelData.diagram.id,
      name = modelData.diagram.name,
      resourceName = modelData.diagram.resourceName,
      deploymentId = modelData.deploymentId,
      deploymentTime = repositoryService.createDeploymentQuery().deploymentId(modelData.deploymentId).singleResult().deploymentTime,
      resultType = modelData.diagram.resultTable.resultType,
      decisionDefinitionId = modelData.resultDefinitionId,
      inputs = modelData.diagram.requiredInputs,
      outputs = modelData.diagram.resultTable.header.outputs.toSet()
    )).let {
      logger.info("saved evaluationModel: $it")
    }
  }

  override fun getType() = TYPE
  override fun onDelete(configuration: RefreshDmnDiagramEvaluationModelCacheConfiguration?, jobEntity: JobEntity?) {}
  override fun newConfiguration(canonicalString: String) = RefreshDmnDiagramEvaluationModelCacheConfiguration.parse(canonicalString)

}

data class RefreshDmnDiagramEvaluationModelCacheCommand(
  val key: String,
  val modelType: DmnModelType
) : Command<String> {
  override fun execute(commandContext: CommandContext): String {
    val message = MessageEntity()
    message.init(commandContext)
    message.jobHandlerType = RefreshDmnDiagramEvaluationModelCacheJobHandler.TYPE
    message.jobHandlerConfiguration = RefreshDmnDiagramEvaluationModelCacheConfiguration(key, modelType)
    commandContext.jobManager.send(message)

    return message.id
  }
}

data class RefreshDmnDiagramEvaluationModelCacheConfiguration(
  val key: String,
  val modelType: DmnModelType
) : JobHandlerConfiguration {

  companion object {
    fun parse(canonicalString: String): RefreshDmnDiagramEvaluationModelCacheConfiguration {
      val (key, type) = canonicalString.split("#")
      return RefreshDmnDiagramEvaluationModelCacheConfiguration(key, DmnModelType.valueOf(type))
    }
  }

  override fun toCanonicalString() = "$key#$modelType"

  internal data class ModelData(
    val tableKeysToDefinitionIds: Map<String, String>,
    val diagram: DmnDiagram,
    val resultDefinitionId: String,
    val deploymentId: String
  )
}
