package io.holunda.decision.generator

import io.holunda.decision.generator.CamundaDecisionGenerator.ModelExtension.convertToString
import io.holunda.decision.model.DmnRule
import io.holunda.decision.model.InputDefinition
import io.holunda.decision.model.InputDefinition.Companion.stringInput
import io.holunda.decision.model.OutputDefinition
import io.holunda.decision.model.OutputDefinition.Companion.stringOutput
import org.camunda.bpm.model.dmn.HitPolicy
import org.junit.Test

class DmnModelBuilderTest {

  @Test
  fun builder() {
    val inputFoo = stringInput("foo", "Foo (input)")
    val inputBar = stringInput("bar", "Bar (input)")
    val outputResult = stringOutput("result", "Result (output)")

    val builder = CamundaDecisionGenerator.model("DRD Model")
      .decisionTable("first_decision")
      .name("First Decision")
      .versionTag("1.0")
      .hitPolicy(HitPolicy.FIRST)
      .rule(DmnRule().addInput(inputFoo, "< 10").addOutput(outputResult, "hello"))
      .rule(DmnRule().addInput(inputBar, "> 10").addOutput(outputResult, "world"))
      .done()



    println(builder)
    println(builder.build().convertToString())
  }
}

