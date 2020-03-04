package io.holunda.decision.model.ext

import io.holunda.decision.lib.test.CamundaDecisionTestLib
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.model.dmn.instance.Definitions
import org.junit.Test

class CamundaDecisionModelExtensionTest {

  private val dmn = CamundaDecisionTestLib.readModel("example_single_table.dmn")

  @Test
  fun `Definitions#findDecisionByKey`() {
    val definitions = dmn.getModelElementByType(Definitions::class)

    assertThat(definitions.findDecisionByKey("decisionTable_1")).isNull()
    assertThat(definitions.findDecisionByKey("example")).isNotNull
    assertThat(definitions.findDecisionByKey("example")?.getDecisionTable()?.id).isEqualTo("decisionTable_1")
  }
}
