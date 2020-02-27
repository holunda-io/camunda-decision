package io.holunda.decision.model

import io.holunda.decision.model.data.DataType

data class OutputDefinition(
  override val key: String,
  override val label: String,
  override val type: DataType
) : CamundaDecisionModel.ColumnDefinition {
  companion object {
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
}
