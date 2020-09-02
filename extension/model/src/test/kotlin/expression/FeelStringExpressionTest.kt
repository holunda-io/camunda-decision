package expression

import io.holunda.decision.model.FeelExpressions.exprEquals_old
import io.holunda.decision.model.FeelExpressions.exprMatchNone_old
import io.holunda.decision.model.FeelExpressions.exprMatchOne_old
import io.holunda.decision.model.FeelExpressions.not
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.stringInput
import io.holunda.decision.model.expression.FeelStringExpression
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class FeelStringExpressionTest {

  private val inFoo = stringInput("foo", "Foo Label")

  @Test
  fun empty() {
    assertThat(FeelStringExpression(inFoo).inputEntry.expression)
      .isNull()
  }

  @Test
  fun `equal single value`() {
    assertThat(inFoo.exprEquals_old("X").inputEntry.expression)
      .isEqualTo("\"X\"")
  }

  @Test
  fun `equal match one`() {
    assertThat(inFoo.exprMatchOne_old("X", "Y", "X").inputEntry.expression)
      .isEqualTo("\"X\",\"Y\"")
  }

  @Test
  fun `equal match none`() {
    assertThat(inFoo.exprMatchNone_old("X", "Y", "X").inputEntry.expression)
      .isEqualTo("not(\"X\",\"Y\")")

    assertThat(inFoo.exprMatchOne_old("X", "Y", "X").not().inputEntry.expression)
      .isEqualTo("not(\"X\",\"Y\")")
  }

  @Test
  fun `not equal`() {
    assertThat(not(inFoo.exprEquals_old("A")).inputEntry.expression)
      .isEqualTo("not(\"A\")")
  }
}
