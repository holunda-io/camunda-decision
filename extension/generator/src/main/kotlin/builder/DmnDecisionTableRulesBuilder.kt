package io.holunda.decision.generator.builder

import io.holunda.decision.model.DecisionDefinitionKey
import io.holunda.decision.model.Name
import io.holunda.decision.model.VersionTag
import io.holunda.decision.model.element.DmnDecisionTable
import io.holunda.decision.model.element.DmnHitPolicy
import io.holunda.decision.model.element.DmnRules

class DmnDecisionTableRulesBuilder : AbstractDmnDecisionTableBuilder() {

  private val dmnRuleBuilders = mutableListOf<DmnRulesBuilder>()

  fun decisionDefinitionKey(decisionDefinitionKey: DecisionDefinitionKey) = apply { this.decisionDefinitionKey = decisionDefinitionKey }

  fun name(name: Name) = apply { this.decisionName = name }

  fun versionTag(versionTag: VersionTag) = apply { this.versionTag = versionTag }

  fun addRule(dmnRulesBuilder: DmnRulesBuilder) = apply { dmnRuleBuilders.add(dmnRulesBuilder) }

  override fun build(): DmnDecisionTable {
    requireNotNull(decisionDefinitionKey) { "must set decisionDefinitionKey" }
    require(dmnRuleBuilders.isNotEmpty()) { "must set at least one rule" }

    println("tableBuilder: $this")

    val combinedRules = DmnRules(dmnRuleBuilders.map { it.build() }.flatMap { it.rules })

    return DmnDecisionTable(
      decisionDefinitionKey = requireNotNull(decisionDefinitionKey) { "must set decisionDefinitionKey" },
      name = decisionName ?: decisionDefinitionKey as Name,
      versionTag = versionTag,
      hitPolicy = DmnHitPolicy.FIRST, // TODO deal with default
      requiredDecisions = setOf(), // TODO deal with default
      header = DmnDecisionTable.Header(combinedRules.distinctInputs, combinedRules.distinctOutputs), // TODO non empty headers
      rules = combinedRules
    )
  }

  override fun toString(): String = "DmnDecisionTableRulesBuilder(" +
    "decisionDefinitionKey=$decisionDefinitionKey, " +
    "decisionName=$decisionName, " +
    "versionTag=$versionTag, " +
    "dmnRuleBuilders=$dmnRuleBuilders, " +
    ")"

}
