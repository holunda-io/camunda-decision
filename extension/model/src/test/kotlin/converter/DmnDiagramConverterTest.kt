package io.holunda.decision.model.converter


import io.holunda.decision.model.CamundaDecisonModelFixtures
import io.holunda.decision.model.element.*
import io.holunda.decision.model.element.InputDefinitionFactory.integerInput
import io.holunda.decision.model.ext.toBpmn
import io.holunda.decision.model.io.DmnReader
import io.holunda.decision.model.io.DmnWriter
import org.camunda.bpm.model.dmn.Dmn
import org.junit.Test

class DmnDiagramConverterTest {

  @Test
  fun `generate graph`() {
    val diagram = CamundaDecisonModelFixtures.DmnDiagramFixtures.decision2DependsOnDecision1

    val dmnModelInstance = DmnDiagramConverter.toModelInstance(diagram)

    println(dmnModelInstance.toBpmn())

  }

  @Test
  fun `create model from diagram`() {
    val foo = integerInput("foo", "input foo")
    val bar = OutputDefinitionFactory.integerOutput("bar", "Result")
    val dmnDiagram = DmnDiagram(
      id = "my_diagram",
      name = "Dmn Diagram",
      decisionTables = listOf(
        DmnDecisionTable(
          key = "decision1",
          name = "My Table",
          header = DmnDecisionTable.Header(listOf(foo), listOf(bar)),
          rules = DmnRules(
            listOf(
              DmnRule(description = "the only one", inputs = listOf(InputEntry(foo, "> 100")), outputs = listOf(OutputEntry(bar, "17")))
            )
          )
        )
      )
    )

    val dmnModelInstance = DmnDiagramConverter.toModelInstance(dmnDiagram)

    println(Dmn.convertToString(dmnModelInstance))
    println(DmnWriter.render(DmnReader.readDecisionTable(dmnModelInstance)))
  }


}