package io.holunda.decision.model.expression

import io.holunda.decision.model.api.definition.DoubleInputDefinition
import io.holunda.decision.model.api.element.toEntry

data class FeelDoubleExpression(
  val input: DoubleInputDefinition,
  val condition: FeelComparableCondition<Double>,
  private val negate: Boolean = false
) : FeelExpression<Double, FeelDoubleExpression> {

  override val inputEntry by lazy {
    this.input.toEntry(
      if (negate)
        condition.notExpression
      else
        condition.expression
    )
  }

  override fun not(): FeelDoubleExpression = copy(negate = !negate)

}

