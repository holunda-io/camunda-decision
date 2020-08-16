package io.holunda.decision.model.ext

import io.holunda.decision.model.DataType
import io.holunda.decision.model.element.column.InputDefinition
import org.camunda.bpm.model.dmn.instance.Input

fun Input.toInputDefinition(): InputDefinition<*> = DataType.valueByTypeRef(inputExpression.typeRef)
  .inputDefinition(
    inputExpression.textContent,
    label
  )

