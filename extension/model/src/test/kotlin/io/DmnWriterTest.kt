package io.holunda.decision.model.io

import io.holunda.decision.lib.test.CamundaDecisionTestLib
import org.junit.Test


class DmnWriterTest {


  @Test
  fun name() {

    val decisionTable = DmnReader.readDecisionTable(CamundaDecisionTestLib.readModel("example_single_table.dmn"))

    val ascii = DmnWriter.render(decisionTable)

    println(ascii)

  }
}
