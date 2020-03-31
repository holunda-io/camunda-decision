package io.holunda.decision.generator.builder

import io.holunda.decision.model.element.DmnHitPolicy
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class DmnDecisionTableRulesBuilderTest {

  @Test
  fun `create table with one rule`() {
    val table = DmnDecisionTableRulesBuilder()
      .decisionDefinitionKey("decision1")
      .build()

    with(table) {
      assertThat(decisionDefinitionKey).isEqualTo("decision1")
      assertThat(name).isEqualTo("decision1")
      assertThat(versionTag).isNull()
      assertThat(hitPolicy).isEqualTo(DmnHitPolicy.FIRST)
      assertThat(rules).isEmpty()
    }
  }
}
