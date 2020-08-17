package io.holunda.decision.lib.test

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class CamundaDecisionTestLibTest {

  @Test
  fun `load dmn from resources`() {
    val xml = CamundaDecisionTestLib.DmnTestResource.FOUR_TABLES.load()

    assertThat(xml).isNotEmpty()
  }
}
