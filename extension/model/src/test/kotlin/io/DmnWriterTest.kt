package io.holunda.decision.model.io

import io.holunda.decision.lib.test.CamundaDecisionTestLib
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.model.dmn.Dmn
import org.junit.Test


class DmnWriterTest {


  @Test
  fun name() {

    val decisionTable = DmnReader.readDecisionTable(CamundaDecisionTestLib.readModel("example_single_table.dmn"))

    val ascii = DmnWriter.render(decisionTable)

    println(ascii)

  }

  @Test
  fun `generate table`() {

    val decisionTable = DmnReader.readDecisionTable(CamundaDecisionTestLib.readModel("example_single_table.dmn"))
    val orig = DmnWriter.render(decisionTable)

    val dmn = DmnWriter.createDmnModelInstance(decisionTable)

 //   println(Dmn.convertToString(dmn))

    assertThat(DmnWriter.render(DmnReader.readDecisionTable(dmn))).isEqualTo(orig)
  }
}
