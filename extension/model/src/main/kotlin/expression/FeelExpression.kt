package io.holunda.decision.model.expression

import io.holunda.decision.model.CamundaDecisionModel
import io.holunda.decision.model.api.FeelString
import io.holunda.decision.model.api.element.InputEntry
import java.text.SimpleDateFormat
import java.util.*

interface FeelExpression<T : Any, SELF : FeelExpression<T, SELF>> {

  companion object {
    fun <T : Any> toFeelString(value: T): FeelString = when (value) {
      is Date -> "date and time(\"${SimpleDateFormat(CamundaDecisionModel.Meta.DATE_TIME_FORMAT).format(value)}\")"
      is Boolean -> if (value) "true" else "false"
      is String -> "\"$value\""
      else -> value.toString()
    }

    fun negateExpression(expression: FeelString?) = expression?.let { "not($it)" }
  }

  val inputEntry: InputEntry<T>

  val expression: FeelString?

  fun not(): SELF


}
