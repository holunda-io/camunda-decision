package io.holunda.decision.model.expression

import io.holunda.decision.model.api.Expression
import io.holunda.decision.model.expression.FeelExpression.Companion.negateExpression
import io.holunda.decision.model.expression.FeelExpression.Companion.toFeelString

/**
 * Feel expressions that can be appllied on the number and date DataTypes.
 */
sealed class FeelComparableCondition<T : Comparable<T>> {

  /**
   * Disjunction (equal/oneOf). Lists allowed values.
   *
   * Example for Long: `1,2,3` is true if the input value is 1,2 or 3.
   */
  data class Disjunction<T : Comparable<T>>(val values: Set<T>) : FeelComparableCondition<T>() {

    override val expression: String? by lazy {
      if (values.isNotEmpty()) values.sorted()
        .joinToString(separator = ",") { toFeelString(it) } else null
    }
  }

  /**
   * Compares input with given value, can be `=,<,<=,>,>=`
   */
  data class Comparison<T : Comparable<T>>(
    val value: T,
    val type: ComparisonType = ComparisonType.Equals
  ) : FeelComparableCondition<T>() {
    override val expression by lazy {
      "${type.symbol}${toFeelString(value)}"
    }

    /**
     * Possible compare types.
     */
    enum class ComparisonType(val symbol: String) {
      Equals(""),
      Less("< "),
      LessOrEqual("<= "),
      Greater("> "),
      GreaterOrEqual(">= ")
    }
  }

  /**
   * Declare that the input has to be in a given range/interval.
   */
  data class Interval<T : Comparable<T>>(val start: T, val end: T, val type: RangeType = RangeType.Include) : FeelComparableCondition<T>() {

    init {
      require(start < end) { "Interval requires start < end, was ($start,$end)" }
    }

    override val expression by lazy {
      (toFeelString(start) to toFeelString(end))
        .let { (s, e) -> "${type.prefix}$s..$e${type.suffix}" }
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
  }

  abstract val expression: Expression?

  fun toFeelString(negate: Boolean) = with(expression) {
    if (negate)
      negateExpression(this)
    else
      this
  }

}

