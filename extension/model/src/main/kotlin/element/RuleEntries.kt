package io.holunda.decision.model.element


sealed class RuleEntry<T:Any>(
  open val definition: ColumnDefinition<T>,
  open val expression: String?
)

data class InputEntry<T : Any>(
  override val definition: InputDefinition<T>,
  override val expression: String?
) : RuleEntry<T>(definition, expression)

data class OutputEntry<T : Any>(
  override val definition: OutputDefinition<T>,
  override val expression: String?
) : RuleEntry<T>(definition, expression)
