package io.holunda.decision.model.api.evaluation

import io.holunda.decision.model.api.DiagramId
import org.camunda.bpm.engine.variable.VariableMap
import org.camunda.bpm.engine.variable.Variables
import org.camunda.bpm.engine.variable.context.VariableContext
import org.camunda.bpm.engine.variable.impl.VariableMapImpl

interface CamundaDecisionEvaluationRequest {

  companion object {

    fun request(diagramId: DiagramId, variables: Map<String,Any>) = CamundaDecisionEvaluationRequestDto(
      diagramId = diagramId,
      variableContext = Variables.fromMap(variables) as VariableMapImpl
    )

    fun request(diagramId: DiagramId, variables: VariableMap) = CamundaDecisionEvaluationRequestDto(
      diagramId = diagramId,
      variableContext = variables as VariableMapImpl
    )
  }

  val diagramId: DiagramId

  val variableContext: VariableContext
}

data class CamundaDecisionEvaluationRequestDto(
  override val diagramId: DiagramId,
  override val variableContext: VariableContext
) : CamundaDecisionEvaluationRequest
