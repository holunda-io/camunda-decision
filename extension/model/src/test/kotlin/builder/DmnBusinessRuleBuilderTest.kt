package io.holunda.decision.model.builder

import io.holunda.decision.model.FeelExpressions.exprEquals
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.stringInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.stringOutput
import io.holunda.decision.model.api.element.toEntry
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class DmnBusinessRuleBuilderTest {

  @Test
  fun `create simple and rule`() {
    val rules = DmnBusinessRuleBuilder()
      .description("my first rule")
      .outputs(stringOutput("bar").toEntry("\"hello\""))
      .condition(stringInput("foo").exprEquals("xxx"))
      .build()

    assertThat(rules).hasSize(1)
    assertThat(rules.distinctInputs).containsExactly(stringInput("foo"))
    assertThat(rules.distinctOutputs).containsExactly(stringOutput("bar"))
  }
}

