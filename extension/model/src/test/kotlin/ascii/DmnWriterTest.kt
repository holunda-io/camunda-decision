package io.holunda.decision.model.ascii

import io.holunda.decision.lib.test.CamundaDecisionTestLib
import io.holunda.decision.lib.test.CamundaDecisionTestLib.readText
import io.holunda.decision.model.CamundaDecisionModel
import io.holunda.decision.model.ascii.DmnWriter
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test

@Ignore
class DmnWriterTest {

  private val dmnExampleSingleTable = CamundaDecisionTestLib.readModel("example_single_table.dmn")

  @Test
  fun `rendered table equals resource`() {

//    val decisionTable = CamundaDecisionModel.readDecisionTable(dmnExampleSingleTable)

    val ascii = "" // TODO DmnWriter.render(decisionTable)

    println(ascii)

    assertThat(ascii).isEqualTo(readText("example_single_table.txt"))
  }

  @Test
  fun `generate table`() {
// TODO
//    val decisionTable = CamundaDecisionModel.readDecisionTable(dmnExampleSingleTable)
//    val orig = DmnWriter.render(decisionTable)
//
//    val dmn = CamundaDecisionModel.createModelInstance(decisionTable)
//
//    assertThat(DmnWriter.render(CamundaDecisionModel.readDecisionTable(dmn))).isEqualTo(orig)
  }
}
