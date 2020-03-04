package io.holunda.decision.model.element

import io.holunda.decision.model.CamundaDecisonModelFixtures.DECISION_1
import io.holunda.decision.model.CamundaDecisonModelFixtures.DECISION_2
import io.holunda.decision.model.CamundaDecisonModelFixtures.DmnDiagramFixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DmnDiagramTest {

  @Test
  fun `get all decisionDefinitionKeys`() {
    assertThat(DmnDiagramFixtures.decision2DependsOnDecision1.decisionDefinitionKeys)
      .containsExactlyInAnyOrder(DECISION_1, DECISION_2)
  }

  @Test
  fun `get required decision mapping`() {
    assertThat(DmnDiagramFixtures.decision2DependsOnDecision1.requiredDecisions)
      .hasSize(1)
      .containsEntry(DECISION_2, setOf(DECISION_1))
  }
}
