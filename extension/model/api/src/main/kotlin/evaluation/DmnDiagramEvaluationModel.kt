package io.holunda.decision.model.api.evaluation

import io.holunda.decision.model.api.DecisionDefinitionId
import io.holunda.decision.model.api.DiagramId
import io.holunda.decision.model.api.Name
import io.holunda.decision.model.api.data.ResultType
import io.holunda.decision.model.api.definition.InputDefinition
import io.holunda.decision.model.api.definition.OutputDefinition
import java.util.*

/**
 * Evaluation model of a given diagram. It knows the required inputs, the expected output (and its result type)
 * and which decision table to call via the camunda DecisionService API.
 */
data class DmnDiagramEvaluationModel(
  val diagramId: DiagramId,
  val name: Name,
  val resourceName: String,
  val deploymentId:String,
  val deploymentTime: Date,
  val decisionDefinitionId: DecisionDefinitionId,
  val inputs: Set<InputDefinition<*>>,
  val outputs: Set<OutputDefinition<*>>,
  val resultType: ResultType
)
