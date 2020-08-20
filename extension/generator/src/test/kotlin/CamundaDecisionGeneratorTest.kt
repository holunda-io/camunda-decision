package io.holunda.decision.generator

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.decision.generator.CamundaDecisionGenerator.diagram
import io.holunda.decision.generator.CamundaDecisionGenerator.rule
import io.holunda.decision.generator.CamundaDecisionGenerator.table
import io.holunda.decision.generator.builder.DmnDecisionTableReferenceBuilder
import io.holunda.decision.lib.test.CamundaDecisionTestLib
import io.holunda.decision.lib.test.CamundaDecisionTestLib.manageDmnDeployment
import io.holunda.decision.model.CamundaDecisionModel
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.booleanInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.integerInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.longInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.booleanOutput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.integerOutput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.longOutput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.stringOutput
import io.holunda.decision.model.api.data.DmnHitPolicy
import io.holunda.decision.model.api.element.toEntry
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.variable.Variables
import org.junit.Rule
import org.junit.Test

internal class CamundaDecisionGeneratorTest {


  private val inFoo = longInput("foo", "Foo Number")
  private val outBar = longOutput("bar", "Bar Number")

  private val inBar = outBar.toInputDefinition()
  private val inActive = booleanInput("active", "is Active?")
  private val outResult = booleanOutput("result", "final result")

  @get:Rule
  val camunda = CamundaDecisionTestLib.camunda()

  val dmnEngine by lazy { camunda.processEngineConfiguration.dmnEngine }
  val decisionService by lazy { camunda.decisionService }


  @Test
  fun `deploy dmn with single decision table`() {
    val inputActive = booleanInput("active", label = "Is active?")
    val outputValue = stringOutput(label = "The amazing result", key = "value")

    val diagramBuilder = CamundaDecisionGenerator.diagram()
      .id("foo")
      .name("My Diagram")
      .addDecisionTable(table("theKey")
        .name("A generated table")
        .versionTag("1")
        .addRule(rule()
          .description("test")
          .condition(input = inActive.toEntry("false"))
          .outputs(outputValue.toEntry("\"FOO\""))
        )
      )

    val diagram = diagramBuilder.build()

    // print out ascii - table
    println(CamundaDecisionModel.render(diagram))


    val dmnXml = CamundaDecisionModel.createDmnXml(diagram)
    println(dmnXml)

    camunda.manageDmnDeployment {
      addString(diagram.resourceName, CamundaDecisionModel.createDmnXml(diagram))
    }

    val evalResult = camunda.decisionService.evaluateDecisionByKey("theKey")
      .variables(
        CamundaBpmData.builder()
          .set(CamundaBpmData.booleanVariable(inputActive.key), false)
          .build()
      )
      .evaluate()

    val resultValue = evalResult  .firstResult
      .getEntry<String>("value")

    assertThat(resultValue).isEqualTo("FOO")
  }

  @Test
  fun `build diagram from existing dmn`() {
     val exampleSingleDmn = CamundaDecisionTestLib.readModel("example_single_table.dmn")

    val diagram = CamundaDecisionGenerator.diagram()
      .addDecisionTable(DmnDecisionTableReferenceBuilder()
        .reference(exampleSingleDmn)
        .decisionDefinitionKey("fooTable") // change name from "example" to "fooTable"
        .name("Changed Name") // change name from "DMN Example" to "Changed Name"
        .versionTag("667") // increase version
      )
      .build()

    println(CamundaDecisionModel.render(diagram))

    assertThat(diagram.name).isEqualTo("DRD")
    val table = diagram.decisionTables.first()

    assertThat(table.decisionDefinitionKey).isEqualTo("fooTable")
    assertThat(table.name).isEqualTo("Changed Name")
    assertThat(table.versionTag).isEqualTo("667")

  }

