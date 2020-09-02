package io.holunda.decision.model.expression.jbool

import com.bpodgursky.jbool_expressions.Expression
import com.bpodgursky.jbool_expressions.Not
import com.bpodgursky.jbool_expressions.Variable
import io.holunda.decision.model.api.definition.InputDefinition
import io.holunda.decision.model.expression.condition.FeelCondition

data class FeelInputVariable(
  val definition: InputDefinition<*>,
  val condition: FeelCondition<*>,
  val negate: Boolean = false) : JBoolExpressionSupplier {

  override fun jbool(): Expression<FeelInputVariable> = with(Variable.of(this)) {
    if (negate) Not.of(this) else this
  }

  fun negate(): FeelInputVariable = copy(negate = !negate)

  override fun toString() = "'${definition.key}{${condition.expression}}'"
}
