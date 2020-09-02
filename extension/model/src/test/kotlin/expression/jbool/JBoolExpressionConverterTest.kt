package io.holunda.decision.model.expression.jbool

import io.holunda.decision.model.FeelExpressions.feelEqual
import io.holunda.decision.model.FeelExpressions.feelGreaterThan
import io.holunda.decision.model.FeelExpressions.feelLessThan
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.longInput
import io.holunda.decision.model.expression.jbool.JBoolExpressionConverter.convert
import io.holunda.decision.model.expression.jbool.JBoolExpressionSupplier.Companion.and
import io.holunda.decision.model.expression.jbool.JBoolExpressionSupplier.Companion.or
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


internal class JBoolExpressionConverterTest {

  private val inFoo = longInput("foo")
  private val inBar = longInput("bar")
  private val inBaz = longInput("baz")

  private val feelFoo = inFoo.feelGreaterThan(5L)
  private val feelBar = inBar.feelLessThan(20L)
  private val feelBaz = inBaz.feelEqual(100L)

  @Test
  fun `transform single variable`() {
    val rows = convert(feelFoo)

    assertThat(rows).hasSize(1)
    assertThat(rows[0]).isEqualTo(listOf(feelFoo))
  }

  @Test
  fun `transform two variables (and)`() {
    val rows = convert(
      and(feelFoo, feelBar)
    )

    assertThat(rows).hasSize(1)
    assertThat(rows[0]).containsExactlyInAnyOrder(feelFoo, feelBar)
  }

  @Test
  fun `transform two variables (or)`() {
    val rows = convert(
      or(feelFoo, feelBar)
    )

    assertThat(rows)
      .hasSize(2)
      .containsExactlyInAnyOrder(
        listOf(feelFoo),
        listOf(feelBar)
      )
  }

  @Test
  fun `transform three variables (and - or)`() {
    val rows = convert(
      and(
        feelFoo,
        or(feelBar, feelBaz)
      ))

    assertThat(rows)
      .hasSize(2)
      .containsExactlyInAnyOrder(
        listOf(feelBaz, feelFoo),
        listOf(feelBar, feelFoo)
      )
  }
}
