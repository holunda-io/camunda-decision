package io.holunda.decision.runtime.evaluation

import io.holunda.decision.model.api.CamundaDecisionEvaluationService
import io.holunda.decision.model.api.CamundaDecisionRepositoryService
import io.holunda.decision.model.api.evaluation.CamundaDecisionEvaluationRequest
import io.holunda.decision.model.api.evaluation.CamundaDecisionEvaluationResult
import io.holunda.decision.model.api.evaluation.CamundaDecisionEvaluationResultDto
import org.camunda.bpm.engine.DecisionService
import org.camunda.bpm.engine.variable.Variables
import org.camunda.bpm.engine.variable.context.VariableContext
import org.camunda.bpm.engine.variable.value.TypedValue

class CamundaDecisionEvaluationServiceBean(
  private val repositoryService: CamundaDecisionRepositoryService,
  private val decisionService: DecisionService
) : CamundaDecisionEvaluationService{
  override fun evaluateDiagram(request: CamundaDecisionEvaluationRequest): CamundaDecisionEvaluationResult {
    val model = repositoryService.loadModel(request.diagramId)

    val evaluateDecisionTableById = decisionService.evaluateDecisionTableById(model.decisionDefinitionId, request.variableContext.toMap())



    return CamundaDecisionEvaluationResultDto(
      diagramId = model.diagramId,
      resultType = model.resultType,
      result = evaluateDecisionTableById.resultList.map { Variables.fromMap(it) }
    )
  }
}

fun VariableContext.toMap() : Map<String, TypedValue> = this.keySet()
  .map { it to this.resolve(it) }
  .toMap()
