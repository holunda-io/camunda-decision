package io.holunda.decision.model.io

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment
import io.holunda.decision.model.DmnDecisionTable
import io.holunda.decision.model.DmnRule
import io.holunda.decision.model.DmnRules
import java.util.*

object DmnWriter {

  fun render(table: DmnDecisionTable) : String = AsciiTable().apply {
    val inputs = table.header.inputs.size
    val outputs = table.header.outputs.size

    addRule()
    addRow(rowTitle(table))
    addRule()

    addRow(rowHeader(table.header))
    addRule()

    addRow(rowSpan("INPUT", inputs) + rowSpan("OUTPUT", outputs ) + "DESCRIPTION")

    addRule()
    addRule()

    table.rules.forEach {
      addRow(rowRule(it))
      addRule()
    }

    setTextAlignment(TextAlignment.CENTER)
  }.render()

  private fun rowTitle(table: DmnDecisionTable) : List<String?> {

    val title = "${table.name} (${table.key}) ${Optional.ofNullable(table.versionTag ).map { "- v$it" }.orElse("") }"

    return rowSpan(title, table.header.inputs.size + table.header.outputs.size + 1)
  }

  private fun rowRule(rule: DmnRule) : List<String> {
    val row = mutableListOf<String>()

    rule.inputs.map { blank(it.expression.getExpression()) }.forEach{row.add(it)}
    rule.outputs.map { it.result.getResult() }.forEach{row.add(it)}

    row.add(rule.description?:"-")

    return  row
  }

  private fun blank(value:String?): String {
    return if (value == null || value.trim() == "") "-" else value
  }

  private fun rowHeader(header: DmnDecisionTable.Header) : List<String> {
    val row = mutableListOf<String>()

    header.inputs.map { "${it.label}<br/>${it.key} (${it.type})" }.forEach { row.add(it) }
    header.outputs.map { "${it.label}<br/>${it.key} (${it.type})" }.forEach { row.add(it) }

    row.add(" ")

    return row
  }

  private fun rowSpan(text:String, span:Int): MutableList<String?> {
    val nulls =  arrayOfNulls<String?>(span-1).toMutableList()
    return (nulls + text).toMutableList()
  }
}
