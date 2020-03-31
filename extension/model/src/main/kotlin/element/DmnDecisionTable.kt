package io.holunda.decision.model.element

import io.holunda.decision.model.DecisionDefinitionKey
import io.holunda.decision.model.Name
import io.holunda.decision.model.VersionTag

data class DmnDecisionTable(
  val decisionDefinitionKey: DecisionDefinitionKey,
  val name: Name,
  val versionTag: VersionTag? = null,
  val hitPolicy: DmnHitPolicy = DmnHitPolicy.FIRST,
  val requiredDecisions: Set<DecisionDefinitionKey> = setOf(),
  val header: Header,
  val rules: DmnRules
) {

  data class Header(val inputs: List<InputDefinition>, val outputs: List<OutputDefinition>) {
    val numInputs = inputs.size
    val numOutputs = outputs.size
  }
}
