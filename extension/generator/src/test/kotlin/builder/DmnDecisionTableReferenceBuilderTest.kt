package io.holunda.decision.generator.builder

import io.holunda.decision.lib.test.CamundaDecisionTestLib.readModel
import io.holunda.decision.model.element.DmnHitPolicy
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class DmnDecisionTableReferenceBuilderTest {

  private val exampleSingleDmn = readModel("example_single_table.dmn")

  @Test
  fun `import single and alter values`() {
    with(DmnDecisionTableReferenceBuilder()
      .reference(exampleSingleDmn)
      .decisionDefinitionKey("otherKey")
      .name("Changed Name")
      .versionTag("667")
      .build()
    ) {
      assertThat(decisionDefinitionKey).isEqualTo("otherKey")
      assertThat(name).isEqualTo("Changed Name")
      assertThat(versionTag).isEqualTo("667")
      assertThat(hitPolicy).isEqualTo(DmnHitPolicy.UNIQUE)
      assertThat(rules).hasSize(4)
    }
  }

  @Test
  fun `just copy values from dmn - take only table`() {
    with(DmnDecisionTableReferenceBuilder().reference(exampleSingleDmn).build()) {
      assertThat(decisionDefinitionKey).isEqualTo("example")
      assertThat(name).isEqualTo("DMN Example")
      assertThat(versionTag).isEqualTo("666")
      assertThat(hitPolicy).isEqualTo(DmnHitPolicy.UNIQUE)
      assertThat(rules).hasSize(4)
    }
  }
}
