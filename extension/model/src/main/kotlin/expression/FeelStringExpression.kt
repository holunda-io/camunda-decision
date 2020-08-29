package io.holunda.decision.model.expression

import io.holunda.decision.model.api.Expression
import io.holunda.decision.model.api.definition.StringInputDefinition
import io.holunda.decision.model.api.element.toEntry
import io.holunda.decision.model.expression.FeelExpression.Companion.negateExpression
import io.holunda.decision.model.expression.FeelExpression.Companion.toFeelString

data class FeelStringExpression(
  val input: StringInputDefinition,
  val values: Set<String> = setOf(),
  private val negate: Boolean = false
) : FeelExpression<String, FeelStringExpression> {

  override val inputEntry by lazy { input.toEntry(expression) }

  override val expression: Expression? by lazy {
    if (values.isEmpty())
      null
    else {
      with(values.joinToString(separator = ",") { toFeelString(it) }) {
        if (negate) negateExpression(this) else this
      }
    }
  }

  override fun not(): FeelStringExpression = copy(negate = !negate)

}
