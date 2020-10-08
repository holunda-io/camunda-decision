package io.holunda.decision.model.api.definition

import io.holunda.decision.model.api.data.*
import java.util.*

/**
 * Input Definitions declare key and type (and an optional label) for input columns.
 * These are used a) define the decision table columns and b) to pass variables during evaluation.
 *
 * The concrete inputDefinitions are defined as sealed-subclasses, because we need to limit the generic type parameter `T`
 * to the 6 allowed values: string, boolean, integer, long, double, date.
 */
sealed class InputDefinition<T : Any>(override val key: String, override val label: String, override val dataType: DataType<T>) : ColumnDefinition<T> {
  abstract fun toOutputDefinition(): OutputDefinition<T>
}

data class StringInputDefinition(override val key: String, override val label: String) : InputDefinition<String>(key, label, StringDataType) {
  override fun toOutputDefinition(): StringOutputDefinition = StringOutputDefinition(key, label)
}

data class BooleanInputDefinition(override val key: String, override val label: String) : InputDefinition<Boolean>(key, label, BooleanDataType) {
  override fun toOutputDefinition(): BooleanOutputDefinition = BooleanOutputDefinition(key, label)
}

sealed class NumberInputDefinition<T : Number>(key: String, label: String, dataType: NumberDataType<T>) : InputDefinition<T>(key, label, dataType)

data class IntegerInputDefinition(override val key: String, override val label: String) : NumberInputDefinition<Integer>(key, label, IntegerDataType) {
  override fun toOutputDefinition(): IntegerOutputDefinition = IntegerOutputDefinition(key, label)
}

data class LongInputDefinition(override val key: String, override val label: String) : NumberInputDefinition<java.lang.Long>(key, label, LongDataType) {
  override fun toOutputDefinition(): LongOutputDefinition = LongOutputDefinition(key, label)
}

data class DoubleInputDefinition(override val key: String, override val label: String) : NumberInputDefinition<java.lang.Double>(key, label, DoubleDataType) {
  override fun toOutputDefinition(): DoubleOutputDefinition = DoubleOutputDefinition(key, label)
}

data class DateInputDefinition(override val key: String, override val label: String) : InputDefinition<Date>(key, label, DateDataType) {
  override fun toOutputDefinition(): DateOutputDefinition = DateOutputDefinition(key, label)
}
