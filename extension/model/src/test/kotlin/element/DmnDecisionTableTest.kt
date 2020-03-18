package io.holunda.decision.model.element

import io.holunda.decision.lib.test.CamundaDecisionTestLib
import io.holunda.decision.model.CamundaDecisionModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class DmnDecisionTableTest {

  private val dmnCollectSum = CamundaDecisionTestLib.readModel("collect_sum.dmn")


  @Test
  fun `read dmnHitPolicy - collect sum`() {
    val table = CamundaDecisionModel.readDecisionTable(dmnCollectSum)

    assertThat(table.hitPolicy).isEqualTo(DmnHitPolicy.COLLECT_SUM)
  }
}
