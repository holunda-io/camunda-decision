package io.holunda.decision.model.expression

import io.holunda.decision.model.api.element.InputEntry


interface FeelExpression<T : Any, SELF : FeelExpression<T, SELF>> {

  val inputEntry: InputEntry<T>

  fun not() : SELF

}

