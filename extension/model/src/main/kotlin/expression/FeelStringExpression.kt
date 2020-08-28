package io.holunda.decision.model.expression

import io.holunda.decision.model.api.definition.StringInputDefinition
import io.holunda.decision.model.api.element.InputEntry
import io.holunda.decision.model.api.element.toEntry

data class FeelStringExpression(
  val input: StringInputDefinition,
  val values: Set<String> = setOf(),
  private val negate: Boolean = false
) : FeelExpression<String, FeelStringExpression> {
  override val inputEntry by lazy {
    input.toEntry(
      if (values.isEmpty())
        null
      else {
        val csv = values.joinToString(separator = ",") { "\"$it\"" }
        if (negate) "not($csv)" else csv
      }
    )
  }

  override fun not(): FeelStringExpression = copy(negate = !negate)

}
