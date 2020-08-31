package io.holunda.decision.model

import io.holunda.decision.model.api.definition.*
import io.holunda.decision.model.api.element.toEntry
import io.holunda.decision.model.expression.*
import io.holunda.decision.model.expression.FeelComparableCondition.Comparison
import io.holunda.decision.model.expression.FeelComparableCondition.Comparison.ComparisonType
import io.holunda.decision.model.expression.FeelComparableCondition.Interval.RangeType
import io.holunda.decision.model.expression.FeelExpression.Companion.toFeelString
import java.util.*

object FeelExpressions {

  // negates the given expression.
  @JvmStatic
  fun <F : FeelExpression<T, F>, T : Any> not(expression: F) = expression.not()

  fun StringInputDefinition.exprEquals(value: String) = exprMatchOne(value)
  fun StringInputDefinition.exprMatchOne(vararg values: String) = FeelStringExpression(input = this, values = values.toSet())
  fun StringInputDefinition.exprMatchNone(vararg values: String) = FeelStringExpression(input = this, values = values.toSet(), negate = true)

  fun BooleanInputDefinition.exprTrue() = exprEquals(true)
  fun BooleanInputDefinition.exprFalse() = exprEquals(false)
  fun BooleanInputDefinition.exprEquals(value: Boolean) = FeelBooleanExpression(input = this, value = value)

  fun IntegerInputDefinition.exprOneOf(vararg values: Int) = FeelIntegerExpression(
    input = this,
    condition = FeelComparableCondition.Disjunction(values = values.toSet())
  )

  fun IntegerInputDefinition.exprEquals(value: Int) = exprComparison(value, ComparisonType.Equals)
  fun IntegerInputDefinition.exprGreaterThan(value: Int) = exprComparison(value, ComparisonType.Greater)
  fun IntegerInputDefinition.exprGreaterThanOrEqual(value: Int) = exprComparison(value, ComparisonType.GreaterOrEqual)
  fun IntegerInputDefinition.exprLessThan(value: Int) = exprComparison(value, ComparisonType.Less)
  fun IntegerInputDefinition.exprLessThanOrEqual(value: Int) = exprComparison(value, ComparisonType.LessOrEqual)
  fun IntegerInputDefinition.exprComparison(value: Int, type: ComparisonType) = FeelIntegerExpression(
    input = this,
    condition = Comparison(value, type)
  )

  fun IntegerInputDefinition.exprBetween(begin: Int, end: Int) = exprInterval(begin, end, RangeType.Include)
  fun IntegerInputDefinition.exprBetweenExclude(begin: Int, end: Int) = exprInterval(begin, end, RangeType.Exclude)
  fun IntegerInputDefinition.exprInterval(begin: Int, end: Int, type: RangeType) = FeelIntegerExpression(
    input = this,
    condition = FeelComparableCondition.Interval(begin, end, type)
  )

  fun LongInputDefinition.exprOneOf(vararg values: Long) = FeelLongExpression(
    input = this,
    condition = FeelComparableCondition.Disjunction(values = values.toSet())
  )

  fun LongInputDefinition.exprEquals(value: Long) = exprComparison(value, ComparisonType.Equals)
  fun LongInputDefinition.exprGreaterThan(value: Long) = exprComparison(value, ComparisonType.Greater)
  fun LongInputDefinition.exprGreaterThanOrEqual(value: Long) = exprComparison(value, ComparisonType.GreaterOrEqual)
  fun LongInputDefinition.exprLessThan(value: Long) = exprComparison(value, ComparisonType.Less)
  fun LongInputDefinition.exprLessThanOrEqual(value: Long) = exprComparison(value, ComparisonType.LessOrEqual)
  fun LongInputDefinition.exprComparison(value: Long, type: ComparisonType) = FeelLongExpression(
    input = this,
    condition = Comparison(value, type)
  )

  fun LongInputDefinition.exprBetween(begin: Long, end: Long) = exprInterval(begin, end, RangeType.Include)
  fun LongInputDefinition.exprBetweenExclude(begin: Long, end: Long) = exprInterval(begin, end, RangeType.Exclude)
  fun LongInputDefinition.exprInterval(begin: Long, end: Long, type: RangeType) = FeelLongExpression(
    input = this,
    condition = FeelComparableCondition.Interval(begin, end, type)
  )


  fun DoubleInputDefinition.exprOneOf(vararg values: Double) = FeelDoubleExpression(
    input = this,
    condition = FeelComparableCondition.Disjunction(values = values.toSet())
  )

  fun DoubleInputDefinition.exprEquals(value: Double) = exprComparison(value, ComparisonType.Equals)
  fun DoubleInputDefinition.exprGreaterThan(value: Double) = exprComparison(value, ComparisonType.Greater)
  fun DoubleInputDefinition.exprGreaterThanOrEqual(value: Double) = exprComparison(value, ComparisonType.GreaterOrEqual)
  fun DoubleInputDefinition.exprLessThan(value: Double) = exprComparison(value, ComparisonType.Less)
  fun DoubleInputDefinition.exprLessThanOrEqual(value: Double) = exprComparison(value, ComparisonType.LessOrEqual)
  fun DoubleInputDefinition.exprComparison(value: Double, type: ComparisonType) = FeelDoubleExpression(
    input = this,
    condition = Comparison(value, type)
  )

  fun DoubleInputDefinition.exprBetween(begin: Double, end: Double) = exprInterval(begin, end, RangeType.Include)
  fun DoubleInputDefinition.exprBetweenExclude(begin: Double, end: Double) = exprInterval(begin, end, RangeType.Exclude)
  fun DoubleInputDefinition.exprInterval(begin: Double, end: Double, type: RangeType) = FeelDoubleExpression(
    input = this,
    condition = FeelComparableCondition.Interval(begin, end, type)
  )

  fun DateInputDefinition.exprExactly(value: Date) = exprComparison(value, ComparisonType.Equals)
  fun DateInputDefinition.exprBefore(value: Date) = exprComparison(value, ComparisonType.Less)
  fun DateInputDefinition.exprAfter(value: Date) = exprComparison(value, ComparisonType.Greater)
  fun DateInputDefinition.exprBetween(start: Date, end: Date) = FeelDateExpression(input = this, condition = FeelComparableCondition.Interval(start, end))
  fun DateInputDefinition.exprComparison(value: Date, type: ComparisonType) = FeelDateExpression(
    input = this,
    condition = Comparison(value, type)
  )

  fun BooleanOutputDefinition.resultValue(value: Boolean) = this.toEntry(toFeelString(value))
  fun BooleanOutputDefinition.resultTrue() = resultValue(true)
  fun BooleanOutputDefinition.resultFalse() = resultValue(false)
  fun StringOutputDefinition.resultValue(value: String) = this.toEntry(toFeelString(value))
  fun IntegerOutputDefinition.resultValue(value: Int) = this.toEntry(toFeelString(value))
  fun LongOutputDefinition.resultValue(value: Long) = this.toEntry(toFeelString(value))
  fun DoubleOutputDefinition.resultValue(value: Double) = this.toEntry(toFeelString(value))
  fun DateOutputDefinition.resultValue(value: Date) = this.toEntry(toFeelString(value))
}
