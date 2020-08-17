package io.holunda.decision.generator

import io.holunda.decision.generator.builder.DmnDecisionTableRulesBuilder
import io.holunda.decision.generator.builder.DmnDiagramBuilder
import io.holunda.decision.generator.builder.DmnBusinessRuleBuilder
import io.holunda.decision.model.api.DecisionDefinitionKey
import io.holunda.decision.model.api.Name
import io.holunda.decision.model.api.element.InputEntry

object CamundaDecisionGenerator {

  @JvmStatic
  @JvmOverloads
  fun diagram(name: Name? = null) = DmnDiagramBuilder().apply {
    if (name != null) name(name)
  }

  @JvmStatic
  @JvmOverloads
  fun table(decisionDefinitionKey: DecisionDefinitionKey? = null) = DmnDecisionTableRulesBuilder()
    .apply {
      if (decisionDefinitionKey != null) decisionDefinitionKey(decisionDefinitionKey)
    }

  @JvmStatic
  fun rule() = DmnBusinessRuleBuilder()

  @JvmStatic
  fun <T : Any> rule(condition: InputEntry<T>? = null) = rule().apply {
    if (condition != null) condition(condition)
  }
}
