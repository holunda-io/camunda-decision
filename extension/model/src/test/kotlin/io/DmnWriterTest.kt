package io.holunda.decision.model.io

import io.holunda.decision.lib.test.CamundaDecisionTestLib
import io.holunda.decision.model.CamundaDecisionModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DmnWriterTest {

  private val dmnExampleSingleTable = CamundaDecisionTestLib.readModel("example_single_table.dmn")

  @Test
  fun name() {

    val decisionTable = CamundaDecisionModel.readDecisionTable(dmnExampleSingleTable)

    val ascii = DmnWriter.render(decisionTable)

    println(ascii)

    assertThat(ascii).isEqualTo(DmnWriterTest::class.java.getResource("/example_single_table.txt").readText().trim())
  }

  @Test
  fun `generate table`() {

    val decisionTable = CamundaDecisionModel.readDecisionTable(dmnExampleSingleTable)
    val orig = DmnWriter.render(decisionTable)

    val dmn = CamundaDecisionModel.createModelInstance(decisionTable)

    assertThat(DmnWriter.render(CamundaDecisionModel.readDecisionTable(dmn))).isEqualTo(orig)
  }
}
