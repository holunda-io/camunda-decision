package io.holunda.decision.model.expression

import io.holunda.decision.model.CamundaDecisionModel
import io.holunda.decision.model.FeelExpressions.exprAfter
import io.holunda.decision.model.FeelExpressions.exprBefore
import io.holunda.decision.model.FeelExpressions.exprBetween_old
import io.holunda.decision.model.FeelExpressions.exprExactly
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.dateInput
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

internal class FeelDateExpressionTest {
  companion object {
    val DATE_START = date("2020-08-29T19:57:22")
    val DATE_END = date("2020-09-30T08:57:22")
    fun date(value: String): Date = SimpleDateFormat(CamundaDecisionModel.Meta.DATE_TIME_FORMAT).parse(value)
    fun date(value: Date): String = SimpleDateFormat(CamundaDecisionModel.Meta.DATE_TIME_FORMAT).format(value)
  }

  private val inDate = dateInput("fooDate", "The Foo Date")

  @Test
  fun `default empty`() {
    assertThat(FeelDateExpression(inDate, FeelComparableCondition.Disjunction(setOf())).inputEntry.expression).isNull()
  }

  @Test
  fun comparison() {
    assertThat(inDate.exprExactly(DATE_START).inputEntry.expression)
      .isEqualTo("date and time(\"2020-08-29T19:57:22\")")

    assertThat(inDate.exprBefore(DATE_START).inputEntry.expression)
      .isEqualTo("< date and time(\"2020-08-29T19:57:22\")")

    assertThat(inDate.exprAfter(DATE_START).inputEntry.expression)
      .isEqualTo("> date and time(\"2020-08-29T19:57:22\")")
  }

  @Test
  fun interval() {
    assertThat(inDate.exprBetween_old(DATE_START, DATE_END).inputEntry.expression)
      .isEqualTo("[date and time(\"2020-08-29T19:57:22\")..date and time(\"2020-09-30T08:57:22\")]")
  }

}
