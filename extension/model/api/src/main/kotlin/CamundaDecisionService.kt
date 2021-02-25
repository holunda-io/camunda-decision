package io.holunda.decision.model.api

import io.holunda.decision.model.api.element.DmnDiagram
import io.holunda.decision.model.api.evaluation.CamundaDecisionEvaluationRequest
import io.holunda.decision.model.api.evaluation.CamundaDecisionEvaluationResult
import io.holunda.decision.model.api.evaluation.DmnDiagramEvaluationModel
import java.util.*

interface CamundaDecisionService : CamundaDecisionEvaluationService, CamundaDecisionRepositoryService, CamundaDecisionQueryService

interface CamundaDecisionRepositoryService {

  /**
   * Vararg deploy for convenience.
   * @see deploy
   */
  fun deploy(vararg diagrams: DmnDiagram) = deploy(diagrams.toList())

  /**
   * Deploys all given diagrams in one deployment.
   */
  fun deploy(diagrams: List<DmnDiagram>): DmnDiagramDeployment

  fun loadModel(diagramId: DiagramId): DmnDiagramEvaluationModel = findModel(diagramId).orElseThrow { IllegalArgumentException("no diagram deployed with id:$diagramId") }

  fun findModel(diagramId: DiagramId): Optional<DmnDiagramEvaluationModel>

  fun findAllModels(): List<DmnDiagramEvaluationModel>

}

interface CamundaDecisionEvaluationService {

  fun evaluateDiagram(request: CamundaDecisionEvaluationRequest): CamundaDecisionEvaluationResult
}

interface CamundaDecisionQueryService {

  fun loadDiagram(decisionDefinitionId: DecisionDefinitionId): DmnDiagram

}

/**
 * Contains return values of camunda [Deployment].
 */
data class DmnDiagramDeployment(
  val id: Id,
  val name: Name?,
  val deployedDiagrams: List<DmnDiagramEvaluationModel>,
  val deploymentTime: Date,
  val source: String?,
  val tenantId: String?
)
