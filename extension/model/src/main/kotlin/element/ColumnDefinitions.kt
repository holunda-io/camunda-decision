package io.holunda.decision.model.element

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.decision.model.*
import java.util.*

interface ColumnDefinition<T : Any> {
  val key: String
  val label: String
  val dataType: DataType<T>

  val typeRef: String get() = dataType.typeRef
  val type: Class<T> get() = dataType.type
  val variableFactory: VariableFactory<T> get() = dataType.variableFactory(key)
}

sealed class InputDefinition<T : Any>(override val key: String, override val label: String, override val dataType: DataType<T>) : ColumnDefinition<T> {
  abstract fun toOutputDefinition() : OutputDefinition<T>
}
data class StringInputDefinition(override val key: String, override val label: String) : InputDefinition<String>(key, label, StringDataType) {
  override fun toOutputDefinition(): StringOutputDefinition = StringOutputDefinition(key, label)
}

data class BooleanInputDefinition(override val key: String, override val label: String) : InputDefinition<Boolean>(key, label, BooleanDataType) {
  override fun toOutputDefinition(): BooleanOutputDefinition = BooleanOutputDefinition(key, label)
}
data class IntegerInputDefinition(override val key: String, override val label: String) : InputDefinition<Int>(key, label, IntegerDataType) {
  override fun toOutputDefinition(): IntegerOutputDefinition = IntegerOutputDefinition(key, label)
}
data class LongInputDefinition(override val key: String, override val label: String) : InputDefinition<Long>(key, label, LongDataType) {
  override fun toOutputDefinition(): LongOutputDefinition = LongOutputDefinition(key, label)
}
data class DoubleInputDefinition(override val key: String, override val label: String) : InputDefinition<Double>(key, label, DoubleDataType) {
  override fun toOutputDefinition(): DoubleOutputDefinition = DoubleOutputDefinition(key, label)
}
data class DateInputDefinition(override val key: String, override val label: String) : InputDefinition<Date>(key, label, DateDataType) {
  override fun toOutputDefinition(): DateOutputDefinition = DateOutputDefinition(key, label)
}

sealed class OutputDefinition<T : Any>(override val key: String, override val label: String, override val dataType: DataType<T>) : ColumnDefinition<T> {
  abstract fun toInputDefinition(): InputDefinition<T>
}
data class StringOutputDefinition(override val key: String, override val label: String) : OutputDefinition<String>(key, label, StringDataType) {
  override fun toInputDefinition(): StringInputDefinition = StringInputDefinition(key, label)
}

data class BooleanOutputDefinition(override val key: String, override val label: String) : OutputDefinition<Boolean>(key, label, BooleanDataType){
  override fun toInputDefinition(): BooleanInputDefinition = BooleanInputDefinition(key, label)
}
data class IntegerOutputDefinition(override val key: String, override val label: String) : OutputDefinition<Int>(key, label, IntegerDataType){
  override fun toInputDefinition(): IntegerInputDefinition = IntegerInputDefinition(key, label)
}
data class LongOutputDefinition(override val key: String, override val label: String) : OutputDefinition<Long>(key, label, LongDataType){
  override fun toInputDefinition(): LongInputDefinition = LongInputDefinition(key, label)
}
data class DoubleOutputDefinition(override val key: String, override val label: String) : OutputDefinition<Double>(key, label, DoubleDataType){
  override fun toInputDefinition(): DoubleInputDefinition = DoubleInputDefinition(key, label)
}
data class DateOutputDefinition(override val key: String, override val label: String) : OutputDefinition<Date>(key, label, DateDataType){
  override fun toInputDefinition(): DateInputDefinition = DateInputDefinition(key, label)
}
