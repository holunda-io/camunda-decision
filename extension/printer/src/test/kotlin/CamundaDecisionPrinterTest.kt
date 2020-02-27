package io.holunda.decision.printer

import org.camunda.bpm.model.dmn.Dmn
import org.junit.Test

class CamundaDecisionPrinterTest {

  val model = Dmn.readModelFromStream(CamundaDecisionPrinterTest::class.java.getResourceAsStream("/dummy_dmn.dmn"))

  @Test
  fun render() {
    println(CamundaDecisionPrinter.render(model))
  }
}
