package io.holunda.decision.model.io

import io.holunda.decision.lib.test.CamundaDecisionTestLib
import io.holunda.decision.model.CamundaDecisionModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DmnWriterTest {

  @Test
  fun name() {

    val decisionTable = CamundaDecisionModel.readDecisionTable(CamundaDecisionTestLib.readModel("example_single_table.dmn"))

    val ascii = DmnWriter.render(decisionTable)

    println(ascii)
  }

  @Test
  fun `generate table`() {

    val decisionTable = CamundaDecisionModel.readDecisionTable(CamundaDecisionTestLib.readModel("example_single_table.dmn"))
    val orig = DmnWriter.render(decisionTable)

    val dmn = CamundaDecisionModel.createModelInstance(decisionTable)

    assertThat(DmnWriter.render(CamundaDecisionModel.readDecisionTable(dmn))).isEqualTo(orig)
  }
}
