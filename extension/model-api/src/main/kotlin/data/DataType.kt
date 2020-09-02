package io.holunda.decision.model.api.data

import io.holunda.camunda.bpm.data.factory.BasicVariableFactory
import io.holunda.decision.model.api.definition.*
import io.holunda.decision.model.api.element.InputEntry
import io.holunda.decision.model.api.element.OutputEntry
import java.util.*

sealed class DataType<T : Any>(
  typeRefEnum: TypeRefEnum,
  val type: Class<T>
) {

  enum class TypeRefEnum { string, boolean, integer, long, double, date }

  companion object {

    fun valueByTypeRef(typeRef: String): DataType<*> = when (TypeRefEnum.valueOf(typeRef)) {
      TypeRefEnum.string -> StringDataType
      TypeRefEnum.boolean -> BooleanDataType
      TypeRefEnum.integer -> IntegerDataType
      TypeRefEnum.long -> LongDataType
      TypeRefEnum.double -> DoubleDataType
      TypeRefEnum.date -> DateDataType
    }
  }

  val typeRef = typeRefEnum.name
  val name = typeRef.toUpperCase()

  fun variableFactory(name: String) = BasicVariableFactory<T>(name, type)

  abstract fun inputDefinition(key: String, label: String): InputDefinition<T>
  abstract fun outputDefinition(key: String, label: String): OutputDefinition<T>

  fun inputEntry(definition: InputDefinition<T>, expression: String?) = InputEntry(definition, expression)
  fun inputEntry(key: String, label: String, expression: String?) = InputEntry(inputDefinition(key, label), expression)

  fun outputEntry(definition: OutputDefinition<T>, expression: String?) = OutputEntry(definition, expression)
  fun outputEntry(key: String, label: String, expression: String?) = OutputEntry(outputDefinition(key, label), expression)

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

object StringDataType : DataType<String>(typeRefEnum = TypeRefEnum.string, type = String::class.java) {
  override fun inputDefinition(key: String, label: String): StringInputDefinition = StringInputDefinition(key, label)
  override fun outputDefinition(key: String, label: String): StringOutputDefinition = StringOutputDefinition(key, label)
}

object BooleanDataType : DataType<Boolean>(typeRefEnum = TypeRefEnum.boolean, type = Boolean::class.java) {
  override fun inputDefinition(key: String, label: String): BooleanInputDefinition = BooleanInputDefinition(key, label)
  override fun outputDefinition(key: String, label: String): BooleanOutputDefinition = BooleanOutputDefinition(key, label)
}

sealed class NumberDataType<T : Number>(typeRefEnum: TypeRefEnum, type: Class<T>) : DataType<T>(typeRefEnum, type)

object IntegerDataType : NumberDataType<Int>(typeRefEnum = TypeRefEnum.integer, type = Int::class.java) {
  override fun inputDefinition(key: String, label: String): IntegerInputDefinition = IntegerInputDefinition(key, label)
  override fun outputDefinition(key: String, label: String): IntegerOutputDefinition = IntegerOutputDefinition(key, label)
}

object LongDataType : NumberDataType<Long>(typeRefEnum = TypeRefEnum.long, type = Long::class.java) {
  override fun inputDefinition(key: String, label: String): LongInputDefinition = LongInputDefinition(key, label)
  override fun outputDefinition(key: String, label: String): LongOutputDefinition = LongOutputDefinition(key, label)
}

object DoubleDataType : NumberDataType<Double>(typeRefEnum = TypeRefEnum.double, type = Double::class.java) {
  override fun inputDefinition(key: String, label: String): DoubleInputDefinition = DoubleInputDefinition(key, label)
  override fun outputDefinition(key: String, label: String): DoubleOutputDefinition = DoubleOutputDefinition(key, label)
}

object DateDataType : DataType<Date>(typeRefEnum = TypeRefEnum.date, type = Date::class.java) {
  override fun inputDefinition(key: String, label: String): DateInputDefinition = DateInputDefinition(key, label)
  override fun outputDefinition(key: String, label: String): DateOutputDefinition = DateOutputDefinition(key, label)
}
