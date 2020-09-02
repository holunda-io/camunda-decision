package io.holunda.decision.model.api.element

import io.holunda.decision.model.api.CamundaDecisionModelApi.generateId
import io.holunda.decision.model.api.Id
import io.holunda.decision.model.api.definition.InputDefinition
import io.holunda.decision.model.api.definition.OutputDefinition
import io.holunda.decision.model.api.entry.InputEntry
import io.holunda.decision.model.api.entry.OutputEntry

/**
 * Immutable representation of single row in a decision table.
 * In the dmn xml, this corresponds to a single `<rule />` element.
 */
data class DmnRule(
  /**
   * A rule has an id, which is unique for the diagram. It's best to have this generated.
   */
  val id: Id = idGenerator(),

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
  val inputs: List<InputEntry<*>>,

  /**
   * A rule has an unbounded list of output entries.
   * These are the result cells of the table.
   *
   * Must be non empty. Might contain entries without expression (empty cells).
   *
   */
  val outputs: List<OutputEntry<*>>
) {

  companion object {
    val idGenerator = { generateId("DecisionRule") }
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

  fun inputEntries(definitions: List<InputDefinition<*>>) = definitions.map { header ->
    inputs.find { it.definition == header }?.expression
  }

  fun outputEntries(definitions: List<OutputDefinition<*>>) = definitions.map { header ->
    outputs.find { it.definition == header }?.expression
  }

}

/**
 * Assuming that a rule contains all definition entries, we can derive the header fields from it.
 */
fun DmnRule.toHeader() = DmnDecisionTable.Header(
  inputs = this.inputs.map { it.definition },
  outputs = this.outputs.map { it.definition }
)
