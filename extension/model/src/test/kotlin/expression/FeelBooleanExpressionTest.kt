package expression

import io.holunda.decision.model.FeelExpressions.exprFalse_old
import io.holunda.decision.model.FeelExpressions.exprTrue_old
import io.holunda.decision.model.FeelExpressions.not
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.booleanInput
import io.holunda.decision.model.expression.FeelBooleanExpression
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


internal class FeelBooleanExpressionTest {

  private val inBoolean = booleanInput("b", "Boolean Value")

  @Test
  fun `empty is null`() {
    assertThat(FeelBooleanExpression(inBoolean).inputEntry.expression).isNull()
  }

  @Test
  fun `check for true`() {
    assertThat(inBoolean.exprTrue_old().inputEntry.expression)
      .isEqualTo("true")
  }

  @Test
  fun `check for false`() {
    assertThat(inBoolean.exprFalse_old().inputEntry.expression)
      .isEqualTo("false")
  }

  @Test
  fun negate() {
    assertThat(not(inBoolean.exprFalse_old()).inputEntry.expression)
      .isEqualTo("true")

    assertThat(not(inBoolean.exprTrue_old()).inputEntry.expression)
      .isEqualTo("false")

    assertThat(not(not(inBoolean.exprTrue_old())).inputEntry.expression)
      .isEqualTo("true")
  }

}
