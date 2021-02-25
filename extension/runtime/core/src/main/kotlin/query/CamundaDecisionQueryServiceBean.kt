package io.holunda.decision.runtime.query

import io.holunda.decision.model.api.CamundaDecisionQueryService
import io.holunda.decision.model.api.DecisionDefinitionKey
import io.holunda.decision.model.api.DmnDiagramConverter
import io.holunda.decision.model.api.element.DmnDiagram
import org.camunda.bpm.engine.RepositoryService
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance

class CamundaDecisionQueryServiceBean(
  private val repositoryService: RepositoryService,
  private val diagramConverter: DmnDiagramConverter
) : CamundaDecisionQueryService{

  fun findModelInstance(decisionDefinitionKey: DecisionDefinitionKey): DmnModelInstance {
    val definition = repositoryService.createDecisionDefinitionQuery()
      .decisionDefinitionKey(decisionDefinitionKey)
      .latestVersion()
      .singleResult()

    return repositoryService.getDmnModelInstance(definition.id)!!
  }

  override fun loadDiagram(decisionDefinitionId: String): DmnDiagram {
    val modelInstance = repositoryService.getDmnModelInstance(decisionDefinitionId)

    return diagramConverter.fromXml(Dmn.convertToString(modelInstance))
  }
}
