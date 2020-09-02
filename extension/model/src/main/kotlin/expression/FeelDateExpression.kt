package io.holunda.decision.model.expression

import io.holunda.decision.model.api.FeelString
import io.holunda.decision.model.api.definition.DateInputDefinition
import io.holunda.decision.model.api.element.toEntry
import java.util.*

/**
 * Boolean feel expressions. Basically, boolean expressions are just `true` or `false`.
 */
data class FeelDateExpression(
  val input: DateInputDefinition,
  val condition: FeelComparableCondition<Date>,
  private val negate: Boolean = false
) : FeelExpression<Date, FeelDateExpression> {

  override val expression: FeelString? by lazy {
    condition.toFeelString(negate)
  }

  override val inputEntry by lazy {
    input.toEntry(expression)
  }

  override fun not(): FeelDateExpression = copy(negate = !negate)
}
