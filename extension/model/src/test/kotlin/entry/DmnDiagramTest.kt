package io.holunda.decision.model.element

import io.holunda.decision.model.CamundaDecisonModelFixtures.DECISION_1
import io.holunda.decision.model.CamundaDecisonModelFixtures.DECISION_2
import io.holunda.decision.model.CamundaDecisonModelFixtures.DmnDiagramFixtures
import io.holunda.decision.model.api.element.DmnDecisionTable
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
    assertThat(DmnDiagramFixtures.decision2DependsOnDecision1.informationRequirements)
      .hasSize(1)
      .contains(DmnDecisionTable.InformationRequirement(DECISION_2, DECISION_1))
  }

  @Test
  fun `resource name`() {
    assertThat(DmnDiagramFixtures.decision2DependsOnDecision1.resourceName).isEqualTo("diagram-diagram-with-two-tables.dmn")
  }
}
