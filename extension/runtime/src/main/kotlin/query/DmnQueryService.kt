package io.holunda.decision.runtime.query

import io.holunda.decision.model.DecisionDefinitionKey
import org.camunda.bpm.engine.RepositoryService
import org.camunda.bpm.model.dmn.DmnModelInstance

class DmnQueryService(
  private val repositoryService: RepositoryService
) {

  fun findModelInstance(decisionDefinitionKey: DecisionDefinitionKey): DmnModelInstance {
    val definition = repositoryService.createDecisionDefinitionQuery()
      .decisionDefinitionKey(decisionDefinitionKey)
      .latestVersion()
      .singleResult()

    return repositoryService.getDmnModelInstance(definition.id)!!
  }
}
