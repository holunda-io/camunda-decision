package io.holunda.decision.generator

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.decision.generator.CamundaDecisionGenerator.rule
import io.holunda.decision.generator.CamundaDecisionGenerator.table
import io.holunda.decision.generator.builder.DmnDecisionTableReferenceBuilder
import io.holunda.decision.generator.builder.manageDmnDeployment
import io.holunda.decision.lib.test.CamundaDecisionTestLib
import io.holunda.decision.model.CamundaDecisionModel.InputDefinitions.booleanInput
import io.holunda.decision.model.CamundaDecisionModel.InputDefinitions.integerInput
import io.holunda.decision.model.CamundaDecisionModel.OutputDefinitions.integerOutput
import io.holunda.decision.model.CamundaDecisionModel.OutputDefinitions.stringOutput
import io.holunda.decision.model.converter.DmnDiagramConverter
import io.holunda.decision.model.element.DmnHitPolicy
import io.holunda.decision.model.element.InputEntry
import io.holunda.decision.model.element.OutputEntry
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

    camunda.manageDmnDeployment(diagram)

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


  @Test
  fun `sum results`() {
    val input = integerInput("input")
    val output = integerOutput("output")

    camunda.manageDmnDeployment(CamundaDecisionGenerator.diagram()
      .addDecisionTable(
        table().decisionDefinitionKey("foo")
          .versionTag("1")
          .hitPolicy(DmnHitPolicy.COLLECT_SUM)
          .addRule(
            rule()
              .condition(InputEntry(input, "> 1"))
              .outputs(OutputEntry(output, "2"))
          )
          .addRule(
            rule()
              .condition(InputEntry(input, "> 10"))
              .outputs(OutputEntry(output, "3"))
          )
      )
      .build())

    // 1 < input < 11 should sum only "2"
    val sum5 = camunda.decisionService.evaluateDecisionByKey("foo")
      .variables(CamundaBpmData.builder()
        .set(CamundaBpmData.intVariable(input.key), 5)
        .build())
      .evaluate().singleResult.getEntry<Int>(output.key)
    assertThat(sum5).isEqualTo(2)

    //  input > 10 should sum "5"
    val sum11 = camunda.decisionService.evaluateDecisionByKey("foo")
      .variables(CamundaBpmData.builder()
        .set(CamundaBpmData.intVariable(input.key), 11)
        .build())
      .evaluate().singleResult.getEntry<Int>(output.key)
    assertThat(sum11).isEqualTo(5)

  }
}
