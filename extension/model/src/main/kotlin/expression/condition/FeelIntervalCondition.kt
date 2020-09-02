package io.holunda.decision.model.expression.condition

import io.holunda.decision.model.expression.FeelExpression

/**
 * Declare that the input has to be in a given range/interval.
 */
data class FeelIntervalCondition<T : Comparable<T>>(
  val start: T,
  val end: T,
  val type: RangeType = RangeType.Include) : FeelCondition<T> {

  init {
    require(start < end) { "Interval requires start < end, was ($start,$end)" }
  }

  override val expression = (FeelCondition.toFeelString(start) to FeelExpression.toFeelString(end))
    .let { (s, e) -> "${type.prefix}$s..$e${type.suffix}" }

  /**
   * RangeType
   *
   * Some FEEL data types, such as numeric types and date types, can be tested against a range of values. These ranges consist of a start value and an end value. The range specifies if the start and end value is included in the range.
   *
   * Start	End	Example	Description
   * * include	include	[1..10] - Test that the input value is greater than or equal to the start value and less than or equal to the end value.
   * * exclude	include	]1..10] or (1..10] - Test that the input value is greater than the start value and less than or equal to the end value.
   * * include	exclude	[1..10[ or [1..10) - Test that the input value is greater than or equal to the start value and less than the end value.
   * * exclude	exclude	]1..10[ or (1..10) - Test that the input value is greater than the start value and less than the end value.
   *
   */
  enum class RangeType(val prefix: String, val suffix: String) {

    Include("[", "]"),
    Exclude("]", "["),
    IncludeExclude("[", "["),
    ExcludeInclude("]", "]")

  }
}
