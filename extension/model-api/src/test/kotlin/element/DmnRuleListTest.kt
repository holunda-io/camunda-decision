package io.holunda.decision.model.api.element

import io.holunda.decision.model.api.CamundaDecisionModelApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class DmnRuleListTest {
  val inputFoo = CamundaDecisionModelApi.InputDefinitions.stringInput("foo")
  val inputBar = CamundaDecisionModelApi.InputDefinitions.stringInput("bar")
  val outHello = CamundaDecisionModelApi.OutputDefinitions.stringOutput("hello")

  val d1 = DmnRule(id = "1", description = "first", inputs = listOf(InputEntry(inputFoo, "\"foo\"")), outputs = listOf(OutputEntry(outHello, "\"res1\"")))
  val d2 = DmnRule(id = "2", description = "second", inputs = listOf(InputEntry(inputBar, "\"bar\"")), outputs = listOf(OutputEntry(outHello, "\"res2\"")))


  @Test
  fun `distinctInputs for single rule`() {
    val rules = DmnRuleList(d1)

    assertThat(rules.distinctInputs).containsOnly(inputFoo)
    assertThat(rules.distinctOutputs).containsOnly(outHello)
  }
}

