package io.holunda.decision.model.expression.condition

data class FeelComparisonCondition<T : Comparable<T>>(
  val value: T,
  val type: ComparisonType = ComparisonType.Equal
) : FeelCondition<T> {

  override val expression by lazy {
    "${type.symbol}${FeelCondition.toFeelString(value)}"
  }

  /**
   * Possible compare types.
   */
  enum class ComparisonType(val symbol: String) {
    Equal(""),
    Less("< "),
    LessOrEqual("<= "),
    Greater("> "),
    GreaterOrEqual(">= ")
  }
}
