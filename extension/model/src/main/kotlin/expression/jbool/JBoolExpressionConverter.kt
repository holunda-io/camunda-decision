package io.holunda.decision.model.expression.jbool

import com.bpodgursky.jbool_expressions.And
import com.bpodgursky.jbool_expressions.Or
import com.bpodgursky.jbool_expressions.Variable
import com.bpodgursky.jbool_expressions.rules.RuleSet
import java.lang.IllegalStateException

object JBoolExpressionConverter {

  fun convert(jboolExpressionSupplier: JBoolExpressionSupplier): List<List<FeelInputVariable>> = convert(jboolExpressionSupplier.jbool())

  fun convert(jboolExpression: FeelInputExpression): List<List<FeelInputVariable>> {

    val rows = mutableListOf<List<FeelInputVariable>>()

    val rowExpressions = updateToOr(RuleSet.toDNF(jboolExpression))
    updateToOr(RuleSet.toDNF(jboolExpression)).children.forEach {
      val and = updateToAnd(it)
      rows.add(and.children.map { updateToVariable(it) }
        .map { it.value }.toList()
      )
    }

    return rows.map { it.sortedBy { it.definition.key }.toList() }.toList()
  }

  private fun updateToOr(expr: FeelInputExpression): Or<FeelInputVariable> = when (expr) {
    is Or -> expr
    is And -> updateToOr(Or.of(expr))
    is Variable -> updateToOr(And.of(expr))
    else -> throw IllegalStateException("updateToOr: missing branch for expression: '${expr.exprType}' - $expr")
  }

  private fun updateToAnd(expr: FeelInputExpression): And<FeelInputVariable> = when (expr) {
    is And -> expr
    is Variable -> updateToAnd(And.of(expr))
    else -> throw IllegalStateException("updateToAnd: missing branch for expression: '${expr.exprType}' - $expr")
  }

  private fun updateToVariable(expr: FeelInputExpression): Variable<FeelInputVariable> = when (expr) {
    is Variable -> expr
    else -> throw IllegalStateException("updateToAnd: missing branch for expression: '${expr.exprType}' - $expr")
  }
}
