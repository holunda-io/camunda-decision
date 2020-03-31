package io.holunda.decision.generator.builder

import io.holunda.decision.model.element.*
import io.holunda.decision.model.element.InputDefinitionFactory.stringInput
import io.holunda.decision.model.element.OutputDefinitionFactory.stringOutput
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class DmnRulesBuilderTest {

  @Test
  fun `create simple and rule`() {
    val rules = DmnRulesBuilder()
      .description("my first rule")
      .outputs(OutputEntry(stringOutput("bar"),"\"hello\""))
      .condition(InputEntry(stringInput("foo"), "\"xxx\""))
      .build()

    assertThat(rules).hasSize(1)
    assertThat(rules.distinctInputs).containsExactly(stringInput("foo"))
    assertThat(rules.distinctOutputs).containsExactly(stringOutput("bar"))
  }
}

