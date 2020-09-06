package io.holunda.decision.runtime.query

import io.holunda.decision.model.api.CamundaDecisionQueryService
import io.holunda.decision.model.api.DecisionDefinitionKey
import io.holunda.decision.model.api.DmnDiagramConverter
import io.holunda.decision.model.api.Id
import io.holunda.decision.model.api.element.DmnDiagram
import org.camunda.bpm.engine.RepositoryService
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance
import java.io.BufferedReader

class CamundaDecisionQueryServiceBean(
  private val repositoryService: RepositoryService,
  private val dmnDiagramConverter: DmnDiagramConverter
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

    return dmnDiagramConverter.fromXml(Dmn.convertToString(modelInstance))
  }
}
