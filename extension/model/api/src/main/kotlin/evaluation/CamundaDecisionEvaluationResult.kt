package io.holunda.decision.model.api.evaluation

import io.holunda.decision.model.api.DiagramId
import io.holunda.decision.model.api.data.ResultType
import org.camunda.bpm.engine.variable.VariableMap

interface CamundaDecisionEvaluationResult {

  val diagramId: DiagramId

  val resultType: ResultType

  val result: List<VariableMap>

}

data class CamundaDecisionEvaluationResultDto(

  override val diagramId: DiagramId,
  override val resultType: ResultType,
  override val result: List<VariableMap>
) : CamundaDecisionEvaluationResult

