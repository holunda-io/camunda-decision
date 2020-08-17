package io.holunda.decision.model.element

import io.holunda.decision.lib.test.CamundaDecisionTestLib.readModel
import io.holunda.decision.model.CamundaDecisionModel
import io.holunda.decision.model.api.data.DmnHitPolicy
import io.holunda.decision.model.api.element.DmnDecisionTable
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test

internal class DmnDecisionTableTest {

  private val dmnCollectSum = readModel("collect_sum.dmn")

  @Test
  @Ignore
  fun `read dmnHitPolicy - collect sum`() {
    val table :DmnDecisionTable? = null // TODO CamundaDecisionModel.readDecisionTable(dmnCollectSum)

    assertThat(table!!.hitPolicy).isEqualTo(DmnHitPolicy.COLLECT_SUM)
  }
}
