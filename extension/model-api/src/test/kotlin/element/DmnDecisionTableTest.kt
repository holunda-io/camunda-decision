package io.holunda.decision.model.api.element

import io.holunda.decision.model.api.DmnDiagramTestFixtures
import io.holunda.decision.model.api.data.DmnHitPolicy
import io.holunda.decision.model.api.data.ResultType
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test

internal class DmnDecisionTableTest {

  @Test
  fun `hitpolicy UNIQUE, one output`() {
    assertThat(DmnDiagramTestFixtures.singleTable.resultTable.resultType).isEqualTo(ResultType.SINGLE)
  }

  @Test
  fun `hitpolicy UNIQUE, two outputs`() {
    assertThat(DmnDiagramTestFixtures.singleTable2Outputs.resultTable.resultType).isEqualTo(ResultType.TUPLE)
  }

  @Test
  @Ignore
  fun `read dmnHitPolicy - collect sum`() {
    val table: DmnDecisionTable? = null // TODO CamundaDecisionModel.readDecisionTable(dmnCollectSum)

    assertThat(table!!.hitPolicy).isEqualTo(DmnHitPolicy.COLLECT_SUM)
  }
}
