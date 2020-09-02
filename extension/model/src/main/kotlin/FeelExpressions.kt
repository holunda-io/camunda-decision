package io.holunda.decision.model

import io.holunda.decision.model.api.definition.*
import io.holunda.decision.model.api.element.toEntry
import io.holunda.decision.model.expression.*
import io.holunda.decision.model.expression.FeelComparableCondition.Comparison
import io.holunda.decision.model.expression.FeelComparableCondition.Comparison.ComparisonType
import io.holunda.decision.model.expression.FeelComparableCondition.Interval.RangeType
import io.holunda.decision.model.expression.FeelExpression.Companion.toFeelString
import io.holunda.decision.model.expression.condition.FeelComparisonCondition
import io.holunda.decision.model.expression.condition.FeelComparisonCondition.ComparisonType.*
import io.holunda.decision.model.expression.condition.FeelDisjunctionCondition
import io.holunda.decision.model.expression.jbool.FeelInputVariable
import java.util.*

object FeelExpressions {

  @JvmStatic
  fun not(variable: FeelInputVariable) = variable.negate()

  // boolean

  fun BooleanInputDefinition.feelTrue() = feelEqual(true)
  fun BooleanInputDefinition.feelFalse() = feelEqual(false)
  fun BooleanInputDefinition.feelEqual(value: Boolean) = FeelInputVariable(this, FeelComparisonCondition(value, Equal))

  // string

  fun StringInputDefinition.feelEqual(value: String) = FeelInputVariable(this, FeelComparisonCondition(value, Equal))
  fun StringInputDefinition.feelMatchOne(vararg values: String) = FeelInputVariable(this, FeelDisjunctionCondition(values.toSet()))
  fun StringInputDefinition.feelMatchNone(vararg values: String) = FeelInputVariable(this, FeelDisjunctionCondition(values.toSet())).negate()

  // integer

  fun IntegerInputDefinition.feelEqual(value: Int) = feelComparison(value, Equal)
  fun IntegerInputDefinition.feelGreaterThan(value: Int) = feelComparison(value, Greater)
  fun IntegerInputDefinition.feelGreaterThanOrEqual(value: Int) = feelComparison(value, GreaterOrEqual)
  fun IntegerInputDefinition.feelLessThan(value: Int) = feelComparison(value, Less)
  fun IntegerInputDefinition.feelLessThanOrEqual(value: Int) = feelComparison(value, LessOrEqual)
  fun IntegerInputDefinition.feelComparison(value: Int, type: FeelComparisonCondition.ComparisonType) = FeelInputVariable(this, FeelComparisonCondition(value, type))

  // long

  fun LongInputDefinition.feelEqual(value: Long) = feelComparison(value, Equal)
  fun LongInputDefinition.feelGreaterThan(value: Long) = feelComparison(value, Greater)
  fun LongInputDefinition.feelGreaterThanOrEqual(value: Long) = feelComparison(value, GreaterOrEqual)
  fun LongInputDefinition.feelLessThan(value: Long) = feelComparison(value, Less)
  fun LongInputDefinition.feelLessThanOrEqual(value: Long) = feelComparison(value, LessOrEqual)
  fun LongInputDefinition.feelComparison(value: Long, type: FeelComparisonCondition.ComparisonType) = FeelInputVariable(this, FeelComparisonCondition(value, type))

  // double

  fun DoubleInputDefinition.feelEqual(value: Double) = feelComparison(value, Equal)
  fun DoubleInputDefinition.feelGreaterThan(value: Double) = feelComparison(value, Greater)
  fun DoubleInputDefinition.feelGreaterThanOrEqual(value: Double) = feelComparison(value, GreaterOrEqual)
  fun DoubleInputDefinition.feelLessThan(value: Double) = feelComparison(value, Less)
  fun DoubleInputDefinition.feelLessThanOrEqual(value: Double) = feelComparison(value, LessOrEqual)
  fun DoubleInputDefinition.feelComparison(value: Double, type: FeelComparisonCondition.ComparisonType) = FeelInputVariable(this, FeelComparisonCondition(value, type))

  // negates the given expression.
  @JvmStatic
  fun <F : FeelExpression<T, F>, T : Any> not(expression: F) = expression.not()

  fun StringInputDefinition.exprEquals_old(value: String) = exprMatchOne_old(value)
  fun StringInputDefinition.exprMatchOne_old(vararg values: String) = FeelStringExpression(input = this, values = values.toSet())
  fun StringInputDefinition.exprMatchNone_old(vararg values: String) = FeelStringExpression(input = this, values = values.toSet(), negate = true)

  fun BooleanInputDefinition.exprTrue_old() = exprEquals_old(true)
  fun BooleanInputDefinition.exprFalse_old() = exprEquals_old(false)
  fun BooleanInputDefinition.exprEquals_old(value: Boolean) = FeelBooleanExpression(input = this, value = value)

  fun IntegerInputDefinition.exprOneOf_old(vararg values: Int) = FeelIntegerExpression(
    input = this,
    condition = FeelComparableCondition.Disjunction(values = values.toSet())
  )

