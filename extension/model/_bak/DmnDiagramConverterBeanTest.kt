package io.holunda.decision.model.converter


import io.holunda.decision.lib.test.CamundaDecisionTestLib
import io.holunda.decision.model.CamundaDecisionModel
import io.holunda.decision.model.CamundaDecisonModelFixtures
import io.holunda.decision.model.ascii.DmnWriter
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class DmnDiagramConverterBeanTest {

  @Test
  fun `generate graph`() {
    val diagram = CamundaDecisonModelFixtures.DmnDiagramFixtures.decision2DependsOnDecision1

    val dmnModelInstance = DmnDiagramConverterBean.toModelInstance(diagram)

    val d2 = DmnDiagramConverterBean.fromModelInstance(dmnModelInstance)

    println(CamundaDecisionModel.createModelInstance(d2).toXml())

    println(DmnWriter.render(d2))

  }

  @Test
  fun `create model from diagram`() {
    val foo = integerInput("foo", "input foo")
    val bar = integerOutput("bar", "Result")
    val dmnDiagram = DmnDiagram(
      id = "my_diagram",
      name = "Dmn Diagram",
      decisionTables = listOf(
        DmnDecisionTable(
          decisionDefinitionKey = "decision1",
          name = "My Table",
          header = DmnDecisionTable.Header(listOf(foo), listOf(bar)),
          rules = DmnRules(
              listOf(
                DmnRule(description = "the only one", inputs = listOf(InputEntry<Int>(foo, "> 100")), outputs = listOf(OutputEntry<Int>(bar, "17")))
              )
          )
        )
      )
    )

    val dmnModelInstance = DmnDiagramConverterBean.toModelInstance(dmnDiagram)

    println(dmnModelInstance.toXml())
    println(CamundaDecisionModel.render(CamundaDecisionModel.readDecisionTable(dmnModelInstance)))
  }



  @Test
  fun `read dummy_dmn model with single table`() {
    val dmn = CamundaDecisionTestLib.readModel("example_single_table.dmn")

    val foo = integerInput("foo", "Foo Value")
    val bar = booleanInput("bar", "Bar Value")
    val status = stringOutput("status", "VIP Status")

    val diagram = DmnDiagramConverterBean.fromModelInstance(dmn)

    val decisionTable = diagram.decisionTables.first()

    assertThat(decisionTable.decisionDefinitionKey).isEqualTo("example")
    assertThat(decisionTable.name).isEqualTo("DMN Example")
    assertThat(decisionTable.versionTag).isEqualTo("666")
    assertThat(decisionTable.hitPolicy).isEqualTo(DmnHitPolicy.UNIQUE)

    assertThat(decisionTable.header.inputs).containsExactly(foo,bar)
    assertThat(decisionTable.header.outputs).containsExactly(status)
  }

}
