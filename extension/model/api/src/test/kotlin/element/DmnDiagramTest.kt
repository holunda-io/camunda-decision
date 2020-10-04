package io.holunda.decision.model.api.element

import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.booleanInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.booleanOutput
import io.holunda.decision.model.api.DmnDiagramTestFixtures
import io.holunda.decision.model.api.DmnDiagramTestFixtures.DECISION_1
import io.holunda.decision.model.api.DmnDiagramTestFixtures.DECISION_2
import io.holunda.decision.model.api.DmnDiagramTestFixtures.inputFoo
import io.holunda.decision.model.api.entry.toEntry
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class DmnDiagramTest {

  @Test
  fun `resultTable from singleTable`() {
    assertThat(DmnDiagramTestFixtures.singleTable.resultTable.decisionDefinitionKey).isEqualTo("singleTable")
  }

  @Test
  fun `required inputs from singleTable`() {
    assertThat(DmnDiagramTestFixtures.singleTable.requiredInputs).containsOnly(inputFoo)
  }

  @Test
  fun `resultTable from 3 table chain`() {
    assertThat(diagram.resultTable.decisionDefinitionKey).isEqualTo("t3")
  }

  @Test
  fun `required inputs from 3 table chain`() {
    assertThat(diagram.requiredInputs).containsExactlyInAnyOrder(
      inT1A, inT1B, inT2D, inT3F
    )
  }

  @Test
  fun `get all decisionDefinitionKeys`() {
    assertThat(DmnDiagramTestFixtures.decision2DependsOnDecision1.decisionDefinitionKeys)
      .containsExactlyInAnyOrder(DECISION_1, DECISION_2)
  }

  @Test
  fun `get required decision mapping`() {
    assertThat(DmnDiagramTestFixtures.decision2DependsOnDecision1.informationRequirements)
      .hasSize(1)
      .contains(DmnDecisionTable.InformationRequirement(DECISION_2, DECISION_1))
  }

  @Test
  fun `resource name`() {
    assertThat(DmnDiagramTestFixtures.decision2DependsOnDecision1.resourceName).isEqualTo("diagram-diagram-with-two-tables.dmn")
  }

  val inT1A = booleanInput("a")
  val inT1B = booleanInput("b")
  val outT1C = booleanOutput("c")

  val inT2C = outT1C.toInputDefinition()
  val inT2D = booleanInput("d")
  val outT2E = booleanOutput("e")

  val inT3E = outT2E.toInputDefinition()
  val inT3F = booleanInput("f")
  val outT3G = booleanOutput("g")

  val diagram = DmnDiagram(
    id = DmnDiagram.idGenerator(),
    name = "3 Table chain",
    decisionTables = listOf(
      DmnDecisionTable(
        decisionDefinitionKey = "t1",
        name = "T1",
        header = DmnDecisionTable.Header(
          inputs = listOf(inT1A, inT1B), outputs = listOf(outT1C)
        ),
        rules = DmnRuleList(
          DmnRule(inputs = listOf(inT1A.toEntry("true"), inT1B.toEntry("true")), outputs = listOf(outT1C.toEntry("true")))
        )
      ),
      DmnDecisionTable(
        decisionDefinitionKey = "t3",
        name = "T3",
        header = DmnDecisionTable.Header(
          inputs = listOf(inT3E, inT3F), outputs = listOf(outT3G)
        ),
        rules = DmnRuleList(
          DmnRule(inputs = listOf(inT3E.toEntry("true"), inT3F.toEntry("true")), outputs = listOf(outT3G.toEntry("true")))
        ),
        requiredDecisions = setOf("t2")
      ),
      DmnDecisionTable(
        decisionDefinitionKey = "t2",
        name = "T2",
        header = DmnDecisionTable.Header(
          inputs = listOf(inT2C, inT2D), outputs = listOf(outT2E)
        ),
        rules = DmnRuleList(
          DmnRule(inputs = listOf(inT2C.toEntry("true"), inT2D.toEntry("true")), outputs = listOf(outT2E.toEntry("true")))
        ),
        requiredDecisions = setOf("t1")
      )

    )
  )

  @Test
  fun `get all decisionDefinitionKeys 2`() {
    assertThat(DmnDiagramTestFixtures.decision2DependsOnDecision1.decisionDefinitionKeys)
      .containsExactlyInAnyOrder(DECISION_1, DECISION_2)
  }

  @Test
  fun `get required decision mapping 2`() {
    assertThat(DmnDiagramTestFixtures.decision2DependsOnDecision1.informationRequirements)
      .hasSize(1)
      .contains(DmnDecisionTable.InformationRequirement(DECISION_2, DECISION_1))
  }

  @Test
  fun `resource name 2`() {
    assertThat(DmnDiagramTestFixtures.decision2DependsOnDecision1.resourceName).isEqualTo("diagram-diagram-with-two-tables.dmn")
  }
}