  @Test
  fun `sum results`() {
    val input = integerInput("input", "Ich bin der input")
    val output = integerOutput("output")

    camunda.manageDmnDeployment {
      val diagram = CamundaDecisionGenerator.diagram()
        .addDecisionTable(
          table("foo")
            .versionTag("1")
            .hitPolicy(DmnHitPolicy.COLLECT_SUM)
            .addRule(
              rule()
                .condition(input.toEntry("> 1"))
                .outputs(output.toEntry( "2"))
            )
            .addRule(
              rule(input.toEntry("> 10"))
                .outputs(output.toEntry("3"))
            )
        )
        .build()
      addString(diagram.resourceName, CamundaDecisionModel.createDmnXml(diagram))
    }
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

  val diagramFooBar = diagram("Foo Bar Diagram")
    // first table: inputs: foo, outputs: bar
    .addDecisionTable(
      table("decision1")
        .addRule(
          rule(inFoo.toEntry("> 20"))
            .outputs(outBar.toEntry("200"))
        )
        .addRule(
          rule(inFoo.toEntry("> 5"))
            .outputs(outBar.toEntry("100"))
        )

    )
    // second table: inputs: bar (from decision 1), active, outputs: result
    .addDecisionTable(
      table("decision2")
        .requiredDecision("decision1")
        .addRule(
          rule(inBar.toEntry("100"))
            .and(inActive.toEntry("true"))
            .outputs(outResult.toEntry("false"))
        )
        .addRule(
          rule(inBar.toEntry("100"))
            .and(inActive.toEntry("false"))
            .outputs(outResult.toEntry("true"))
        )
        .addRule(
          rule(inBar.toEntry("200"))
            .and(inActive.toEntry("true"))
            .outputs(outResult.toEntry("true"))
        )
        .addRule(
          rule(inBar.toEntry("200"))
            .and(inActive.toEntry("false"))
            .outputs(outResult.toEntry("false"))
        )
    ).build().apply { println(CamundaDecisionModel.render(this)) }

  @Test
  fun `correct order of inputEntries`() {
    assertThat(diagramFooBar.findDecistionTable("decision1")?.inputEntry(0,0)).isEqualTo("> 20");
    assertThat(diagramFooBar.findDecistionTable("decision2")?.inputEntry(0,0)).isEqualTo("100");


  }


  @Test
  fun `graph with two tables`() {


    println(CamundaDecisionModel.render(diagramFooBar))

    println(CamundaDecisionModel.createDmnXml(diagramFooBar))

    camunda.manageDmnDeployment {
      addString(diagramFooBar.resourceName, CamundaDecisionModel.createDmnXml(diagramFooBar))

    }

    assertThat(decisionService.evaluateDecisionTableByKey("decision1",
      Variables.putValue(inFoo.key, 10L)
    ).singleResult.getEntry<Long>(outBar.key)).isEqualTo(100L)


    assertThat(decisionService.evaluateDecisionTableByKey("decision2",
      Variables.putValue(inFoo.key, 10L)
        .putValue(inActive.key, false)
    ).singleResult.getEntry<Boolean>(outResult.key)).isTrue()

    assertThat(decisionService.evaluateDecisionTableByKey("decision2",
      Variables.putValue(inFoo.key, 30L)
        .putValue(inActive.key, false))
      .singleResult.getEntry<Boolean>(outResult.key)).isFalse()
  }


  @Test
  fun `layout for graphs`() {
    val diagramFooBar = diagram("Foo Bar Diagram")
      // first table: inputs: foo, outputs: bar
      .addDecisionTable(
        table("decision1")
          .addRule(
            rule(inFoo.toEntry("> 20"))
              .outputs(outBar.toEntry("200"))
          )
          .addRule(
            rule(inFoo.toEntry("> 5"))
              .outputs(outBar.toEntry("100"))
          )

      )
      // second table: inputs: bar (from decision 1), active, outputs: result
      .addDecisionTable(
        table("decision2")
          .requiredDecision("decision1")
          .addRule(
            rule(inBar.toEntry("100"))
              .and(inActive.toEntry("true"))
              .outputs(outResult.toEntry("false"))
          )
          .addRule(
            rule(inBar.toEntry("100"))
              .and(inActive.toEntry("false"))
              .outputs(outResult.toEntry("true"))
          )
          .addRule(
            rule(inBar.toEntry("200"))
              .and(inActive.toEntry("true"))
              .outputs(outResult.toEntry("true"))
          )
          .addRule(
            rule(inBar.toEntry("200"))
              .and(inActive.toEntry("false"))
              .outputs(outResult.toEntry("false"))
          )
      )

    //println(DmnDiagramConverterBean.toModelInstance(diagramFooBar.build()).toXml())

  }


}
