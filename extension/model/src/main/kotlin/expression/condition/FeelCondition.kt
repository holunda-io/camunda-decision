package io.holunda.decision.model.expression.condition

import io.holunda.decision.model.CamundaDecisionModel
import io.holunda.decision.model.api.FeelString
import java.text.SimpleDateFormat
import java.util.*

interface FeelCondition<T : Comparable<T>> {

  companion object {
    val DATE_FORMAT: SimpleDateFormat get() = SimpleDateFormat(CamundaDecisionModel.Meta.DATE_TIME_FORMAT)

    fun toFeelString(value: Any): FeelString = when (value) {
      is Date -> "date and time(\"${DATE_FORMAT.format(value)}\")"
      is Boolean -> if (value) "true" else "false"
      is String -> "\"$value\""
      else -> value.toString()
    }

    fun negateExpression(expression: FeelString?) = expression?.let { "not($it)" }
  }

  val expression: FeelString?

  fun toFeelString(negate: Boolean): FeelString? = with(expression) {
    if (negate)
      io.holunda.decision.model.expression.FeelExpression.negateExpression(this)
    else
      this
  }
}
