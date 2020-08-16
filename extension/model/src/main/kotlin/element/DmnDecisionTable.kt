package io.holunda.decision.model.element

import io.holunda.decision.model.DecisionDefinitionKey
import io.holunda.decision.model.Name
import io.holunda.decision.model.VersionTag
import io.holunda.decision.model.element.column.InputDefinition
import io.holunda.decision.model.element.column.OutputDefinition
import io.holunda.decision.model.element.row.DmnRule

data class DmnDecisionTable(
  val decisionDefinitionKey: DecisionDefinitionKey,
  val name: Name,
  val versionTag: VersionTag? = null,
  val hitPolicy: DmnHitPolicy = DmnHitPolicy.FIRST,
  val requiredDecisions: Set<DecisionDefinitionKey> = setOf(),
  val header: Header,
  val rules: List<DmnRule>
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
              rules: DmnRules
  ) : this(decisionDefinitionKey, name, versionTag, hitPolicy, setOf(requiredDecision), header, rules)

  data class Header(val inputs: List<InputDefinition<*>>, val outputs: List<OutputDefinition<*>>) {
    val numInputs = inputs.size
    val numOutputs = outputs.size
  }
}
