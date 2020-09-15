package io.holunda.decision.runtime.deployment

import io.holunda.decision.model.api.*
import io.holunda.decision.model.api.element.DmnDiagram
import io.holunda.decision.model.api.evaluation.DmnDiagramEvaluationModel
import org.camunda.bpm.engine.RepositoryService
import org.camunda.bpm.engine.repository.DecisionRequirementsDefinition
import org.camunda.bpm.engine.repository.Deployment
import org.camunda.bpm.model.dmn.Dmn
import java.util.*

/**
 * Deploys DmnDiagram using RepositoryService.
 */
class CamundaDecisionRepositoryServiceBean(
  private val repositoryService: RepositoryService,
  private val diagramConverter: DmnDiagramConverter
) : CamundaDecisionRepositoryService {

  override fun findAllModels(): List<DmnDiagramEvaluationModel> = repositoryService
    .createDecisionRequirementsDefinitionQuery()
    .latestVersion()
    .list()
    .map { load(it) }

  private fun load(requirementsDefinition: DecisionRequirementsDefinition): DmnDiagramEvaluationModel {
    val tableKeysToDefinitionIds = repositoryService.createDecisionDefinitionQuery()
      .decisionRequirementsDefinitionId(requirementsDefinition.id)
      .list().map { it.key to it.id }.toMap()

    val diagram = diagramConverter.fromXml(Dmn.convertToString(repositoryService.getDmnModelInstance(tableKeysToDefinitionIds.values.first())))
    val decisionDefinitionId: DecisionDefinitionId = tableKeysToDefinitionIds[diagram.resultTable.decisionDefinitionKey]!!

    return DmnDiagramEvaluationModel(
      diagramId = diagram.id,
      name = diagram.name,
      resourceName = diagram.resourceName,
      deploymentId = requirementsDefinition.deploymentId,
      deploymentTime = repositoryService.createDeploymentQuery().deploymentId(requirementsDefinition.deploymentId).singleResult().deploymentTime,
      resultType = diagram.resultTable.resultType,
      decisionDefinitionId = decisionDefinitionId,
      inputs = diagram.requiredInputs,
      outputs = diagram.resultTable.header.outputs.toSet()
    )

  }


  override fun deploy(diagrams: List<DmnDiagram>): DmnDiagramDeployment {
    val deploymentBuilder = repositoryService.createDeployment()

    diagrams.map { it.resourceName to Dmn.readModelFromStream(diagramConverter.toXml(it).byteInputStream()) }
      .forEach {
        deploymentBuilder.addModelInstance(it.first, it.second)
      }

    return deploymentBuilder.deploy().let { deployment ->
      DmnDiagramDeployment(
        id = deployment.id,
        name = deployment.name,
        deploymentTime = deployment.deploymentTime ?: Date(),
        source = deployment.source,
        tenantId = deployment.tenantId,
        deployedDiagrams = diagrams.map { loadDeployedDiagram(it, deployment) }
      )
    }
  }

  override fun findModel(diagramId: Id): Optional<DmnDiagramEvaluationModel> {
    TODO("Not yet implemented")
  }

  private fun loadDeployedDiagram(diagram: DmnDiagram, deployment: Deployment): DmnDiagramEvaluationModel {
    // finds all tables included in one diagram xml
    val resultDecisionDefinion = repositoryService.createDecisionDefinitionQuery()
      .deploymentId(deployment.id)
      .decisionDefinitionResourceName(diagram.resourceName)
      .decisionDefinitionKey(diagram.resultTable.decisionDefinitionKey)
      .singleResult()

    return DmnDiagramEvaluationModel(
      diagramId = diagram.id,
      name = diagram.name,
      resourceName = diagram.resourceName,
      deploymentId = deployment.id,
      deploymentTime = deployment.deploymentTime,
      resultType = diagram.resultTable.resultType,
      decisionDefinitionId = resultDecisionDefinion.id,
      inputs = diagram.requiredInputs,
      outputs = diagram.resultTable.header.outputs.toSet()
    )
  }
}
