package io.holunda.decision.model

import io.holunda.decision.model.data.DataType

data class InputDefinition(
  override val key: String,
  override val label: String,
  override val type: DataType
) : CamundaDecisionModel.ColumnDefinition {
  companion object {
    @JvmStatic
    @JvmOverloads
    fun stringInput(key: String, label: String = key) = InputDefinition(key,label, DataType.STRING)

    @JvmStatic
    @JvmOverloads
    fun booleanInput(key: String, label: String = key) = InputDefinition(key,label, DataType.BOOLEAN)

    @JvmStatic
    @JvmOverloads
    fun integerInput(key: String, label: String = key) = InputDefinition(key,label, DataType.INTEGER)

    @JvmStatic
    @JvmOverloads
    fun longInput(key: String, label: String = key) = InputDefinition(key,label, DataType.LONG)

    @JvmStatic
    @JvmOverloads
    fun doubleInput(key: String, label: String = key) = InputDefinition(key,label, DataType.DOUBLE)

    @JvmStatic
    @JvmOverloads
    fun dateInput(key: String, label: String = key) = InputDefinition(key,label, DataType.DATE)

  }
}
