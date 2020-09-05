package io.holunda.decision.model.builder

import io.holunda.decision.model.FeelConditions.feelComparison
import io.holunda.decision.model.FeelConditions.feelEqual
import io.holunda.decision.model.FeelConditions.resultTrue
import io.holunda.decision.model.FeelConditions.resultValue
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.doubleInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.integerInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.longInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.stringInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.booleanOutput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.stringOutput
import io.holunda.decision.model.api.element.DmnDecisionTable
import io.holunda.decision.model.api.element.DmnRule
import io.holunda.decision.model.api.entry.toEntry
import io.holunda.decision.model.condition.FeelComparisonCondition
import io.holunda.decision.model.condition.jbool.JBoolExpressionSupplier.Companion.and
import io.holunda.decision.model.condition.jbool.JBoolExpressionSupplier.Companion.or
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
  fun `rule with two input (or) and one output`() {
    val rules = DmnBusinessRuleBuilder()
      .condition(
        or(
          a.feelEqual(1),
          b.feelEqual(2L)
        )
      )
      .result(o1.resultTrue())
      .build()

    assertThat(rules).hasSize(2)
    assertThat(rules.distinctInputs).containsExactly(a, b)
    assertThat(rules.distinctOutputs).containsExactly(o1)
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
    assertThat(rules.distinctInputs).containsExactlyInAnyOrder(a, b, c)
  }


  @Test
  fun `or variables`() {
    val rules = DmnBusinessRuleBuilder()
      .condition(
        or(
          a.feelComparison(5, FeelComparisonCondition.ComparisonType.Greater),
          and(
            b.feelEqual(5L),
            c.feelEqual(6.1)
          )
        )
      )
      .result(o1.resultValue(false)
      )
      .build()

    assertThat(rules.size).isEqualTo(2)
    assertThat(rules.map { it.inputs.map { it.definition } }).containsExactlyInAnyOrder(
      listOf(b, c),
      listOf(a)
    )
  }

  @Test
  fun `fillRule add empty inputs`() {
    val rule = DmnRule(inputs = listOf(a.toEntry("1")), outputs = listOf(o2.resultTrue()))

    val header = DmnDecisionTable.Header(
      inputs = listOf(a, b, c),
      outputs = listOf(o1, o2)
    )

    val filled = rule.fillRule(header)

    assertThat(filled.inputDefinitions).isEqualTo(header.inputs)
    assertThat(filled.outputDefinitions).isEqualTo(header.outputs)
  }
}
