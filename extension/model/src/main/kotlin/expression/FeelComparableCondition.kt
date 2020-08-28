package io.holunda.decision.model.expression

sealed class FeelComparableCondition<T : Comparable<T>> {

  data class Disjunction<T : Comparable<T>>(val values: Set<T>) : FeelComparableCondition<T>() {

    override val expression: String? by lazy {
      if (values.isNotEmpty()) values.sorted().joinToString(separator = ",") { it.toString() } else null
    }

  }

  data class Comparison<T : Comparable<T>>(
    val value: T,
    val type: ComparisonType = ComparisonType.Equals
  ) : FeelComparableCondition<T>() {
    override val expression by lazy {
      "${type.symbol}$value"
    }
  }

  data class Interval<T : Comparable<T>>(val start: T, val end: T, val type: RangeType = RangeType.Include) : FeelComparableCondition<T>() {
    override val expression by lazy {
      "${type.prefix}$start..$end${type.suffix}"
    }
  }

  abstract val expression: String?

  val notExpression by lazy {
    expression?.let { "not($it)" }
  }
}

enum class ComparisonType(val symbol: String) {
  Equals(""),
  Less("< "),
  LessOrEqual("<= "),
  Greater("> "),
  GreaterOrEqual(">= ")

}


/**
 * Range
 * Some FEEL data types, such as numeric types and date types, can be tested against a range of values. These ranges consist of a start value and an end value. The range specifies if the start and end value is included in the range.
 *
 * Start	End	Example	Description
 * * include	include	[1..10]	Test that the input value is greater than or equal to the start value and less than or equal to the end value.
 * * exclude	include	]1..10] or (1..10]	Test that the input value is greater than the start value and less than or equal to the end value.
 * * include	exclude	[1..10[ or [1..10)	Test that the input value is greater than or equal to the start value and less than the end value.
 * * exclude	exclude	]1..10[ or (1..10)	Test that the input value is greater than the start value and less than the end value.
 *
 */
enum class RangeType(val prefix: String, val suffix: String) {

  Include("[", "]"),
  Exclude("]", "["),
  IncludeExclude("[", "["),
  ExcludeInclude("]", "]")

}
