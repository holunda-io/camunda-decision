package io.holunda.decision.generator

import io.holunda.decision.generator.model.ColumnHeader
import io.holunda.decision.generator.model.DmnRule
import org.camunda.bpm.model.dmn.HitPolicy
import org.junit.Test

class DecisionTableGeneratorTest {

  @Test
  fun name() {
    val inputFoo = ColumnHeader("foo", "Foo (input)", "string")
    val inputBar = ColumnHeader("bar", "Bar (input)", "string")
    val outputResult = ColumnHeader("result", "Result (output)", "string")

    val model = DmnModel.create("My DMN")

    val decisionModel = model.DecisionModel("myDefKey", "My Table", HitPolicy.FIRST,"1")
      .rules(listOf(
        DmnRule().apply {
          inputs[inputFoo] = "< 10"
          outputs[outputResult] =  "hello"
        },
        DmnRule().apply {
          inputs[inputBar] = "> 10"
          outputs[outputResult] =  "hello"
        }
      ))

    println(model.convertToString())
  }
}
