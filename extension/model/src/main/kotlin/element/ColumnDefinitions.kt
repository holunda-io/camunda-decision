package io.holunda.decision.model.element

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.decision.model.data.*
import java.util.*

interface ColumnDefinition<T : Any> {
  val key: String
  val label: String
  val dataType: DataType<T>

  val typeRef: String
    get() = dataType.typeRef
  val type: Class<T>
    get() = dataType.type
  val variableFactory:VariableFactory<T> get() = dataType.variableFactory(key)
}

sealed class InputDefinition<T : Any>(override val key: String, override val label: String, override val dataType: DataType<T>) : ColumnDefinition<T>
data class StringInputDefinition(override val key: String, override val label: String) : InputDefinition<String>(key, label, StringDataType)
data class BooleanInputDefinition(override val key: String, override val label: String) : InputDefinition<Boolean>(key, label, BooleanDataType)
data class IntegerInputDefinition(override val key: String, override val label: String) : InputDefinition<Int>(key, label, IntegerDataType)
data class LongInputDefinition(override val key: String, override val label: String) : InputDefinition<Long>(key, label, LongDataType)
data class DoubleInputDefinition(override val key: String, override val label: String) : InputDefinition<Double>(key, label, DoubleDataType)
data class DateInputDefinition(override val key: String, override val label: String) : InputDefinition<Date>(key, label, DateDataType)

sealed class OutputDefinition<T : Any>(override val key: String, override val label: String, override val dataType: DataType<T>) : ColumnDefinition<T>
data class StringOutputDefinition(override val key: String, override val label: String) : OutputDefinition<String>(key, label, StringDataType)
data class BooleanOutputDefinition(override val key: String, override val label: String) : OutputDefinition<Boolean>(key, label, BooleanDataType)
data class IntegerOutputDefinition(override val key: String, override val label: String) : OutputDefinition<Int>(key, label, IntegerDataType)
data class LongOutputDefinition(override val key: String, override val label: String) : OutputDefinition<Long>(key, label, LongDataType)
data class DoubleOutputDefinition(override val key: String, override val label: String) : OutputDefinition<Double>(key, label, DoubleDataType)
data class DateOutputDefinition(override val key: String, override val label: String) : OutputDefinition<Date>(key, label, DateDataType)
