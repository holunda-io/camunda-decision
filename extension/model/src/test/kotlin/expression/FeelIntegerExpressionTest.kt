package expression

import io.holunda.decision.model.FeelExpressions.exprBetween
import io.holunda.decision.model.FeelExpressions.exprBetweenExclude
import io.holunda.decision.model.FeelExpressions.exprEquals
import io.holunda.decision.model.FeelExpressions.exprGreaterThan
import io.holunda.decision.model.FeelExpressions.exprGreaterThanOrEqual
import io.holunda.decision.model.FeelExpressions.exprInterval
import io.holunda.decision.model.FeelExpressions.exprLessThan
import io.holunda.decision.model.FeelExpressions.exprLessThanOrEqual
import io.holunda.decision.model.FeelExpressions.exprOneOf
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
    assertThat(inInt.exprOneOf(2, 1).inputEntry.expression)
      .isEqualTo("1,2")
    assertThat(inInt.exprOneOf(1).inputEntry.expression)
      .isEqualTo("1")
  }

  @Test
  fun comparison() {
    assertThat(inInt.exprEquals(1).inputEntry.expression)
      .isEqualTo("1")

    assertThat(not(inInt.exprEquals(1)).inputEntry.expression)
      .isEqualTo("not(1)")

    assertThat(inInt.exprGreaterThan(1).inputEntry.expression)
      .isEqualTo("> 1")

    assertThat(inInt.exprGreaterThanOrEqual(1).inputEntry.expression)
      .isEqualTo(">= 1")

    assertThat(inInt.exprLessThan(1).inputEntry.expression)
      .isEqualTo("< 1")

    assertThat(inInt.exprLessThanOrEqual(1).inputEntry.expression)
      .isEqualTo("<= 1")
  }

  @Test
  fun interval() {
    assertThat(inInt.exprBetween(1, 10).inputEntry.expression)
      .isEqualTo("[1..10]")
    assertThat(inInt.exprBetweenExclude(1, 10).inputEntry.expression)
      .isEqualTo("]1..10[")

    assertThat(inInt.exprInterval(1, 10, RangeType.Include).inputEntry.expression)
      .isEqualTo("[1..10]")
    assertThat(inInt.exprInterval(1, 10, RangeType.Exclude).inputEntry.expression)
      .isEqualTo("]1..10[")
    assertThat(inInt.exprInterval(1, 10, RangeType.IncludeExclude).inputEntry.expression)
      .isEqualTo("[1..10[")
    assertThat(inInt.exprInterval(1, 10, RangeType.ExcludeInclude).inputEntry.expression)
      .isEqualTo("]1..10]")
    assertThat(not(inInt.exprInterval(1, 10, RangeType.ExcludeInclude)).inputEntry.expression)
      .isEqualTo("not(]1..10])")
  }
}
