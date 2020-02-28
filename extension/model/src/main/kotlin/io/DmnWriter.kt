package io.holunda.decision.model.io

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment
import io.holunda.decision.model.CamundaDecisionModel
import io.holunda.decision.model.DmnDecisionTable
import io.holunda.decision.model.DmnRule
import java.util.*

object DmnWriter {

  fun render(table: DmnDecisionTable): String {
    var ascii = AsciiDmnTable(table.header.inputs.size, table.header.outputs.size)

    ascii.addRow("${table.name} ('${table.key}') ${Optional.ofNullable(table.versionTag ).map { "- v$it" }.orElse("") }" to  ascii.columns)

    ascii.addRow(table.header.allDefinitions().map { "${it.label}<br/>'${it.key}' (${it.type})"  }.toMutableList().apply { add(" ") })

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

  private fun rowRule(rule: DmnRule) : List<String> {
    val row = mutableListOf<String>()

    rule.inputs.map { blank(it.expression.getExpression()) }.forEach{row.add(it)}
    rule.outputs.map { it.result.getResult() }.forEach{row.add(it)}

    row.add(rule.description?:"-")

    return  row
  }

  private fun DmnDecisionTable.Header.allDefinitions(): MutableList<CamundaDecisionModel.ColumnDefinition> {
    var definitions = mutableListOf<CamundaDecisionModel.ColumnDefinition>()
    this.inputs.forEach { definitions.add(it) }
    this.outputs.forEach { definitions.add(it) }

    return definitions
  }


  private fun blank(value:String?): String {
    return if (value == null || value.trim() == "") "-" else value
  }

  private class AsciiDmnTable( val inputs: Int, val outputs: Int) {

    companion object {
      fun rowSpan(text:String,  cols: Int) = text to cols
    }
    val columns = inputs + outputs + 1
    val asciiTable = AsciiTable().apply {
      addRule()
    }


    fun addRow(vararg rowSpans: Pair<String,Int>, doubleLine: Boolean = false): AsciiDmnTable {
      require(rowSpans.map { it.second }.sum() == columns) { "columns must have size=$columns, was $rowSpans" }

      val cols = mutableListOf<String?>()

      rowSpans.forEach { (text, span) ->
        val nulls =  arrayOfNulls<String?>(span-1).toMutableList()
        cols.addAll(nulls + text)
      }

      return addRow(cols, doubleLine)
    }

    fun addRow(cols: List<String?>, doubleLine: Boolean = false) = apply {
      require(cols.size == columns) { "columns must have size=$columns, was $cols" }
      asciiTable.addRow(cols)
      asciiTable.addRule()
      if (doubleLine) {
        asciiTable.addRule()
      }
    }

    fun render() = asciiTable.apply {
      setTextAlignment(TextAlignment.CENTER)
    }.render()!!
  }
}