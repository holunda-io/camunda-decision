package io.holunda.decision.model.io

import io.holunda.decision.lib.test.CamundaDecisionTestLib
import io.holunda.decision.lib.test.CamundaDecisionTestLib.readText
import io.holunda.decision.model.CamundaDecisionModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DmnWriterTest {

  private val dmnExampleSingleTable = CamundaDecisionTestLib.readModel("example_single_table.dmn")

  @Test
  fun `rendered table equals resource`() {

    val decisionTable = CamundaDecisionModel.readDecisionTable(dmnExampleSingleTable)

    val ascii = DmnWriter.render(decisionTable)

    println(ascii)

    assertThat(ascii).isEqualTo(readText("example_single_table.txt"))
  }

  @Test
  fun `generate table`() {

    val decisionTable = CamundaDecisionModel.readDecisionTable(dmnExampleSingleTable)
    val orig = DmnWriter.render(decisionTable)

    val dmn = CamundaDecisionModel.createModelInstance(decisionTable)

    assertThat(DmnWriter.render(CamundaDecisionModel.readDecisionTable(dmn))).isEqualTo(orig)
  }
}
