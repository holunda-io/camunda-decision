package io.holunda.decision.model.expression

import io.holunda.decision.model.api.definition.IntegerInputDefinition
import io.holunda.decision.model.api.element.toEntry

data class FeelIntegerExpression(
  val input: IntegerInputDefinition,
  val condition: FeelComparableCondition<Int>,
  private val negate: Boolean = false
) : FeelExpression<Int, FeelIntegerExpression> {

  override val inputEntry by lazy {
    this.input.toEntry(expression)
  }

  override val expression by lazy {
    condition.toFeelString(negate)
  }

  override fun not(): FeelIntegerExpression = copy(negate = !negate)

}
