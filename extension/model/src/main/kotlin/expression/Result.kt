package io.holunda.decision.model.expression

sealed class Result {
  abstract fun getResult() : String

  data class FeelResult(val expr: String) : Result() {
    override fun getResult() = expr
  }
}
