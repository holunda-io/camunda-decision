package io.holunda.decision.model.expression

sealed class Expression {

  abstract fun getExpression() : String

  data class FeelExpression( val expr:String) : Expression() {
    override fun getExpression(): String = expr
  }
}
