package io.holunda.decision.model

import io.holunda.decision.model.api.DecisionDefinitionKey
import io.holunda.decision.model.api.Name
import io.holunda.decision.model.api.element.InputEntry
import io.holunda.decision.model.builder.DmnBusinessRuleBuilder
import io.holunda.decision.model.builder.DmnDecisionTableRulesBuilder
import io.holunda.decision.model.builder.DmnDiagramBuilder

object CamundaDecisionGenerator {

  @JvmStatic
  @JvmOverloads
  fun diagram(name: Name? = null) = DmnDiagramBuilder().apply {
    name?.let { name(it) }
  }

  @JvmStatic
  @JvmOverloads
  fun table(decisionDefinitionKey: DecisionDefinitionKey? = null) = DmnDecisionTableRulesBuilder()
    .apply { decisionDefinitionKey?.let { decisionDefinitionKey(it) } }

  @JvmStatic
  fun rule() = DmnBusinessRuleBuilder()

  @JvmStatic
  fun <T : Any> rule(condition: InputEntry<T>? = null) = rule()
    .apply { condition?.let { condition(it) } }
}
