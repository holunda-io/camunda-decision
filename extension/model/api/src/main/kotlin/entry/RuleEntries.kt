package io.holunda.decision.model.api.entry

import io.holunda.decision.model.api.FeelString
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
  override val expression: FeelString?
) : RuleEntry<T>(definition, expression)

/**
 * Output cells of decision table.
 */
data class OutputEntry<T : Any>(
  override val definition: OutputDefinition<T>,
  override val expression: FeelString?
) : RuleEntry<T>(definition, expression)


fun <T : Any> InputDefinition<T>.toEntry(expression: FeelString?) = InputEntry(this, expression)
fun <T : Any> OutputDefinition<T>.toEntry(expression: FeelString?) = OutputEntry(this, expression)

