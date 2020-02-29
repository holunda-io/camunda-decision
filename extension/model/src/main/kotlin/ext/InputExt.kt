package io.holunda.decision.model.ext

import io.holunda.decision.model.element.InputDefinition
import io.holunda.decision.model.data.DataType
import org.camunda.bpm.model.dmn.instance.Input

fun Input.toInputDefinition() = InputDefinition(
    key = inputExpression.textContent,
    label = label,
    type = DataType.valueOf(inputExpression.typeRef.toUpperCase())
)
