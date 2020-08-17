package io.holunda.decision.generator.builder

import io.holunda.decision.generator.CamundaDecisionGenerator.rule
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.stringInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.stringOutput
import io.holunda.decision.model.api.data.DmnHitPolicy
import io.holunda.decision.model.api.element.InputEntry
import io.holunda.decision.model.api.element.OutputEntry
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class DmnDecisionTableRulesBuilderTest {

  @Test
  fun `create table with one rule`() {
    val builder = DmnDecisionTableRulesBuilder()
      .decisionDefinitionKey("decision1")
      .addRule(rule()
        .condition(InputEntry(definition = stringInput("foo"), expression = "\"abc\""))
        .outputs(OutputEntry(definition = stringOutput("bar"), expression = "\"xyz\""))
      )

    with(builder.build()) {
      assertThat(decisionDefinitionKey).isEqualTo("decision1")
      assertThat(name).isEqualTo("decision1")
      assertThat(versionTag).isNull()
      assertThat(hitPolicy).isEqualTo(DmnHitPolicy.FIRST)
      assertThat(rules).hasSize(1)
    }

    builder.versionTag("123")
    with(builder.build()) {
      assertThat(versionTag).isEqualTo("123")
    }
  }
}
