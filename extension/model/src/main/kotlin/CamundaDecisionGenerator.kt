package io.holunda.decision.model

import io.holunda.decision.model.api.DecisionDefinitionKey
import io.holunda.decision.model.api.Name
import io.holunda.decision.model.builder.DmnBusinessRuleBuilder
import io.holunda.decision.model.builder.DmnDecisionTableRulesBuilder
import io.holunda.decision.model.builder.DmnDiagramBuilder
import io.holunda.decision.model.expression.FeelExpression

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
  fun <T : Any, SELF: FeelExpression<T, SELF>> rule(condition: FeelExpression<T,SELF>? = null) = rule()
    .apply { condition?.let { condition(it) } }
}
