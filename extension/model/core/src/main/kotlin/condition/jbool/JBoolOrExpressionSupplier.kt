package io.holunda.decision.model.condition.jbool

import com.bpodgursky.jbool_expressions.Expression
import com.bpodgursky.jbool_expressions.Or

data class JBoolOrExpressionSupplier(
  val expressions: List<JBoolExpressionSupplier>
) : JBoolExpressionSupplier {

  override fun jbool(): Expression<FeelInputVariable> = Or.of(
    expressions.map { it.jbool() }.toList()
  )
}
