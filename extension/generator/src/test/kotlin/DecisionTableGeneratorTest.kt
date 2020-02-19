package io.holunda.decision.generator

import io.holunda.decision.model.DmnRule
import io.holunda.decision.model.InputDefinition.Companion.stringInput
import io.holunda.decision.model.OutputDefinition.Companion.stringOutput
import org.camunda.bpm.model.dmn.HitPolicy
import org.junit.Test

class DecisionTableGeneratorTest {

  @Test
  fun name() {
    val inputFoo = stringInput("foo", "Foo (input)")
    val inputBar = stringInput("bar", "Bar (input)")
    val outputResult = stringOutput("result", "Result (output)")

    val model = DmnModel.create("My DMN")

    val dmnRules = listOf<DmnRule>(
      DmnRule().addInput(inputFoo, "< 10").addOutput(outputResult, "hello"),
      DmnRule().addInput(inputBar, "> 10").addOutput(outputResult, "world")
    )

//
//    val decisionModel = model.DecisionModel("myDefKey", "My Table", HitPolicy.FIRST,"1")
//      .rules(listOf(
//        DmnRule().apply {
//          inputs[inputFoo] = "< 10"
//          outputs[outputResult] =  "hello"
//        },
//        DmnRule().apply {
//          inputs[inputBar] = "> 10"
//          outputs[outputResult] =  "hello"
//        }
//      ))

    println(model.convertToString())
  }
}
