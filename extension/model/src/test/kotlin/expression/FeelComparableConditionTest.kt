package expression

import io.holunda.decision.model.expression.FeelComparableCondition.*
import io.holunda.decision.model.expression.FeelComparableCondition.Comparison.ComparisonType.*
import io.holunda.decision.model.expression.FeelComparableCondition.Interval.RangeType.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

internal class FeelComparableConditionTest {

  @Test
  fun disjunction() {
    assertThat(Disjunction(setOf(1L)).toFeelString(false)).isEqualTo("1")
  }

  @Test
  fun `disjunction sorts values`() {
    assertThat(Disjunction(setOf(3L, 4L, 1L)).toFeelString(false)).isEqualTo("1,3,4")
  }

  @Test
  fun comparison() {
    assertThat(Comparison(1L, Equals).expression).isEqualTo("1")
    assertThat(Comparison(1L, Greater).expression).isEqualTo("> 1")
    assertThat(Comparison(1L, GreaterOrEqual).expression).isEqualTo(">= 1")
    assertThat(Comparison(1L, Less).expression).isEqualTo("< 1")
    assertThat(Comparison(1L, LessOrEqual).expression).isEqualTo("<= 1")
  }

  @Test
  fun interval() {
    assertThat(Interval(1L, 5L).expression).isEqualTo("[1..5]")
    assertThat(Interval(1L, 5L, Exclude).expression).isEqualTo("]1..5[")
    assertThat(Interval(1L, 5L, IncludeExclude).expression).isEqualTo("[1..5[")
    assertThat(Interval(1L, 5L, ExcludeInclude).expression).isEqualTo("]1..5]")
  }

  @Test
  fun `interval fails when end lt start`() {
    assertThatThrownBy { Interval(2L, 2L) }
      .isInstanceOf(IllegalArgumentException::class.java)
      .hasMessage("Interval requires start < end, was (2,2)")

    assertThatThrownBy { Interval(5L, 1L) }
      .isInstanceOf(IllegalArgumentException::class.java)
      .hasMessage("Interval requires start < end, was (5,1)")
  }
}
