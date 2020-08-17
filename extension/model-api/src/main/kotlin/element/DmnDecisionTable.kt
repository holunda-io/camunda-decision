package io.holunda.decision.model.api.element

import io.holunda.decision.model.api.DecisionDefinitionKey
import io.holunda.decision.model.api.Id
import io.holunda.decision.model.api.Name
import io.holunda.decision.model.api.VersionTag
import io.holunda.decision.model.api.data.DmnHitPolicy
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

  /**
   * Creates a new instance using only one required decision instead of a set.
   */
  @JvmOverloads
  constructor(decisionDefinitionKey: DecisionDefinitionKey,
              name: Name,
              versionTag: VersionTag? = null,
              hitPolicy: DmnHitPolicy = DmnHitPolicy.FIRST,
              requiredDecision: DecisionDefinitionKey,
              header: Header,
              rules: DmnRuleList
  ) : this(decisionDefinitionKey, name, versionTag, hitPolicy, setOf(requiredDecision), header, rules)

  init {
    require(requiredDecisions.size < 2) { "currently, only 0 or one required decisions are supported." }
  }

  val informationRequirement: InformationRequirement? = if (requiredDecisions.isNotEmpty())
    InformationRequirement(this.decisionDefinitionKey, requiredDecisions.first())
  else null

  data class Header(val inputs: List<InputDefinition<*>>, val outputs: List<OutputDefinition<*>>) {
    val numInputs = inputs.size
    val numOutputs = outputs.size
  }

  data class InformationRequirement(val decisionTable: DecisionDefinitionKey, val requiredDecisionTable: DecisionDefinitionKey) {
    val id: Id = "graph_${requiredDecisionTable}_$decisionTable"
  }
}
