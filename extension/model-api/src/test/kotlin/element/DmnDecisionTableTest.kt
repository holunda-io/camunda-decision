package element

import io.holunda.decision.model.api.DmnDiagramTestFixtures
import io.holunda.decision.model.api.data.ResultType
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class DmnDecisionTableTest {

  @Test
  fun `hitpolicy UNIQUE, one output`() {
    assertThat(DmnDiagramTestFixtures.singleTable.resultTable.resultType).isEqualTo(ResultType.SingleResult)
  }

  @Test
  fun `hitpolicy UNIQUE, two outputs`() {
    assertThat(DmnDiagramTestFixtures.singleTable2Outputs.resultTable.resultType).isEqualTo(ResultType.TupleResult)
  }
}
