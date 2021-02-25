package io.holunda.decision.model.condition

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class FeelDisjunctionConditionTest {

  // no oneOf for boolean and date

  @Test
  fun `string oneOf`() {
    assertThat(FeelDisjunctionCondition(setOf<String>()).expression).isNull()
    assertThat(FeelDisjunctionCondition("a").expression).isEqualTo("\"a\"")
    assertThat(FeelDisjunctionCondition("a", "b").expression).isEqualTo("\"a\",\"b\"")
    assertThat(FeelDisjunctionCondition("b", "a", "b").expression).isEqualTo("\"a\",\"b\"")
  }

  @Test
  fun `integer oneOf`() {
    assertThat(FeelDisjunctionCondition(setOf<Int>()).expression).isNull()
    assertThat(FeelDisjunctionCondition(1).expression).isEqualTo("1")
    assertThat(FeelDisjunctionCondition(1, 2).expression).isEqualTo("1,2")
  }

  @Test
  fun `long oneOf`() {
    assertThat(FeelDisjunctionCondition(setOf<Long>()).expression).isNull()
    assertThat(FeelDisjunctionCondition(1L).expression).isEqualTo("1")
    assertThat(FeelDisjunctionCondition(1L, 2L).expression).isEqualTo("1,2")
  }

  @Test
  fun `double oneOf`() {
    assertThat(FeelDisjunctionCondition(setOf<Double>()).expression).isNull()
    assertThat(FeelDisjunctionCondition(1.1).expression).isEqualTo("1.1")
    assertThat(FeelDisjunctionCondition(1.1, 2.2).expression).isEqualTo("1.1,2.2")
    assertThat(FeelDisjunctionCondition(2.1, 1.2).expression).isEqualTo("1.2,2.1")
  }
}
