package io.holunda.decision.model.api.element

import io.holunda.decision.model.api.DecisionDefinitionKey
import io.holunda.decision.model.api.Id
import io.holunda.decision.model.api.Name
import io.holunda.decision.model.api.VersionTag
import io.holunda.decision.model.api.data.DmnHitPolicy
import io.holunda.decision.model.api.data.ResultType
import io.holunda.decision.model.api.definition.InputDefinition
import io.holunda.decision.model.api.definition.OutputDefinition

data class DmnDecisionTable(
  val decisionDefinitionKey: DecisionDefinitionKey,
  val name: Name,
  val versionTag: VersionTag? = null,
  val hitPolicy: DmnHitPolicy = DmnHitPolicy.FIRST,
  val requiredDecisions: Set<DecisionDefinitionKey> = setOf(),
  val header: Header,
  val rules: DmnRuleList
) {

  init {
    require(requiredDecisions.size < 2) { "currently, only 0 or one required decisions are supported." }
    require(rules.isNotEmpty()) { "a decision table must contain rules" }
  }

  val informationRequirement: InformationRequirement? = if (requiredDecisions.isNotEmpty())
    InformationRequirement(this.decisionDefinitionKey, requiredDecisions.first())
  else null

  val resultType : ResultType = if (header.numOutputs > 1)
    ResultType.TUPLE
  else
    ResultType.SINGLE

  data class Header(val inputs: List<InputDefinition<*>>, val outputs: List<OutputDefinition<*>>) {
    init {
        require(inputs.isNotEmpty()) { "table header must contain inputs"}
        require(outputs.isNotEmpty()) { "table header must contain outputs"}
    }
    val numInputs = inputs.size
    val numOutputs = outputs.size
  }

  data class InformationRequirement(val decisionTable: DecisionDefinitionKey, val requiredDecisionTable: DecisionDefinitionKey) {
    val id: Id = "graph_${requiredDecisionTable}_$decisionTable"
  }

  fun inputEntry(row:Int, col:Int): String? {
    return rules[row].inputs[col].expression;
  }

}
