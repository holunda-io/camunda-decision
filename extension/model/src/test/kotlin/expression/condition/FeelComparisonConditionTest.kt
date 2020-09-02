package io.holunda.decision.model.expression.condition

import io.holunda.decision.model.expression.condition.FeelComparisonCondition.ComparisonType
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class FeelComparisonConditionTest {

  @Test
  fun `string comparison`() {
    assertThat(FeelComparisonCondition("XXX").expression).isEqualTo("\"XXX\"")
  }

  @Test
  fun `boolean comparison`() {
    assertThat(FeelComparisonCondition(true).expression).isEqualTo("true")
    assertThat(FeelComparisonCondition(false).expression).isEqualTo("false")
  }

  @Test
  fun `date comparison`() {
    val date = FeelCondition.DATE_FORMAT.parse("2020-09-01T21:29:11")
    assertThat(FeelComparisonCondition(date).expression).isEqualTo("date and time(\"2020-09-01T21:29:11\")")
    assertThat(FeelComparisonCondition(date, ComparisonType.Greater).expression).isEqualTo("> date and time(\"2020-09-01T21:29:11\")")
    assertThat(FeelComparisonCondition(date, ComparisonType.Less).expression).isEqualTo("< date and time(\"2020-09-01T21:29:11\")")
  }

  @Test
  fun `long comparison`() {
    assertThat(FeelComparisonCondition(10L, ComparisonType.Equal).expression).isEqualTo("10")
    assertThat(FeelComparisonCondition(10L, ComparisonType.Greater).expression).isEqualTo("> 10")
    assertThat(FeelComparisonCondition(10L, ComparisonType.GreaterOrEqual).expression).isEqualTo(">= 10")
    assertThat(FeelComparisonCondition(10L, ComparisonType.Less).expression).isEqualTo("< 10")
    assertThat(FeelComparisonCondition(10L, ComparisonType.LessOrEqual).expression).isEqualTo("<= 10")
  }

  @Test
  fun `integer comparison`() {
    assertThat(FeelComparisonCondition(20).expression).isEqualTo("20")
    assertThat(FeelComparisonCondition(20, ComparisonType.Equal).expression).isEqualTo("20")
    assertThat(FeelComparisonCondition(20, ComparisonType.Greater).expression).isEqualTo("> 20")
    assertThat(FeelComparisonCondition(20, ComparisonType.GreaterOrEqual).expression).isEqualTo(">= 20")
    assertThat(FeelComparisonCondition(20, ComparisonType.Less).expression).isEqualTo("< 20")
    assertThat(FeelComparisonCondition(20, ComparisonType.LessOrEqual).expression).isEqualTo("<= 20")
  }

  @Test
  fun `double comparison`() {
    assertThat(FeelComparisonCondition(10.1).expression).isEqualTo("10.1")
    assertThat(FeelComparisonCondition(10.1, ComparisonType.Equal).expression).isEqualTo("10.1")
    assertThat(FeelComparisonCondition(10.1, ComparisonType.Greater).expression).isEqualTo("> 10.1")
    assertThat(FeelComparisonCondition(10.1, ComparisonType.GreaterOrEqual).expression).isEqualTo(">= 10.1")
    assertThat(FeelComparisonCondition(10.1, ComparisonType.Less).expression).isEqualTo("< 10.1")
    assertThat(FeelComparisonCondition(10.1, ComparisonType.LessOrEqual).expression).isEqualTo("<= 10.1")
  }

}
