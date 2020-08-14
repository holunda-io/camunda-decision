package io.holunda.decision.model.ext

import io.holunda.decision.model.data.DataType
import io.holunda.decision.model.element.InputDefinition
import org.camunda.bpm.model.dmn.instance.Input

fun Input.toInputDefinition(): InputDefinition<*> = DataType.valueOf(inputExpression.typeRef)
  .inputDefinition(
    inputExpression.textContent,
    label
  )

