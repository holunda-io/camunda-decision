package io.holunda.decision.model.expression

import io.holunda.decision.model.api.definition.IntegerInputDefinition
import io.holunda.decision.model.api.definition.LongInputDefinition
import io.holunda.decision.model.api.element.toEntry

data class FeelLongExpression(
  val input: LongInputDefinition,
  val condition: FeelComparableCondition<Long>,
  private val negate: Boolean = false
) : FeelExpression<Long, FeelLongExpression> {

  override val inputEntry by lazy {
    this.input.toEntry(
      if (negate)
        condition.notExpression
      else
        condition.expression
    )
  }

  override fun not(): FeelLongExpression = copy(negate = !negate)

}

