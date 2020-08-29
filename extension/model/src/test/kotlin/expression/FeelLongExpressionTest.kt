package expression

import io.holunda.decision.model.FeelExpressions
import io.holunda.decision.model.FeelExpressions.exprBetween
import io.holunda.decision.model.FeelExpressions.exprBetweenExclude
import io.holunda.decision.model.FeelExpressions.exprEquals
import io.holunda.decision.model.FeelExpressions.exprGreaterThan
import io.holunda.decision.model.FeelExpressions.exprGreaterThanOrEqual
import io.holunda.decision.model.FeelExpressions.exprInterval
import io.holunda.decision.model.FeelExpressions.exprLessThan
import io.holunda.decision.model.FeelExpressions.exprLessThanOrEqual
import io.holunda.decision.model.FeelExpressions.exprOneOf
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.longInput
import io.holunda.decision.model.expression.FeelComparableCondition
import io.holunda.decision.model.expression.FeelComparableCondition.Interval.RangeType
import io.holunda.decision.model.expression.FeelLongExpression
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class FeelLongExpressionTest {

  private val inLong = longInput("fooLong", "The Foo Long")

  @Test
  fun `default empty`() {
    assertThat(FeelLongExpression(inLong, FeelComparableCondition.Disjunction(setOf())).inputEntry.expression).isNull()
  }

  @Test
  fun `one of`() {
    assertThat(inLong.exprOneOf(2, 1).inputEntry.expression)
      .isEqualTo("1,2")
    assertThat(inLong.exprOneOf(1).inputEntry.expression)
      .isEqualTo("1")
  }

  @Test
  fun comparison() {
    assertThat(inLong.exprEquals(1).inputEntry.expression)
      .isEqualTo("1")

    assertThat(FeelExpressions.not(inLong.exprEquals(1)).inputEntry.expression)
      .isEqualTo("not(1)")

    assertThat(inLong.exprGreaterThan(1).inputEntry.expression)
      .isEqualTo("> 1")

    assertThat(inLong.exprGreaterThanOrEqual(1).inputEntry.expression)
      .isEqualTo(">= 1")

    assertThat(inLong.exprLessThan(1).inputEntry.expression)
      .isEqualTo("< 1")

    assertThat(inLong.exprLessThanOrEqual(1).inputEntry.expression)
      .isEqualTo("<= 1")
  }

  @Test
  fun interval() {
    assertThat(inLong.exprBetween(1, 10).inputEntry.expression)
      .isEqualTo("[1..10]")
    assertThat(inLong.exprBetweenExclude(1, 10).inputEntry.expression)
      .isEqualTo("]1..10[")

    assertThat(inLong.exprInterval(1, 10, RangeType.Include).inputEntry.expression)
      .isEqualTo("[1..10]")
    assertThat(inLong.exprInterval(1, 10, RangeType.Exclude).inputEntry.expression)
      .isEqualTo("]1..10[")
    assertThat(inLong.exprInterval(1, 10, RangeType.IncludeExclude).inputEntry.expression)
      .isEqualTo("[1..10[")
    assertThat(inLong.exprInterval(1, 10, RangeType.ExcludeInclude).inputEntry.expression)
      .isEqualTo("]1..10]")
    assertThat(FeelExpressions.not(inLong.exprInterval(1, 10, RangeType.ExcludeInclude)).inputEntry.expression)
      .isEqualTo("not(]1..10])")
  }
}
