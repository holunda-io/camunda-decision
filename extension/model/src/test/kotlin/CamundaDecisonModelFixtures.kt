package io.holunda.decision.model

import io.holunda.decision.model.CamundaDecisionModel.InputDefinitions.integerInput
import io.holunda.decision.model.CamundaDecisionModel.OutputDefinitions.integerOutput
import io.holunda.decision.model.CamundaDecisionModel.OutputDefinitions.stringOutput
import io.holunda.decision.model.element.*
import io.holunda.decision.model.element.row.DmnRule
import io.holunda.decision.model.element.row.InputEntry
import io.holunda.decision.model.element.row.OutputEntry


object CamundaDecisonModelFixtures {

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
          rules = DmnRules(
            DmnRule(description = "amazing", inputs = listOf(InputEntry<Int>(inputFoo, ">10")), outputs = listOf(OutputEntry<Int>(outputBar, "100")))
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
          rules = DmnRules(
            DmnRule(description = "based on decision1", inputs = listOf(InputEntry(inputBar, ">99")), outputs = listOf(OutputEntry(outputResult, "100")))
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
        rules = DmnRules(
          DmnRule(
            description = "woohoo",
            inputs = listOf(InputEntry(inputFoo, "< 100")),
            outputs = listOf(OutputEntry(outputResult, "\"Result\""))
          )
        )
      )
    )
  }
}
