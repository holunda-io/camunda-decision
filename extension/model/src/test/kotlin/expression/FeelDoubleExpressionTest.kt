package io.holunda.decision.model.expression

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
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.doubleInput
import io.holunda.decision.model.expression.FeelComparableCondition.Interval.RangeType
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class FeelDoubleExpressionTest {

  private val inDouble = doubleInput("fooDouble", "The Foo Double")


  @Test
  fun `default empty`() {
    assertThat(FeelDoubleExpression(inDouble, FeelComparableCondition.Disjunction(setOf())).inputEntry.expression).isNull()
  }

  @Test
  fun `one of`() {
    assertThat(inDouble.exprOneOf_old(2.0, 1.1).inputEntry.expression)
      .isEqualTo("1.1,2.0")
    assertThat(inDouble.exprOneOf_old(1.2).inputEntry.expression)
      .isEqualTo("1.2")
  }

  @Test
  fun comparison() {
    assertThat(inDouble.exprEquals_old(1.2).inputEntry.expression)
      .isEqualTo("1.2")

    assertThat(FeelExpressions.not(inDouble.exprEquals_old(1.2)).inputEntry.expression)
      .isEqualTo("not(1.2)")

    assertThat(inDouble.exprGreaterThan_old(1.2).inputEntry.expression)
      .isEqualTo("> 1.2")

    assertThat(inDouble.exprGreaterThanOrEqual_old(1.2).inputEntry.expression)
      .isEqualTo(">= 1.2")

    assertThat(inDouble.exprLessThan_old(1.2).inputEntry.expression)
      .isEqualTo("< 1.2")

    assertThat(inDouble.exprLessThanOrEqual_old(1.2).inputEntry.expression)
      .isEqualTo("<= 1.2")
  }

  @Test
  fun interval() {
    assertThat(inDouble.exprBetween_old(1.2, 10.2).inputEntry.expression)
      .isEqualTo("[1.2..10.2]")
    assertThat(inDouble.exprBetweenExclude_old(1.2, 10.2).inputEntry.expression)
      .isEqualTo("]1.2..10.2[")

    assertThat(inDouble.exprInterval_old(1.2, 10.2, RangeType.Include).inputEntry.expression)
      .isEqualTo("[1.2..10.2]")
    assertThat(inDouble.exprInterval_old(1.2, 10.2, RangeType.Exclude).inputEntry.expression)
      .isEqualTo("]1.2..10.2[")
    assertThat(inDouble.exprInterval_old(1.2, 10.2, RangeType.IncludeExclude).inputEntry.expression)
      .isEqualTo("[1.2..10.2[")
    assertThat(inDouble.exprInterval_old(1.2, 10.2, RangeType.ExcludeInclude).inputEntry.expression)
      .isEqualTo("]1.2..10.2]")
    assertThat(FeelExpressions.not(inDouble.exprInterval_old(1.2, 10.2, RangeType.ExcludeInclude)).inputEntry.expression)
      .isEqualTo("not(]1.2..10.2])")
  }

}
