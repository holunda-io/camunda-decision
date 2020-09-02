package expression

import io.holunda.decision.model.FeelExpressions
import io.holunda.decision.model.FeelExpressions.exprBetween_old
import io.holunda.decision.model.FeelExpressions.exprBetweenExclude_old
import io.holunda.decision.model.FeelExpressions.exprEquals_old
import io.holunda.decision.model.FeelExpressions.exprGreaterThan_old
import io.holunda.decision.model.FeelExpressions.exprGreaterThanOrEqual_old
import io.holunda.decision.model.FeelExpressions.exprInterval_old
import io.holunda.decision.model.FeelExpressions.exprLessThan_old
import io.holunda.decision.model.FeelExpressions.exprLessThanOrEqual_old
import io.holunda.decision.model.FeelExpressions.exprOneOf_old
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
    assertThat(inLong.exprOneOf_old(2, 1).inputEntry.expression)
      .isEqualTo("1,2")
    assertThat(inLong.exprOneOf_old(1).inputEntry.expression)
      .isEqualTo("1")
  }

  @Test
  fun comparison() {
    assertThat(inLong.exprEquals_old(1).inputEntry.expression)
      .isEqualTo("1")

    assertThat(FeelExpressions.not(inLong.exprEquals_old(1)).inputEntry.expression)
      .isEqualTo("not(1)")

    assertThat(inLong.exprGreaterThan_old(1).inputEntry.expression)
      .isEqualTo("> 1")

    assertThat(inLong.exprGreaterThanOrEqual_old(1).inputEntry.expression)
      .isEqualTo(">= 1")

    assertThat(inLong.exprLessThan_old(1).inputEntry.expression)
      .isEqualTo("< 1")

    assertThat(inLong.exprLessThanOrEqual_old(1).inputEntry.expression)
      .isEqualTo("<= 1")
  }

  @Test
  fun interval() {
    assertThat(inLong.exprBetween_old(1, 10).inputEntry.expression)
      .isEqualTo("[1..10]")
    assertThat(inLong.exprBetweenExclude_old(1, 10).inputEntry.expression)
      .isEqualTo("]1..10[")

    assertThat(inLong.exprInterval_old(1, 10, RangeType.Include).inputEntry.expression)
      .isEqualTo("[1..10]")
    assertThat(inLong.exprInterval_old(1, 10, RangeType.Exclude).inputEntry.expression)
      .isEqualTo("]1..10[")
    assertThat(inLong.exprInterval_old(1, 10, RangeType.IncludeExclude).inputEntry.expression)
      .isEqualTo("[1..10[")
    assertThat(inLong.exprInterval_old(1, 10, RangeType.ExcludeInclude).inputEntry.expression)
      .isEqualTo("]1..10]")
    assertThat(FeelExpressions.not(inLong.exprInterval_old(1, 10, RangeType.ExcludeInclude)).inputEntry.expression)
      .isEqualTo("not(]1..10])")
  }
}
