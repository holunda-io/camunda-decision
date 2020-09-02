package expression

import io.holunda.decision.model.FeelExpressions.exprBetween_old
import io.holunda.decision.model.FeelExpressions.exprBetweenExclude_old
import io.holunda.decision.model.FeelExpressions.exprEquals_old
import io.holunda.decision.model.FeelExpressions.exprGreaterThan_old
import io.holunda.decision.model.FeelExpressions.exprGreaterThanOrEqual_old
import io.holunda.decision.model.FeelExpressions.exprInterval_old
import io.holunda.decision.model.FeelExpressions.exprLessThan_old
import io.holunda.decision.model.FeelExpressions.exprLessThanOrEqual_old
import io.holunda.decision.model.FeelExpressions.exprOneOf_old
import io.holunda.decision.model.FeelExpressions.not
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.integerInput
import io.holunda.decision.model.expression.FeelComparableCondition
import io.holunda.decision.model.expression.FeelComparableCondition.Interval.RangeType
import io.holunda.decision.model.expression.FeelIntegerExpression
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class FeelIntegerExpressionTest {

  private val inInt = integerInput("fooInt", "The Foo Int")


  @Test
  fun `default empty`() {
    assertThat(FeelIntegerExpression(inInt, FeelComparableCondition.Disjunction(setOf())).inputEntry.expression).isNull()
  }

  @Test
  fun `one of`() {
    assertThat(inInt.exprOneOf_old(2, 1).inputEntry.expression)
      .isEqualTo("1,2")
    assertThat(inInt.exprOneOf_old(1).inputEntry.expression)
      .isEqualTo("1")
  }

  @Test
  fun comparison() {
    assertThat(inInt.exprEquals_old(1).inputEntry.expression)
      .isEqualTo("1")

    assertThat(not(inInt.exprEquals_old(1)).inputEntry.expression)
      .isEqualTo("not(1)")

    assertThat(inInt.exprGreaterThan_old(1).inputEntry.expression)
      .isEqualTo("> 1")

    assertThat(inInt.exprGreaterThanOrEqual_old(1).inputEntry.expression)
      .isEqualTo(">= 1")

    assertThat(inInt.exprLessThan_old(1).inputEntry.expression)
      .isEqualTo("< 1")

    assertThat(inInt.exprLessThanOrEqual_old(1).inputEntry.expression)
      .isEqualTo("<= 1")
  }

  @Test
  fun interval() {
    assertThat(inInt.exprBetween_old(1, 10).inputEntry.expression)
      .isEqualTo("[1..10]")
    assertThat(inInt.exprBetweenExclude_old(1, 10).inputEntry.expression)
      .isEqualTo("]1..10[")

    assertThat(inInt.exprInterval_old(1, 10, RangeType.Include).inputEntry.expression)
      .isEqualTo("[1..10]")
    assertThat(inInt.exprInterval_old(1, 10, RangeType.Exclude).inputEntry.expression)
      .isEqualTo("]1..10[")
    assertThat(inInt.exprInterval_old(1, 10, RangeType.IncludeExclude).inputEntry.expression)
      .isEqualTo("[1..10[")
    assertThat(inInt.exprInterval_old(1, 10, RangeType.ExcludeInclude).inputEntry.expression)
      .isEqualTo("]1..10]")
    assertThat(not(inInt.exprInterval_old(1, 10, RangeType.ExcludeInclude)).inputEntry.expression)
      .isEqualTo("not(]1..10])")
  }
}
