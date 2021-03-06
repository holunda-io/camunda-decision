package io.holunda.decision.model.ascii

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment
import io.holunda.decision.model.api.definition.ColumnDefinition
import io.holunda.decision.model.api.element.DmnDecisionTable
import io.holunda.decision.model.api.element.DmnDiagram
import io.holunda.decision.model.api.element.DmnRule
import io.holunda.decision.model.api.entry.RuleEntry
import java.util.*

/**
 * Prints decision tables to ascii.
 */
object DmnWriter {

  /**
   * @return ascii table containing the diagrams decison tables.
   */
  fun render(diagram: DmnDiagram) = diagram.decisionTables.map { render(it) }.joinToString("\n\n")

  /**
   * @return ascii table representing a single decision table.
   */
  fun render(table: DmnDecisionTable): String {
    require(table.header.inputs.isNotEmpty()) { "table '${table.decisionDefinitionKey}' has no inputs" }
    require(table.header.outputs.isNotEmpty()) { "table '${table.decisionDefinitionKey}' has no outputs" }
    val ascii = AsciiDmnTable(table.header.inputs.size, table.header.outputs.size)

    ascii.addRow("${table.name} ('${table.decisionDefinitionKey}') ${Optional.ofNullable(table.versionTag).map { "- v$it" }.orElse("")}" to ascii.columns)

    ascii.addRow(table.header.allDefinitions().map { "${it.label}<br/>'${it.key}' (${it.dataType.name})" }.toMutableList().apply { add(table.hitPolicy.name) })

    ascii.addRow(
      "- INPUT -" to ascii.inputs,
      "- OUTPUT - " to ascii.outputs,
      "- DESCRIPTION -" to 1
    )

    table.rules.forEach {
      ascii.addRow(rowRule(it))
    }

    return ascii.render()
  }

  private fun rowRule(rule: DmnRule): List<String> {
    val row = mutableListOf<String>()
    rule.allEntries().map { it.expression ?: "-" }.map { if (it == "") "-" else it }.forEach { row.add(it) }

    row.add(rule.description ?: "-")

    return row
  }

  private fun DmnRule.allEntries(): List<RuleEntry<*>> {
    val list = mutableListOf<RuleEntry<*>>()
    this.inputs.forEach { list.add(it) }
    this.outputs.forEach { list.add(it) }

    return list
  }

  private fun DmnDecisionTable.Header.allDefinitions(): MutableList<ColumnDefinition<*>> {
    val definitions = mutableListOf<ColumnDefinition<*>>()
    this.inputs.forEach { definitions.add(it) }
    this.outputs.forEach { definitions.add(it) }

    return definitions
  }

  private class AsciiDmnTable(val inputs: Int, val outputs: Int) {
    val columns = inputs + outputs + 1
    val asciiTable = AsciiTable().apply {
      addRule()
    }

    fun addRow(vararg rowSpans: Pair<String, Int>): AsciiDmnTable {
      require(rowSpans.map { it.second }.sum() == columns) { "columns must have size=$columns, was $rowSpans" }

      val cols = mutableListOf<String?>()

      rowSpans.forEach { (text, span) ->
        val nulls = arrayOfNulls<String?>(span - 1).toMutableList()
        cols.addAll(nulls + text)
      }

      return addRow(cols)
    }

    fun addRow(cols: List<String?>) = apply {
      require(cols.size == columns) { "columns must have size=$columns, was $cols" }
      asciiTable.addRow(cols)
      asciiTable.addRule()
    }

    fun render() = asciiTable.apply {
      setTextAlignment(TextAlignment.CENTER)
    }.render()!!
  }
}