  fun IntegerInputDefinition.exprEquals_old(value: Int) = exprComparison_old(value, ComparisonType.Equals)
  fun IntegerInputDefinition.exprGreaterThan_old(value: Int) = exprComparison_old(value, ComparisonType.Greater)
  fun IntegerInputDefinition.exprGreaterThanOrEqual_old(value: Int) = exprComparison_old(value, ComparisonType.GreaterOrEqual)
  fun IntegerInputDefinition.exprLessThan_old(value: Int) = exprComparison_old(value, ComparisonType.Less)
  fun IntegerInputDefinition.exprLessThanOrEqual_old(value: Int) = exprComparison_old(value, ComparisonType.LessOrEqual)
  fun IntegerInputDefinition.exprComparison_old(value: Int, type: ComparisonType) = FeelIntegerExpression(
    input = this,
    condition = Comparison(value, type)
  )

  fun IntegerInputDefinition.exprBetween_old(begin: Int, end: Int) = exprInterval_old(begin, end, RangeType.Include)
  fun IntegerInputDefinition.exprBetweenExclude_old(begin: Int, end: Int) = exprInterval_old(begin, end, RangeType.Exclude)
  fun IntegerInputDefinition.exprInterval_old(begin: Int, end: Int, type: RangeType) = FeelIntegerExpression(
    input = this,
    condition = FeelComparableCondition.Interval(begin, end, type)
  )

  fun LongInputDefinition.exprOneOf_old(vararg values: Long) = FeelLongExpression(
    input = this,
    condition = FeelComparableCondition.Disjunction(values = values.toSet())
  )

  fun LongInputDefinition.exprEquals_old(value: Long) = exprComparison_old(value, ComparisonType.Equals)
  fun LongInputDefinition.exprGreaterThan_old(value: Long) = exprComparison_old(value, ComparisonType.Greater)
  fun LongInputDefinition.exprGreaterThanOrEqual_old(value: Long) = exprComparison_old(value, ComparisonType.GreaterOrEqual)
  fun LongInputDefinition.exprLessThan_old(value: Long) = exprComparison_old(value, ComparisonType.Less)
  fun LongInputDefinition.exprLessThanOrEqual_old(value: Long) = exprComparison_old(value, ComparisonType.LessOrEqual)
  fun LongInputDefinition.exprComparison_old(value: Long, type: ComparisonType) = FeelLongExpression(
    input = this,
    condition = Comparison(value, type)
  )

  fun LongInputDefinition.exprBetween_old(begin: Long, end: Long) = exprInterval_old(begin, end, RangeType.Include)
  fun LongInputDefinition.exprBetweenExclude_old(begin: Long, end: Long) = exprInterval_old(begin, end, RangeType.Exclude)
  fun LongInputDefinition.exprInterval_old(begin: Long, end: Long, type: RangeType) = FeelLongExpression(
    input = this,
    condition = FeelComparableCondition.Interval(begin, end, type)
  )


  fun DoubleInputDefinition.exprOneOf_old(vararg values: Double) = FeelDoubleExpression(
    input = this,
    condition = FeelComparableCondition.Disjunction(values = values.toSet())
  )

  fun DoubleInputDefinition.exprEquals_old(value: Double) = exprComparison_old(value, ComparisonType.Equals)
  fun DoubleInputDefinition.exprGreaterThan_old(value: Double) = exprComparison_old(value, ComparisonType.Greater)
  fun DoubleInputDefinition.exprGreaterThanOrEqual_old(value: Double) = exprComparison_old(value, ComparisonType.GreaterOrEqual)
  fun DoubleInputDefinition.exprLessThan_old(value: Double) = exprComparison_old(value, ComparisonType.Less)
  fun DoubleInputDefinition.exprLessThanOrEqual_old(value: Double) = exprComparison_old(value, ComparisonType.LessOrEqual)
  fun DoubleInputDefinition.exprComparison_old(value: Double, type: ComparisonType) = FeelDoubleExpression(
    input = this,
    condition = Comparison(value, type)
  )

  fun DoubleInputDefinition.exprBetween_old(begin: Double, end: Double) = exprInterval_old(begin, end, RangeType.Include)
  fun DoubleInputDefinition.exprBetweenExclude_old(begin: Double, end: Double) = exprInterval_old(begin, end, RangeType.Exclude)
  fun DoubleInputDefinition.exprInterval_old(begin: Double, end: Double, type: RangeType) = FeelDoubleExpression(
    input = this,
    condition = FeelComparableCondition.Interval(begin, end, type)
  )

  fun DateInputDefinition.exprExactly(value: Date) = exprComparison_old(value, ComparisonType.Equals)
  fun DateInputDefinition.exprBefore(value: Date) = exprComparison_old(value, ComparisonType.Less)
  fun DateInputDefinition.exprAfter(value: Date) = exprComparison_old(value, ComparisonType.Greater)
  fun DateInputDefinition.exprBetween_old(start: Date, end: Date) = FeelDateExpression(input = this, condition = FeelComparableCondition.Interval(start, end))
  fun DateInputDefinition.exprComparison_old(value: Date, type: ComparisonType) = FeelDateExpression(
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
