package io.holunda.decision.model.condition.jbool

import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.stringInput
import io.holunda.decision.model.condition.FeelComparisonCondition
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class FeelInputVariableTest {
  private val inFoo = stringInput("foo")
  private val variable = FeelInputVariable(inFoo, FeelComparisonCondition("xxx"))

  @Test
  fun `feelVariable toString`() {
    assertThat(variable.toString()).isEqualTo("'foo{\"xxx\"}'")
  }

  @Test
  fun `not feelVariable toString`() {
    assertThat(variable.negate().jbool().toLexicographicString()).isEqualTo("!'foo{\"xxx\"}'")
  }

  @Test
  fun `not feelVariable dnf`() {
    assertThat(variable.negate().jboolDnf().toLexicographicString()).isEqualTo("!'foo{\"xxx\"}'")
  }


}
