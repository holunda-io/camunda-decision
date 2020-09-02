package io.holunda.decision.model.builder

import io.holunda.decision.model.CamundaDecisionGenerator.rule
import io.holunda.decision.model.FeelConditions.feelEqual
import io.holunda.decision.model.FeelConditions.resultValue
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.longInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.stringInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.stringOutput
import io.holunda.decision.model.api.data.DmnHitPolicy
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class DmnDecisionTableRulesBuilderTest {

  private val inFoo = stringInput("foo")
  private val inBaz = longInput("baz")
  private val outBar = stringOutput("bar")

  @Test
  fun `create table with one rule`() {
    val builder = DmnDecisionTableRulesBuilder()
      .decisionDefinitionKey("decision1")
      .name("Decision One")
      .addRule(rule()
        .condition(
          inFoo.feelEqual("abc"),
          inBaz.feelEqual(5)
        )
        .result( outBar.resultValue("xyz"))
      )

    with(builder.build()) {
      assertThat(decisionDefinitionKey).isEqualTo("decision1")
      assertThat(name).isEqualTo("Decision One")
      assertThat(versionTag).isNull()
      assertThat(hitPolicy).isEqualTo(DmnHitPolicy.FIRST)
      assertThat(rules).hasSize(1)

      assertThat(header.numInputs).isEqualTo(2)
      assertThat(header.numOutputs).isEqualTo(1)
      assertThat(header.inputs[0]).isEqualTo(inBaz)
      assertThat(header.inputs[1]).isEqualTo(inFoo)

    }

    builder.versionTag("123")
    with(builder.build()) {
      assertThat(versionTag).isEqualTo("123")
    }
  }
}
