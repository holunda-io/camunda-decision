package io.holunda.decision.runtime

import io.holunda.decision.model.api.*
import io.holunda.decision.model.api.element.DmnDiagram
import io.holunda.decision.model.api.evaluation.CamundaDecisionEvaluationRequest
import io.holunda.decision.model.api.evaluation.CamundaDecisionEvaluationResult
import io.holunda.decision.model.api.evaluation.DmnDiagramEvaluationModel
import java.util.*

class CamundaDecisionServiceBean(
  private val repositoryService: CamundaDecisionRepositoryService,
  private val queryService: CamundaDecisionQueryService,
  private val evaluationService: CamundaDecisionEvaluationService
) : CamundaDecisionService  {

  override fun evaluateDiagram(request: CamundaDecisionEvaluationRequest): CamundaDecisionEvaluationResult = evaluationService.evaluateDiagram(request)

  override fun deploy(diagrams: List<DmnDiagram>): DmnDiagramDeployment = repositoryService.deploy(diagrams)

  override fun findModel(diagramId: DiagramId): Optional<DmnDiagramEvaluationModel> = repositoryService.findModel(diagramId)

  override fun findAllModels(): List<DmnDiagramEvaluationModel> = repositoryService.findAllModels()

  override fun loadDiagram(diagramId: DiagramId): DmnDiagram = queryService.loadDiagram(diagramId)
}
