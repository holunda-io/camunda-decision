package io.holunda.decision.model.element


import io.holunda.decision.model.data.DataType

interface ColumnDefinition {
  val key:String
  val label:String
  val type: DataType
}

data class InputDefinition(
  override val key: String,
  override val label: String,
  override val type: DataType
) : ColumnDefinition

data class OutputDefinition(
  override val key: String,
  override val label: String,
  override val type: DataType
) : ColumnDefinition
