package io.holunda.decision.generator.builder

import io.holunda.decision.model.DecisionDefinitionKey
import io.holunda.decision.model.Name
import io.holunda.decision.model.VersionTag
import io.holunda.decision.model.element.DmnDecisionTable
import io.holunda.decision.model.element.DmnHitPolicy
import io.holunda.decision.model.element.DmnRules

class DmnDecisionTableRulesBuilder : DmnDecisionTableBuilder() {

  fun decisionDefinitionKey(decisionDefinitionKey: DecisionDefinitionKey) = apply { this.decisionDefinitionKey = decisionDefinitionKey }

  fun name(name: Name) = apply { this.decisionName = name }

  fun versionTag(versionTag: VersionTag) = apply { this.versionTag = versionTag }

  override fun build(): DmnDecisionTable {
    requireNotNull(decisionDefinitionKey) { "must set decisionDefinitionKey" }

    return DmnDecisionTable(
      decisionDefinitionKey = requireNotNull(decisionDefinitionKey) { "must set decisionDefinitionKey" },
      name = decisionName ?: decisionDefinitionKey as Name,
      versionTag = versionTag,
      hitPolicy = DmnHitPolicy.FIRST, // TODO deal with default
      requiredDecisions = setOf(), // TODO deal with default
      header = DmnDecisionTable.Header(listOf(), listOf()), // TODO non empty headers
      rules = DmnRules() // TODO non empty rules
    )
  }
}
