package io.holunda.decision.model.api

import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.integerInput
import io.holunda.decision.model.api.data.DmnHitPolicy
import io.holunda.decision.model.api.element.DmnDecisionTable
import io.holunda.decision.model.api.element.DmnDiagram
import io.holunda.decision.model.api.element.DmnRule
import io.holunda.decision.model.api.element.DmnRuleList
import io.holunda.decision.model.api.entry.InputEntry
import io.holunda.decision.model.api.entry.OutputEntry
import io.holunda.decision.model.api.entry.toEntry

object DmnDiagramTestFixtures {
  const val DECISION_1 = "decision1"
  const val DECISION_2 = "decision2"

  val inputFoo = integerInput("foo", "Foo")
  val inputBar = integerInput("bar", "Bar")

  val outputBar = inputBar.toOutputDefinition().copy(label = "Bar Result")
  val outputResult = CamundaDecisionModelApi.OutputDefinitions.stringOutput("result", "Result")


  val singleTable = DmnDiagram(
    DmnDecisionTable(
      decisionDefinitionKey = "singleTable",
      name = "Decision ",
      versionTag = "1",
      header = DmnDecisionTable.Header(listOf(inputFoo), listOf(outputResult)),
      hitPolicy = DmnHitPolicy.FIRST,
      rules = DmnRuleList(
        DmnRule(
          id = "r5",
          description = "woohoo",
          inputs = listOf(InputEntry(inputFoo, "< 100")),
          outputs = listOf(OutputEntry(outputResult, "\"Result\""))
        )
      )
    )
  )

  val singleTable2Outputs = DmnDiagram(
    DmnDecisionTable(
      decisionDefinitionKey = "singleTable",
      name = "Decision ",
      versionTag = "1",
      header = DmnDecisionTable.Header(listOf(inputFoo), listOf(outputResult, outputBar)),
      hitPolicy = DmnHitPolicy.FIRST,
      rules = DmnRuleList(
        DmnRule(
          id = "r5",
          description = "woohoo",
          inputs = listOf(inputFoo.toEntry("< 100")),
          outputs = listOf(outputResult.toEntry("\"Result\""), outputBar.toEntry("5"))
        )
      )
    )
  )

  val decision2DependsOnDecision1 = DmnDiagram(
    id = "table2dependsOnTable1",
    name = "Diagram with two tables",
    decisionTables = listOf(
      DmnDecisionTable(
        decisionDefinitionKey = DECISION_1,
        name = "Decision 1",
        hitPolicy = DmnHitPolicy.FIRST,
        versionTag = "1",
        header = DmnDecisionTable.Header(
          listOf(inputFoo), listOf(outputBar)
        ),
        rules = DmnRuleList(
          DmnRule(
            id = "r1",
            description = "amazing",
            inputs = listOf(inputFoo.toEntry(">10")),
            outputs = listOf(outputBar.toEntry("100"))
          )
        )
      ),
      DmnDecisionTable(
        decisionDefinitionKey = DECISION_2,
        name = "Decision 2",
        hitPolicy = DmnHitPolicy.FIRST,
        versionTag = "1",
        header = DmnDecisionTable.Header(
          listOf(inputBar), listOf(outputResult)
        ),
        rules = DmnRuleList(
          DmnRule(
            id = "r5",
            description = "based on decision1",
            inputs = listOf(inputBar.toEntry(">99")),
            outputs = listOf(outputResult.toEntry("100"))
          )
        ),
        requiredDecisions = setOf(DECISION_1)
      )
    )
  )
}
