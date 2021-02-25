package io.holunda.decision.model.condition

import io.holunda.decision.model.condition.FeelCondition.Companion.DATE_FORMAT
import io.holunda.decision.model.condition.FeelCondition.Companion.negateExpression
import io.holunda.decision.model.condition.FeelCondition.Companion.toFeelString
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class FeelConditionTest {


  @Test
  fun `toFeelString Long`() {
    assertThat(toFeelString(5L)).isEqualTo("5")
  }

  @Test
  fun `toFeelString Int`() {
    assertThat(toFeelString(10)).isEqualTo("10")
  }

  @Test
  fun `toFeelString Double`() {
    assertThat(toFeelString(10.2)).isEqualTo("10.2")
  }

  @Test
  fun `toFeelString String`() {
    assertThat(toFeelString("hello world")).isEqualTo("\"hello world\"")
  }

  @Test
  fun `toFeelString Boolean`() {
    assertThat(toFeelString(true)).isEqualTo("true")
    assertThat(toFeelString(false)).isEqualTo("false")
  }

  @Test
  fun `toFeelString Date`() {
    val date = DATE_FORMAT.parse("2020-08-29T20:08:34")
    assertThat(toFeelString(date)).isEqualTo("date and time(\"2020-08-29T20:08:34\")")
  }

  @Test
  fun `verify notExpression`() {
    assertThat(negateExpression(null)).isNull()
    assertThat(negateExpression("[1..3]")).isEqualTo("not([1..3])")
  }
}
