package io.holunda.decision.model.element

import io.holunda.decision.model.CamundaDecisionModel.InputDefinitions.stringInput
import io.holunda.decision.model.CamundaDecisionModel.OutputDefinitions.stringOutput
import io.holunda.decision.model.element.row.DmnRule
import io.holunda.decision.model.element.row.InputEntry
import io.holunda.decision.model.element.row.OutputEntry
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class DmnRulesTest {

  val inputFoo = stringInput("foo")
  val inputBar = stringInput("bar")
  val outHello = stringOutput("hello")

  val d1 = DmnRule(id = null, description = "first", inputs = listOf(InputEntry(inputFoo, "\"foo\"")), outputs = listOf(OutputEntry(outHello, "\"res1\"")))
  val d2 = DmnRule(id = null, description = "second", inputs = listOf(InputEntry(inputBar, "\"bar\"")), outputs = listOf(OutputEntry(outHello, "\"res2\"")))


  @Test
  fun `add rules`() {

    val r1 = DmnRules(d1)
    val r2 = DmnRules(d2)

    val sum = r1 + r2

    assertThat(sum).isEqualTo(DmnRules(d1, d2))
  }

  @Test
  fun `distinctInputs for single rule`() {
    val rules = DmnRules(d1)

    assertThat(rules.distinctInputs).containsOnly(inputFoo)
    assertThat(rules.distinctOutputs).containsOnly(outHello)
  }
}

