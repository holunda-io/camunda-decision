package io.holunda.decision.model

import io.holunda.decision.model.api.definition.*
import io.holunda.decision.model.api.entry.toEntry
import io.holunda.decision.model.condition.FeelComparisonCondition
import io.holunda.decision.model.condition.FeelComparisonCondition.ComparisonType
import io.holunda.decision.model.condition.FeelComparisonCondition.ComparisonType.*
import io.holunda.decision.model.condition.FeelCondition.Companion.toFeelString
import io.holunda.decision.model.condition.FeelDisjunctionCondition
import io.holunda.decision.model.condition.FeelIntervalCondition
import io.holunda.decision.model.condition.FeelIntervalCondition.RangeType
import io.holunda.decision.model.condition.jbool.FeelInputVariable
import java.util.*

object FeelConditions {

  @JvmStatic
  fun not(variable: FeelInputVariable) = variable.negate()

  // boolean

  fun BooleanInputDefinition.feelTrue() = feelEqual(true)
  fun BooleanInputDefinition.feelFalse() = feelEqual(false)
  fun BooleanInputDefinition.feelEqual(value: Boolean) = FeelInputVariable(this, FeelComparisonCondition(value, Equal))

  fun BooleanOutputDefinition.resultValue(value: Boolean) = this.toEntry(toFeelString(value))
  fun BooleanOutputDefinition.resultTrue() = resultValue(true)
  fun BooleanOutputDefinition.resultFalse() = resultValue(false)

  // string

  fun StringInputDefinition.feelEqual(value: String) = FeelInputVariable(this, FeelComparisonCondition(value, Equal))
  fun StringInputDefinition.feelMatchOne(vararg values: String) = FeelInputVariable(this, FeelDisjunctionCondition(values.toSet()))
  fun StringInputDefinition.feelMatchNone(vararg values: String) = FeelInputVariable(this, FeelDisjunctionCondition(values.toSet())).negate()

  fun StringOutputDefinition.resultValue(value: String) = this.toEntry(toFeelString(value))

  // integer

  fun IntegerInputDefinition.feelEqual(value: Int) = feelComparison(value, Equal)
  fun IntegerInputDefinition.feelGreaterThan(value: Int) = feelComparison(value, Greater)
  fun IntegerInputDefinition.feelGreaterThanOrEqual(value: Int) = feelComparison(value, GreaterOrEqual)
  fun IntegerInputDefinition.feelLessThan(value: Int) = feelComparison(value, Less)
  fun IntegerInputDefinition.feelLessThanOrEqual(value: Int) = feelComparison(value, LessOrEqual)
  fun IntegerInputDefinition.feelComparison(value: Int, type: ComparisonType) = FeelInputVariable(this, FeelComparisonCondition(value, type))
  fun IntegerInputDefinition.feelMatchOne(vararg values: Int) = FeelInputVariable(this, FeelDisjunctionCondition(values.toSet()))
  fun IntegerInputDefinition.feelBetween(begin: Int, end: Int) = feelInterval(begin, end, RangeType.Include)
  fun IntegerInputDefinition.feelBetweenExclude(begin: Int, end: Int) = feelInterval(begin, end, RangeType.Exclude)
  fun IntegerInputDefinition.feelInterval(begin: Int, end: Int, type: RangeType) = FeelInputVariable(this, FeelIntervalCondition(begin = begin, end = end, type = type))

  fun IntegerOutputDefinition.resultValue(value: Int) = this.toEntry(toFeelString(value))

  // long

  fun LongInputDefinition.feelEqual(value: Long) = feelComparison(value, Equal)
  fun LongInputDefinition.feelGreaterThan(value: Long) = feelComparison(value, Greater)
  fun LongInputDefinition.feelGreaterThanOrEqual(value: Long) = feelComparison(value, GreaterOrEqual)
  fun LongInputDefinition.feelLessThan(value: Long) = feelComparison(value, Less)
  fun LongInputDefinition.feelLessThanOrEqual(value: Long) = feelComparison(value, LessOrEqual)
  fun LongInputDefinition.feelComparison(value: Long, type: ComparisonType) = FeelInputVariable(this, FeelComparisonCondition(value, type))
  fun LongInputDefinition.feelMatchOne(vararg values: Long) = FeelInputVariable(this, FeelDisjunctionCondition(values.toSet()))
  fun LongInputDefinition.feelBetween(begin: Long, end: Long) = feelInterval(begin, end, RangeType.Include)
  fun LongInputDefinition.feelBetweenExclude(begin: Long, end: Long) = feelInterval(begin, end, RangeType.Exclude)
  fun LongInputDefinition.feelInterval(begin: Long, end: Long, type: RangeType) = FeelInputVariable(this, FeelIntervalCondition(begin = begin, end = end, type = type))

  fun LongOutputDefinition.resultValue(value: Long) = this.toEntry(toFeelString(value))

  // double

  fun DoubleInputDefinition.feelEqual(value: Double) = feelComparison(value, Equal)
  fun DoubleInputDefinition.feelGreaterThan(value: Double) = feelComparison(value, Greater)
  fun DoubleInputDefinition.feelGreaterThanOrEqual(value: Double) = feelComparison(value, GreaterOrEqual)
  fun DoubleInputDefinition.feelLessThan(value: Double) = feelComparison(value, Less)
  fun DoubleInputDefinition.feelLessThanOrEqual(value: Double) = feelComparison(value, LessOrEqual)
  fun DoubleInputDefinition.feelComparison(value: Double, type: ComparisonType) = FeelInputVariable(this, FeelComparisonCondition(value, type))
  fun DoubleInputDefinition.feelMatchOne(vararg values: Double) = FeelInputVariable(this, FeelDisjunctionCondition(values.toSet()))
  fun DoubleInputDefinition.feelBetween(begin: Double, end: Double) = feelInterval(begin, end, RangeType.Include)
  fun DoubleInputDefinition.feelBetweenExclude(begin: Double, end: Double) = feelInterval(begin, end, RangeType.Exclude)
  fun DoubleInputDefinition.feelInterval(begin: Double, end: Double, type: RangeType) = FeelInputVariable(this, FeelIntervalCondition(begin = begin, end = end, type = type))

  fun DoubleOutputDefinition.resultValue(value: Double) = this.toEntry(toFeelString(value))

  // date

  fun DateInputDefinition.feelExactly(value: Date) = feelComparison(value, Equal)
  fun DateInputDefinition.feelBefore(value: Date) = feelComparison(value, Less)
  fun DateInputDefinition.feelAfter(value: Date) = feelComparison(value, Greater)
  fun DateInputDefinition.feelBetween(begin: Date, end: Date) = FeelInputVariable(this, FeelIntervalCondition(begin, end, RangeType.Include))
  fun DateInputDefinition.feelComparison(value: Date, type: ComparisonType) = FeelInputVariable(this, FeelComparisonCondition(value, type))

  fun DateOutputDefinition.resultValue(value: Date) = this.toEntry(toFeelString(value))
}
