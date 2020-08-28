package expression

import io.holunda.decision.model.api.CamundaDecisionModelApi
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.booleanInput
import io.holunda.decision.model.expression.FeelBooleanExpression
import io.holunda.decision.model.expression.FeelExpressions
import io.holunda.decision.model.expression.FeelExpressions.exprFalse
import io.holunda.decision.model.expression.FeelExpressions.exprTrue
import io.holunda.decision.model.expression.FeelExpressions.not
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
    assertThat(inBoolean.exprTrue().inputEntry.expression)
      .isEqualTo("true")
  }

  @Test
  fun `check for false`() {
    assertThat(inBoolean.exprFalse().inputEntry.expression)
      .isEqualTo("false")
  }

  @Test
  fun negate() {
    assertThat(not(inBoolean.exprFalse()).inputEntry.expression)
      .isEqualTo("true")

    assertThat(not(inBoolean.exprTrue()).inputEntry.expression)
      .isEqualTo("false")

    assertThat(not(not(inBoolean.exprTrue())).inputEntry.expression)
      .isEqualTo("true")
  }
}
