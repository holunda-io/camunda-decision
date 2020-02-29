package io.holunda.decision.model.element

interface RuleEntry<T : ColumnDefinition> {
  val definition: T
  val expression: String?
}

data class InputEntry(
  override val definition: InputDefinition,
  override val expression: String?
) : RuleEntry<InputDefinition>

data class OutputEntry(
  override val definition: OutputDefinition,
  override val expression: String?
) : RuleEntry<OutputDefinition>
