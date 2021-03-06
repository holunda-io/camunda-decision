package io.holunda.decision.model

import io.holunda.decision.model.api.DecisionDefinitionKey
import io.holunda.decision.model.api.Name
import io.holunda.decision.model.api.element.DmnDiagram
import io.holunda.decision.model.builder.DmnBusinessRuleBuilder
import io.holunda.decision.model.builder.DmnDecisionTableReferenceBuilder
import io.holunda.decision.model.builder.DmnDecisionTableRulesBuilder
import io.holunda.decision.model.builder.DmnDiagramBuilder
import io.holunda.decision.model.condition.jbool.JBoolExpressionSupplier

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
  @JvmOverloads
  fun tableReference(diagram:DmnDiagram, decisionDefinitionKey: DecisionDefinitionKey? = null) = DmnDecisionTableReferenceBuilder()
    .reference(diagram, decisionDefinitionKey)

  @JvmStatic
  fun rule() = DmnBusinessRuleBuilder()

  @JvmStatic
  fun rule(expression: JBoolExpressionSupplier, vararg expressions: JBoolExpressionSupplier): DmnBusinessRuleBuilder = rule()
    .let {
      val list = listOf(expression) + expressions.toList()
      it.condition(*list.toTypedArray())
    }
}
