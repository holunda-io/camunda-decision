package io.holunda.decision.model.expression

import io.holunda.decision.model.api.definition.BooleanInputDefinition
import io.holunda.decision.model.api.element.InputEntry
import io.holunda.decision.model.api.element.toEntry


data class FeelBooleanExpression(
  val input: BooleanInputDefinition,
  val value: Boolean? = null,
  private val negate: Boolean = false
) : FeelExpression<Boolean, FeelBooleanExpression> {
  override val inputEntry by lazy {
    input.toEntry(
      value?.xor(negate)?.toString()
    )
  }

  override fun not(): FeelBooleanExpression = copy(negate = !negate)
}
