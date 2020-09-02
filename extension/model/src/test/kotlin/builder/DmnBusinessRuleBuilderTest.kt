package io.holunda.decision.model.builder

import io.holunda.decision.model.FeelExpressions.feelEqual
import io.holunda.decision.model.FeelExpressions.not
import io.holunda.decision.model.FeelExpressions.resultValue
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.doubleInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.integerInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.longInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.stringInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.booleanOutput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.stringOutput
import io.holunda.decision.model.expression.jbool.JBoolExpressionSupplier
import io.holunda.decision.model.expression.jbool.JBoolExpressionSupplier.Companion.and
import io.holunda.decision.model.expression.jbool.JBoolExpressionSupplier.Companion.or
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class DmnBusinessRuleBuilderTest {

  private val a = integerInput("a")
  private val b = longInput("b")
  private val c = doubleInput("c")

  private val o1 = booleanOutput("o1")
  private val o2 = booleanOutput("o2")

  @Test
  fun `simple rule with one input and one output`() {
    val rules = DmnBusinessRuleBuilder()
      .description("my first rule")
      .condition(stringInput("foo").feelEqual("xxx"))
      .result(stringOutput("bar").resultValue("hello"))
      .build()

    assertThat(rules).hasSize(1)
    assertThat(rules.distinctInputs).containsExactly(stringInput("foo"))
    assertThat(rules.distinctOutputs).containsExactly(stringOutput("bar"))
  }

  @Test
  fun `create rule with jbool and`() {
    val rules = DmnBusinessRuleBuilder()
      .description("only a")
      .condition(
        a.feelEqual(1),
        b.feelEqual(2L),
        c.feelEqual(3.0)
      )
      .result(o1.resultValue(true))
      .build()

    assertThat(rules).hasSize(1)
    assertThat(rules.distinctInputs).containsExactlyInAnyOrder(a,b,c)
  }

  @Test
  fun `or variables`() {
    val rules = DmnBusinessRuleBuilder()
      .condition(
        or(
          a.feelEqual(4),
          and(
            b.feelEqual(5L),
            c.feelEqual(6.1)
          )
        )
      )
      .result(o1.resultValue(false))
      .build()
  }
}
