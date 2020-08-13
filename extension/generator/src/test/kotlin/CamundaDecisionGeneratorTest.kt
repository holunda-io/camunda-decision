package io.holunda.decision.generator

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.decision.generator.CamundaDecisionGenerator.rule
import io.holunda.decision.generator.CamundaDecisionGenerator.table
import io.holunda.decision.generator.builder.DmnDecisionTableBuilder
import io.holunda.decision.generator.builder.DmnDecisionTableReferenceBuilder
import io.holunda.decision.lib.test.CamundaDecisionTestLib
import io.holunda.decision.model.CamundaDecisionModel
import io.holunda.decision.model.converter.DmnDiagramConverter
import io.holunda.decision.model.element.*
import io.holunda.decision.model.element.InputDefinitionFactory.booleanInput
import io.holunda.decision.model.element.OutputDefinitionFactory.stringOutput
import io.holunda.decision.model.ext.toXml
import io.holunda.decision.model.io.DmnWriter
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test


internal class CamundaDecisionGeneratorTest {

  private val exampleSingleDmn = CamundaDecisionTestLib.readModel("example_single_table.dmn")

  @get:Rule
  val camunda = CamundaDecisionTestLib.camunda()

  @Test
  fun `deploy dmn with single decision table`() {
    val diagramBuilder = CamundaDecisionGenerator.diagram()
      .id("foo")
      .name("My Diagram")
      .addDecisionTable(table()
        .decisionDefinitionKey("theKey")
        .name("A generated table")
        .versionTag("1")
        .addRule(rule()
          .description("test")
          .condition(input = InputEntry(booleanInput(label = "Is active", key = "active"), "false"))
          .outputs(OutputEntry(definition = stringOutput(label = "The amazing result", key = "value"), expression = "\"FOO\""))
        )
      )

    val diagram = diagramBuilder.build()

    val dmn = DmnDiagramConverter.toModelInstance(diagram)

    println(DmnWriter.render(DmnDiagramConverter.fromModelInstance(dmn)))

    camunda.manageDeployment(camunda.repositoryService.createDeployment()
      .addModelInstance("foo.dmn", DmnDiagramConverter.toModelInstance(diagram))
      .deploy()
    )

    val resultValue = camunda.decisionService.evaluateDecisionByKey("theKey")
      .variables(
        CamundaBpmData.builder()
          .set(CamundaBpmData.booleanVariable("active"), false)
          .build()
      )
      .evaluate()
      .firstResult
      .getEntry<String>("value")

    assertThat(resultValue).isEqualTo("FOO")
  }

  @Test
  fun `build diagram from existing dmn`() {
    val diagram = CamundaDecisionGenerator.diagram()
      .addDecisionTable(DmnDecisionTableReferenceBuilder()
        .reference(exampleSingleDmn)
        .decisionDefinitionKey("fooTable") // change name from "example" to "fooTable"
        .name("Changed Name") // change name from "DMN Example" to "Changed Name"
        .versionTag("667") // increase version
      )
      .build()



    println(DmnWriter.render(diagram))
    println(DmnDiagramConverter.toModelInstance(diagram).toXml())

    assertThat(diagram.name).isEqualTo("DRD")
    val table = diagram.decisionTables.first()

    assertThat(table.decisionDefinitionKey).isEqualTo("fooTable")
    assertThat(table.name).isEqualTo("Changed Name")
    assertThat(table.versionTag).isEqualTo("667")

  }
}
