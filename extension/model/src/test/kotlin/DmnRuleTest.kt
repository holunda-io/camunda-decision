package io.holunda.decision.model

import io.holunda.decision.model.element.DmnRule
import io.holunda.decision.model.element.InputDefinitionFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DmnRuleTest {

  @Test
  fun addInput() {
    var rule = DmnRule()

    assertThat(rule.inputs).isEmpty()

    rule = rule.addInput(InputDefinitionFactory.stringInput("foo"), "<10")

    assertThat(rule.inputs).isNotEmpty()
  }
}
