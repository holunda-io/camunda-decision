package io.holunda.decision.model.expression

import io.holunda.decision.model.api.definition.BooleanInputDefinition
import io.holunda.decision.model.api.element.toEntry
import io.holunda.decision.model.expression.FeelExpression.Companion.toFeelString

/**
 * Boolean feel expressions. Basically, boolean expressions are just `true` or `false`.
 */
data class FeelBooleanExpression(
  val input: BooleanInputDefinition,
  val value: Boolean? = null,
  private val negate: Boolean = false
) : FeelExpression<Boolean, FeelBooleanExpression> {

  override val inputEntry by lazy {
    input.toEntry(expression)
  }

  override val expression by lazy {
    value?.xor(negate)?.let { toFeelString(it) }
  }

  override fun not(): FeelBooleanExpression = copy(negate = !negate)


}
