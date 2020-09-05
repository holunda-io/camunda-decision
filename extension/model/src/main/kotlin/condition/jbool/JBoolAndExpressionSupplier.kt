package io.holunda.decision.model.condition.jbool

import com.bpodgursky.jbool_expressions.And
import com.bpodgursky.jbool_expressions.Expression

data class JBoolAndExpressionSupplier(
  val expressions: List<JBoolExpressionSupplier>
) : JBoolExpressionSupplier {

  override fun jbool(): Expression<FeelInputVariable> = And.of(
    expressions.map { it.jbool() }.toList()
  )
}
