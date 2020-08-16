package io.holunda.decision.model

import io.holunda.camunda.bpm.data.factory.BasicVariableFactory
import io.holunda.decision.model.element.column.*
import io.holunda.decision.model.element.row.InputEntry
import io.holunda.decision.model.element.row.OutputEntry
import java.util.*

sealed class DataType<T : Any>(
  val type: Class<T>,
  val name: String = type.simpleName.toUpperCase(),
  val typeRef: String = name.toLowerCase()
) {

  companion object {
    fun valueByTypeRef(typeRef: String): DataType<*> = when (typeRef) {
      "string" -> StringDataType
      "boolean" -> BooleanDataType
      "integer" -> IntegerDataType
      "long" -> LongDataType
      "double" -> DoubleDataType
      "date" -> DateDataType
      else -> throw IllegalArgumentException("typeRef ('$typeRef') must be one of string, boolean, integer, long, double, date")
    }
  }

  fun variableFactory(name: String) = BasicVariableFactory<T>(name, type)

  abstract fun inputDefinition(key: String, label: String): InputDefinition<T>
  abstract fun outputDefinition(key: String, label: String): OutputDefinition<T>

  fun inputEntry(definition: InputDefinition<T>, expression: String?) = InputEntry<T>(definition, expression)
  fun inputEntry(key: String, label: String, expression: String?) = InputEntry<T>(inputDefinition(key, label), expression)

  fun outputEntry(definition: OutputDefinition<T>, expression: String?) = OutputEntry<T>(definition, expression)
  fun outputEntry(key: String, label: String, expression: String?) = OutputEntry<T>(outputDefinition(key, label), expression)

  override fun toString() = """DataType(name="$name", typeRef="$typeRef", type=$type)"""

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as DataType<*>

    if (type != other.type) return false
    if (name != other.name) return false
    if (typeRef != other.typeRef) return false

    return true
  }

  override fun hashCode(): Int {
    var result = type.hashCode()
    result = 31 * result + name.hashCode()
    result = 31 * result + typeRef.hashCode()
    return result
  }
}

object StringDataType : DataType<String>(name = "STRING", type = String::class.java) {
  override fun inputDefinition(key: String, label: String): StringInputDefinition = StringInputDefinition(key, label)
  override fun outputDefinition(key: String, label: String): StringOutputDefinition = StringOutputDefinition(key, label)
}

object BooleanDataType : DataType<Boolean>(name = "BOOLEAN", type = Boolean::class.java) {
  override fun inputDefinition(key: String, label: String): BooleanInputDefinition = BooleanInputDefinition(key, label)
  override fun outputDefinition(key: String, label: String): BooleanOutputDefinition = BooleanOutputDefinition(key, label)
}

object IntegerDataType : DataType<Int>(name = "INTEGER", type = Int::class.java) {
  override fun inputDefinition(key: String, label: String): IntegerInputDefinition = IntegerInputDefinition(key, label)
  override fun outputDefinition(key: String, label: String): IntegerOutputDefinition = IntegerOutputDefinition(key, label)
}

object LongDataType : DataType<Long>(name = "LONG", type = Long::class.java) {
  override fun inputDefinition(key: String, label: String): LongInputDefinition = LongInputDefinition(key, label)
  override fun outputDefinition(key: String, label: String): LongOutputDefinition = LongOutputDefinition(key, label)
}

object DoubleDataType : DataType<Double>(name = "DOUBLE", type = Double::class.java) {
  override fun inputDefinition(key: String, label: String): DoubleInputDefinition = DoubleInputDefinition(key, label)
  override fun outputDefinition(key: String, label: String): DoubleOutputDefinition = DoubleOutputDefinition(key, label)
}

object DateDataType : DataType<Date>(name = "DATE", type = Date::class.java) {
  override fun inputDefinition(key: String, label: String): DateInputDefinition = DateInputDefinition(key, label)
  override fun outputDefinition(key: String, label: String): DateOutputDefinition = DateOutputDefinition(key, label)
}
