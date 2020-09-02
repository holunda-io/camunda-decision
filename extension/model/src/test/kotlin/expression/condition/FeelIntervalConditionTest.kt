package expression.condition

import io.holunda.decision.model.expression.condition.FeelIntervalCondition
import io.holunda.decision.model.expression.condition.FeelIntervalCondition.RangeType
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test


internal class FeelIntervalConditionTest {

  // no interval for boolean, string, date

  @Test
  fun `long interval`() {
    assertThat(FeelIntervalCondition(1L, 5L).expression).isEqualTo("[1..5]")
    assertThat(FeelIntervalCondition(1L, 5L, RangeType.Exclude).expression).isEqualTo("]1..5[")
    assertThat(FeelIntervalCondition(1L, 5L, RangeType.IncludeExclude).expression).isEqualTo("[1..5[")
    assertThat(FeelIntervalCondition(1L, 5L, RangeType.ExcludeInclude).expression).isEqualTo("]1..5]")
  }

  @Test
  fun `double interval`() {
    assertThat(FeelIntervalCondition(1.2, 10.2).expression)
      .isEqualTo("[1.2..10.2]")
    assertThat(FeelIntervalCondition(1.2, 10.2, RangeType.Include).expression)
      .isEqualTo("[1.2..10.2]")

    assertThat(FeelIntervalCondition(1.2, 10.2, RangeType.Exclude).expression)
      .isEqualTo("]1.2..10.2[")

    assertThat(FeelIntervalCondition(1.2, 10.2, RangeType.IncludeExclude).expression)
      .isEqualTo("[1.2..10.2[")

    assertThat(FeelIntervalCondition(1.2, 10.2, RangeType.ExcludeInclude).expression)
      .isEqualTo("]1.2..10.2]")
  }

  @Test
  fun `integer interval`() {
    assertThat(FeelIntervalCondition(1, 10).expression)
      .isEqualTo("[1..10]")
    assertThat(FeelIntervalCondition(1, 10, RangeType.Include).expression)
      .isEqualTo("[1..10]")

    assertThat(FeelIntervalCondition(1, 10, RangeType.Exclude).expression)
      .isEqualTo("]1..10[")

    assertThat(FeelIntervalCondition(1, 10, RangeType.IncludeExclude).expression)
      .isEqualTo("[1..10[")

    assertThat(FeelIntervalCondition(1, 10, RangeType.ExcludeInclude).expression)
      .isEqualTo("]1..10]")
  }

  @Test
  fun `interval fails when end lt start`() {
    assertThatThrownBy { FeelIntervalCondition(2L, 2L) }
      .isInstanceOf(IllegalArgumentException::class.java)
      .hasMessage("Interval requires start < end, was (2,2)")

    assertThatThrownBy { FeelIntervalCondition(5L, 1L) }
      .isInstanceOf(IllegalArgumentException::class.java)
      .hasMessage("Interval requires start < end, was (5,1)")
  }
}
