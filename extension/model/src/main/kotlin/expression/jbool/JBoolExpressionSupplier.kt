package io.holunda.decision.model.expression.jbool

import com.bpodgursky.jbool_expressions.Expression
import com.bpodgursky.jbool_expressions.rules.RuleSet

interface JBoolExpressionSupplier {
  companion object {
    fun and(vararg expressions: JBoolExpressionSupplier) = JBoolAndExpressionSupplier(expressions = expressions.asList())
    fun or(vararg expressions: JBoolExpressionSupplier) = JBoolOrExpressionSupplier(expressions = expressions.asList())
    fun not(variable: FeelInputVariable) = variable.negate()
  }

  fun jbool(): FeelInputExpression

  fun jboolDnf(): FeelInputExpression = RuleSet.toDNF(jbool())
}

typealias FeelInputExpression = Expression<FeelInputVariable>

