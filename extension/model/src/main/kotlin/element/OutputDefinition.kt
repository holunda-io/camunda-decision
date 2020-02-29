package io.holunda.decision.model.element

import io.holunda.decision.model.data.DataType

object OutputDefinitionFactory {
  @JvmStatic
  @JvmOverloads
  fun stringOutput(key: String, label: String = key) = OutputDefinition(key, label, DataType.STRING)

  @JvmStatic
  @JvmOverloads
  fun booleanOutput(key: String, label: String = key) = OutputDefinition(key, label, DataType.BOOLEAN)

  @JvmStatic
  @JvmOverloads
  fun integerOutput(key: String, label: String = key) = OutputDefinition(key, label, DataType.INTEGER)

  @JvmStatic
  @JvmOverloads
  fun longOutput(key: String, label: String = key) = OutputDefinition(key, label, DataType.LONG)

  @JvmStatic
  @JvmOverloads
  fun doubleOutput(key: String, label: String = key) = OutputDefinition(key, label, DataType.DOUBLE)

  @JvmStatic
  @JvmOverloads
  fun dateOutput(key: String, label: String = key) = OutputDefinition(key, label, DataType.DATE)

}

