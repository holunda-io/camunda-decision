package io.holunda.decision.printer

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestWord
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance

object CamundaDecisionPrinter {

  fun render(model:DmnModelInstance) : String {
    return "";
  }

}

fun main() {
  val table = AsciiTable().apply {
    addRule()
    addRow(null,null, "INPUT", "OUTPUT")
    addRule()
    addRule()
    addRow("foo", "bar", ".", "hello")
    addRule()
    renderer.cwc = CWC_LongestWord()
  }

  println(table.render())

}
