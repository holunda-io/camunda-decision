package io.holunda.decision.model.element.row

import io.holunda.decision.model.element.column.InputDefinition
import io.holunda.decision.model.element.column.OutputDefinition

/**
 * Immutable representation of single row in a decision table.
 * In the dmn xml, this corresponds to a single `<rule />` element.
 */
data class DmnRule(
  /**
   * A rule has an id, which is unique for the diagram. It's best to have this generated.
   */
  val id: String? = null,

  /**
   * A rule might have a description, which (if present) is displayed in the "annotations" column next to the outputs.
   */
  val description: String? = null,

  /**
   * A rule has an unbounded list of input entries.
   * These are the condition cells of the table.
   *
   * Must be non empty. Might contain entries without expression (empty cells).
   */
  val inputs: List<InputEntry<*>> = listOf(),

  /**
   * A rule has an unbounded list of output entries.
   * These are the result cells of the table.
   *
   * Must be non empty. Might contain entries without expression (empty cells).
   *
   */
  val outputs: List<OutputEntry<*>> = listOf()
) {

  companion object {

    private fun <T : Any> add(list: List<T>, entry: T) = list.toMutableList()
      .apply { this.add(entry) }
      .toList()
  }

  /**
   * All input definitions.
   */
  val inputDefinitions by lazy {
    inputs.map { it.definition }
  }

  /**
   * All output definitions.
   */
  val outputDefinitions by lazy {
    outputs.map { it.definition }
  }

  @Deprecated("modifiying rules should only be done in the generator module.")
  fun addInput(entry: InputEntry<*>) = copy(inputs = add(inputs, entry))
  //fun addInput(definition: InputDefinition, expression: Expression) = addInput(InputEntry(definition, expression))
  @Deprecated("modifiying rules should only be done in the generator module.")
  fun <T:Any> addInput(definition: InputDefinition<T>, expression: String) = addInput(InputEntry(definition, expression))

  @Deprecated("modifiying rules should only be done in the generator module.")
  fun addOutput(entry: OutputEntry<*>) = copy(outputs = add(outputs, entry))
  //fun addOutput(definition: OutputDefinition, result: Result) = addOutput(OutputEntry(definition, result))
  @Deprecated("modifiying rules should only be done in the generator module.")
  fun <T:Any> addOutput(definition: OutputDefinition<T>, result: String) = addOutput(OutputEntry(definition, result))


  fun inputEntries(definitions: List<InputDefinition<*>>) = definitions.map { header ->
    inputs.find { it.definition == header }?.expression
  }

  fun outputEntries(definitions: List<OutputDefinition<*>>) = definitions.map { header ->
    outputs.find { it.definition == header }?.expression
  }

}
