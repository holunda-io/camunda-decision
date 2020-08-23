package io.holunda.decision.model.ascii

import io.holunda.decision.lib.test.CamundaDecisionTestLib
import io.holunda.decision.lib.test.CamundaDecisionTestLib.readText
import io.holunda.decision.model.CamundaDecisionModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DmnWriterTest {

  private val dmnExampleSingleTable = CamundaDecisionTestLib.DmnTestResource.SINGLE_TABLE.load()

  @Test
  fun `rendered table equals resource`() {

    val decisionTable = CamundaDecisionModel.readDiagram(dmnExampleSingleTable)

    assertThat(decisionTable.findDecistionTable("example")?.versionTag).isEqualTo("666")

    val ascii = DmnWriter.render(decisionTable)

    println(ascii)

    assertThat(ascii).isEqualTo(readText("example_single_table.txt"))
  }

}
