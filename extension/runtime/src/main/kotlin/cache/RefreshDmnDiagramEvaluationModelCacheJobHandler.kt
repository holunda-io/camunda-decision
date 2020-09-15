package io.holunda.decision.runtime.cache

import io.holunda.decision.model.api.DecisionDefinitionId
import io.holunda.decision.model.api.DmnDiagramConverter
import io.holunda.decision.model.api.evaluation.DmnDiagramEvaluationModel
import org.camunda.bpm.engine.impl.interceptor.Command
import org.camunda.bpm.engine.impl.interceptor.CommandContext
import org.camunda.bpm.engine.impl.jobexecutor.JobHandler
import org.camunda.bpm.engine.impl.jobexecutor.JobHandlerConfiguration
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity
import org.camunda.bpm.engine.impl.persistence.entity.JobEntity
import org.camunda.bpm.engine.impl.persistence.entity.MessageEntity
import org.camunda.bpm.model.dmn.Dmn
import org.slf4j.LoggerFactory

class RefreshDmnDiagramEvaluationModelCacheJobHandler (
  private val evaluationModelRepository: DmnDiagramEvaluationModelRepository,
  private val diagramConverter: DmnDiagramConverter
) : JobHandler<RefreshDmnDiagramEvaluationModelCacheConfiguration> {

  companion object {
    const val TYPE = "refreshDmnDiagramEvaluationModelCacheJobHandler"
    private val logger = LoggerFactory.getLogger(RefreshDmnDiagramEvaluationModelCacheJobHandler::class.java)
  }

  override fun execute(configuration: RefreshDmnDiagramEvaluationModelCacheConfiguration, execution: ExecutionEntity?, commandContext: CommandContext, tenantId: String?) {
    val repositoryService =  commandContext.processEngineConfiguration.repositoryService

    val def = repositoryService.createDecisionRequirementsDefinitionQuery()
      .decisionRequirementsDefinitionKey(configuration.decisionRequirementDefinitionKey)
      .singleResult()

    if (def == null) {
      logger.warn("No definition found for $configuration")
      return
    }

    val tableKeysToDefinitionIds = repositoryService.createDecisionDefinitionQuery()
      .decisionRequirementsDefinitionId(def.id)
      .list().map { it.key to it.id }.toMap()

    val diagram = diagramConverter.fromXml(Dmn.convertToString(repositoryService.getDmnModelInstance(tableKeysToDefinitionIds.values.first())))
    val decisionDefinitionId: DecisionDefinitionId = tableKeysToDefinitionIds[diagram.resultTable.decisionDefinitionKey]!!

    evaluationModelRepository.save(DmnDiagramEvaluationModel(
      diagramId = diagram.id,
      name = diagram.name,
      resourceName = diagram.resourceName,
      deploymentId = def.deploymentId,
      deploymentTime = repositoryService.createDeploymentQuery().deploymentId(def.deploymentId).singleResult().deploymentTime,
      resultType = diagram.resultTable.resultType,
      decisionDefinitionId = decisionDefinitionId,
      inputs = diagram.requiredInputs,
      outputs = diagram.resultTable.header.outputs.toSet()
    ))
  }

  override fun getType() = TYPE
  override fun onDelete(configuration: RefreshDmnDiagramEvaluationModelCacheConfiguration?, jobEntity: JobEntity?) {}
  override fun newConfiguration(canonicalString: String) = RefreshDmnDiagramEvaluationModelCacheConfiguration(canonicalString)

}

data class RefreshDmnDiagramEvaluationModelCacheCommand(
  val decisionRequirementDefinitionKey: String
) : Command<String> {
  override fun execute(commandContext: CommandContext): String {
    val message = MessageEntity()
    message.init(commandContext)
    message.jobHandlerType = RefreshDmnDiagramEvaluationModelCacheJobHandler.TYPE
    message.jobHandlerConfiguration = RefreshDmnDiagramEvaluationModelCacheConfiguration(this.decisionRequirementDefinitionKey)
    commandContext.jobManager.send(message)

    return message.id
  }

}

data class RefreshDmnDiagramEvaluationModelCacheConfiguration(
  val decisionRequirementDefinitionKey: String
) : JobHandlerConfiguration {
  override fun toCanonicalString() = decisionRequirementDefinitionKey
}
