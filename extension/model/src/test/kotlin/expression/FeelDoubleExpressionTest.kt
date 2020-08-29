package io.holunda.decision.model.expression

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
    assertThat(inDouble.exprOneOf(2.0, 1.1).inputEntry.expression)
      .isEqualTo("1.1,2.0")
    assertThat(inDouble.exprOneOf(1.2).inputEntry.expression)
      .isEqualTo("1.2")
  }

  @Test
  fun comparison() {
    assertThat(inDouble.exprEquals(1.2).inputEntry.expression)
      .isEqualTo("1.2")

    assertThat(FeelExpressions.not(inDouble.exprEquals(1.2)).inputEntry.expression)
      .isEqualTo("not(1.2)")

    assertThat(inDouble.exprGreaterThan(1.2).inputEntry.expression)
      .isEqualTo("> 1.2")

    assertThat(inDouble.exprGreaterThanOrEqual(1.2).inputEntry.expression)
      .isEqualTo(">= 1.2")

    assertThat(inDouble.exprLessThan(1.2).inputEntry.expression)
      .isEqualTo("< 1.2")

    assertThat(inDouble.exprLessThanOrEqual(1.2).inputEntry.expression)
      .isEqualTo("<= 1.2")
  }

  @Test
  fun interval() {
    assertThat(inDouble.exprBetween(1.2, 10.2).inputEntry.expression)
      .isEqualTo("[1.2..10.2]")
    assertThat(inDouble.exprBetweenExclude(1.2, 10.2).inputEntry.expression)
      .isEqualTo("]1.2..10.2[")

    assertThat(inDouble.exprInterval(1.2, 10.2, RangeType.Include).inputEntry.expression)
      .isEqualTo("[1.2..10.2]")
    assertThat(inDouble.exprInterval(1.2, 10.2, RangeType.Exclude).inputEntry.expression)
      .isEqualTo("]1.2..10.2[")
    assertThat(inDouble.exprInterval(1.2, 10.2, RangeType.IncludeExclude).inputEntry.expression)
      .isEqualTo("[1.2..10.2[")
    assertThat(inDouble.exprInterval(1.2, 10.2, RangeType.ExcludeInclude).inputEntry.expression)
      .isEqualTo("]1.2..10.2]")
    assertThat(FeelExpressions.not(inDouble.exprInterval(1.2, 10.2, RangeType.ExcludeInclude)).inputEntry.expression)
      .isEqualTo("not(]1.2..10.2])")
  }

}
