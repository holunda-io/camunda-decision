package io.holunda.decision.model.api.definition

import io.holunda.decision.model.api.data.*
import java.util.*

/**
 * Output Definitions declare key and type (and an optional label) for output (aka result) columns.
 * These are used a) define the decision table columns and b) to read results after evaluation.
 *
 * The concrete outputDefinitions are defined as sealed-subclasses, because we need to limit the generic type parameter `T`
 * to the 6 allowed values: string, boolean, integer, long, double, date.
 */
sealed class OutputDefinition<T : Any>(override val key: String, override val label: String, override val dataType: DataType<T>) : ColumnDefinition<T> {
  abstract fun toInputDefinition(): InputDefinition<T>
}

data class StringOutputDefinition(override val key: String, override val label: String) : OutputDefinition<String>(key, label, StringDataType) {
  override fun toInputDefinition(): StringInputDefinition = StringInputDefinition(key, label)
}

data class BooleanOutputDefinition(override val key: String, override val label: String) : OutputDefinition<Boolean>(key, label, BooleanDataType) {
  override fun toInputDefinition(): BooleanInputDefinition = BooleanInputDefinition(key, label)
}

sealed class NumberOutputDefinition<T : Number>(key: String, label: String, dataType: NumberDataType<T>) : OutputDefinition<T>(key, label, dataType)


data class IntegerOutputDefinition(override val key: String, override val label: String) : NumberOutputDefinition<Int>(key, label, IntegerDataType) {
  override fun toInputDefinition(): IntegerInputDefinition = IntegerInputDefinition(key, label)
}

data class LongOutputDefinition(override val key: String, override val label: String) : NumberOutputDefinition<Long>(key, label, LongDataType) {
  override fun toInputDefinition(): LongInputDefinition = LongInputDefinition(key, label)
}

data class DoubleOutputDefinition(override val key: String, override val label: String) : NumberOutputDefinition<Double>(key, label, DoubleDataType) {
  override fun toInputDefinition(): DoubleInputDefinition = DoubleInputDefinition(key, label)
}

data class DateOutputDefinition(override val key: String, override val label: String) : OutputDefinition<Date>(key, label, DateDataType) {
  override fun toInputDefinition(): DateInputDefinition = DateInputDefinition(key, label)
}
