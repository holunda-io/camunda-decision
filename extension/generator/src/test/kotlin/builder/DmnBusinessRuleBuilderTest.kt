package io.holunda.decision.generator.builder

import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.stringInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.stringOutput
import io.holunda.decision.model.api.element.InputEntry
import io.holunda.decision.model.api.element.OutputEntry
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class DmnBusinessRuleBuilderTest {

  @Test
  fun `create simple and rule`() {
    val rules = DmnBusinessRuleBuilder()
      .description("my first rule")
      .outputs(OutputEntry(stringOutput("bar"), "\"hello\""))
      .condition(InputEntry(stringInput("foo"), "\"xxx\""))
      .build()

    assertThat(rules).hasSize(1)
    assertThat(rules.distinctInputs).containsExactly(stringInput("foo"))
    assertThat(rules.distinctOutputs).containsExactly(stringOutput("bar"))
  }
}

