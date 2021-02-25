package io.holunda.decision.model.condition.jbool

import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.integerInput
import io.holunda.decision.model.condition.FeelDisjunctionCondition
import io.holunda.decision.model.condition.jbool.JBoolExpressionSupplier.Companion.and
import io.holunda.decision.model.condition.jbool.JBoolExpressionSupplier.Companion.not
import io.holunda.decision.model.condition.jbool.JBoolExpressionSupplier.Companion.or
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class JBoolExpressionSupplierTest {

  private val inFoo = integerInput("foo")
  private val fooFeel = FeelInputVariable(inFoo, FeelDisjunctionCondition(1))
  private val fooString = "'foo{1}'"
  private val literalFooString = "('foo{1}')"

  private val inBar = integerInput("bar")
  private val barFeel = FeelInputVariable(inBar, FeelDisjunctionCondition(2))
  private val barString = "'bar{2}'"

  private val inBaz = integerInput("baz")
  private val bazFeel = FeelInputVariable(inBaz, FeelDisjunctionCondition(3))


  @Test
  fun `feelInputVariable to Variable`() {
    assertThat(fooFeel.jbool().toLexicographicString()).isEqualTo(fooString)
  }

  @Test
  fun `single variable - and`() {
    assertThat(and(fooFeel).jbool().toLexicographicString()).isEqualTo(literalFooString)
  }

  @Test
  fun `single variable - and not`() {
    assertThat(and(not(fooFeel))
      .jbool().toLexicographicString())
      .isEqualTo("(!$fooString)")

    assertThat(and(not(fooFeel.negate()))
      .jbool().toLexicographicString())
      .isEqualTo("($fooString)")
  }

  @Test
  fun `or with single variable`() {
    assertThat(or(fooFeel).jbool().toLexicographicString()).isEqualTo(literalFooString)
  }

  @Test
  fun `and with two variables`() {
    assertThat(and(
      fooFeel,
      barFeel
    ).jbool().toLexicographicString()).isEqualTo("($barString & $fooString)")
  }

  @Test
  fun `or with two variables`() {
    assertThat(or(
      fooFeel,
      barFeel
    ).jbool().toLexicographicString()).isEqualTo("($barString | $fooString)")
  }

  @Test
  fun `and with only one or element`() {
    assertThat(and(or(fooFeel, barFeel)).jboolDnf().toLexicographicString())
      .isEqualTo("($barString | $fooString)")
  }

  @Test
  fun `and or with three variables`() {
    assertThat(and(
      bazFeel,
      or(
        fooFeel,
        barFeel
      )
    ).jbool().toLexicographicString()).isEqualTo("('baz{3}' & ('bar{2}' | 'foo{1}'))")
  }

  @Test
  fun `dnf of - and or with three variables`() {
    assertThat(and(
      bazFeel,
      or(
        fooFeel,
        barFeel
      )
    ).jboolDnf().toLexicographicString())
      .isEqualTo("(('bar{2}' & 'baz{3}') | ('baz{3}' & 'foo{1}'))")
  }
}
