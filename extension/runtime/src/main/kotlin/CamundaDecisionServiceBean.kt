package io.holunda.decision.runtime

import io.holunda.decision.model.api.*
import io.holunda.decision.model.api.element.DmnDiagram
import io.holunda.decision.model.api.evaluation.DmnDiagramEvaluationModel

class CamundaDecisionServiceBean(
  private val repositoryService: CamundaDecisionRepositoryService,
  private val queryService: CamundaDecisionQueryService,
  private val evaluationService: CamundaDecisionEvaluationService
) : CamundaDecisionService  {

  override fun deploy(diagrams: List<DmnDiagram>): DmnDiagramDeployment = repositoryService.deploy(diagrams)

  override fun findAllModels(): List<DmnDiagramEvaluationModel> = repositoryService.findAllModels()

  override fun loadDiagram(id: Id): DmnDiagram = queryService.loadDiagram(id)
}
