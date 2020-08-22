package io.holunda.decision.model.builder

import io.holunda.decision.model.api.DecisionDefinitionKey
import io.holunda.decision.model.api.Name
import io.holunda.decision.model.api.VersionTag
import io.holunda.decision.model.api.data.DmnHitPolicy
import io.holunda.decision.model.api.element.DmnDecisionTable
import io.holunda.decision.model.api.element.DmnRuleList

class DmnDecisionTableRulesBuilder : AbstractDmnDecisionTableBuilder() {

  private val dmnRuleBuilders = mutableListOf<DmnBusinessRuleBuilder>()
  private var hitPolicy : DmnHitPolicy? = null

  fun decisionDefinitionKey(decisionDefinitionKey: DecisionDefinitionKey) = apply { this.decisionDefinitionKey = decisionDefinitionKey }

  fun name(name: Name) = apply { this.decisionName = name }

  fun versionTag(versionTag: VersionTag) = apply { this.versionTag = versionTag }

  fun requiredDecision(requiredDecision: DecisionDefinitionKey) = apply { this.requiredDecision = requiredDecision }

  fun hitPolicy(hitPolicy: DmnHitPolicy) = apply { this.hitPolicy = hitPolicy }

  fun addRule(dmnBusinessRuleBuilder: DmnBusinessRuleBuilder) = apply { dmnRuleBuilders.add(dmnBusinessRuleBuilder) }

  override fun build(): DmnDecisionTable {
    requireNotNull(decisionDefinitionKey) { "must set decisionDefinitionKey" }
    require(dmnRuleBuilders.isNotEmpty()) { "must set at least one rule" }

    val combinedRules = DmnRuleList(dmnRuleBuilders.map { it.build() }.flatMap { it })

    return DmnDecisionTable(
      decisionDefinitionKey = requireNotNull(decisionDefinitionKey) { "must set decisionDefinitionKey" },
      name = decisionName ?: decisionDefinitionKey as Name,
      versionTag = versionTag,
      hitPolicy = hitPolicy ?: DmnHitPolicy.FIRST,
      requiredDecisions = if (requiredDecision != null) setOf(requiredDecision!!) else setOf(),
      header = DmnDecisionTable.Header(combinedRules.distinctInputs, combinedRules.distinctOutputs),
      rules = combinedRules
    )
  }

  override fun toString(): String = "DmnDecisionTableRulesBuilder(" +
    "decisionDefinitionKey=$decisionDefinitionKey, " +
    "decisionName=$decisionName, " +
    "versionTag=$versionTag, " +
    "hitPolicy=$hitPolicy, " +
    "dmnRuleBuilders=$dmnRuleBuilders, " +
    ")"

}
