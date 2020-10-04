package io.holunda.decision.model

import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.integerInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.integerOutput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.stringOutput
import io.holunda.decision.model.api.data.DmnHitPolicy
import io.holunda.decision.model.api.element.DmnDecisionTable
import io.holunda.decision.model.api.element.DmnDiagram
import io.holunda.decision.model.api.element.DmnRule
import io.holunda.decision.model.api.element.DmnRuleList
import io.holunda.decision.model.api.entry.InputEntry
import io.holunda.decision.model.api.entry.OutputEntry


object CamundaDecisionModelFixtures {

  const val DECISION_1 = "decision1"
  const val DECISION_2 = "decision2"

  val inputFoo = integerInput("foo", "Foo")
  val inputBar = integerInput("bar", "Bar")

  val outputBar = integerOutput("bar", "Bar Result")
  val outputResult = stringOutput("result", "Result")

  object DmnDiagramFixtures {

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
              inputs = listOf(InputEntry<Int>(inputFoo, ">10")),
              outputs = listOf(OutputEntry<Int>(outputBar, "100"))
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
              inputs = listOf(InputEntry(inputBar, ">99")),
              outputs = listOf(OutputEntry(outputResult, "100"))
            )
          ),
          requiredDecisions = setOf(DECISION_1)
        )
      )
    )

    val singleTable = DmnDiagram(
      DmnDecisionTable(
        decisionDefinitionKey = DECISION_1,
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
  }
}
