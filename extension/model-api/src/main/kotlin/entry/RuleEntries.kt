package io.holunda.decision.model.api.element

import io.holunda.decision.model.api.definition.ColumnDefinition
import io.holunda.decision.model.api.definition.InputDefinition
import io.holunda.decision.model.api.definition.OutputDefinition

/**
 * Common super class of input- and output entries.
 */
sealed class RuleEntry<T : Any>(
  open val definition: ColumnDefinition<T>,
  open val expression: String?
)

/**
 * Input cells of decision table.
 */
data class InputEntry<T : Any>(
  override val definition: InputDefinition<T>,
  override val expression: String?
) : RuleEntry<T>(definition, expression)

/**
 * Output cells of decision table.
 */
data class OutputEntry<T : Any>(
  override val definition: OutputDefinition<T>,
  override val expression: String?
) : RuleEntry<T>(definition, expression)


fun <T : Any> InputDefinition<T>.toEntry(expression: String?) = InputEntry<T>(this, expression)
fun <T : Any> OutputDefinition<T>.toEntry(expression: String?) = OutputEntry<T>(this, expression)
