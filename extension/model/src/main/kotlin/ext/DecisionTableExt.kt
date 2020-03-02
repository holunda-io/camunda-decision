package io.holunda.decision.model.ext

import io.holunda.decision.model.element.DmnDecisionTable
import io.holunda.decision.model.element.InputDefinition
import io.holunda.decision.model.element.OutputDefinition
import org.camunda.bpm.model.dmn.instance.DecisionTable
import org.camunda.bpm.model.dmn.instance.Input
import org.camunda.bpm.model.dmn.instance.InputExpression
import org.camunda.bpm.model.dmn.instance.Output

fun DecisionTable.toHeader() = DmnDecisionTable.Header(
  inputs = inputs.map { it.toInputDefinition() },
  outputs = outputs.map { it.toOutputDefinition() }
)


fun DecisionTable.output(column: OutputDefinition) = addChildElement(Output::class).apply {
  this.name = column.key
  this.label = column.label
  this.typeRef = column.type.typeRef
}

fun DecisionTable.input(column: InputDefinition) = addChildElement(Input::class).apply {
  this.label = column.label
  inputExpression = newInstance(InputExpression::class).apply {
    text = textContent(column.key)
    typeRef = column.type.typeRef
  }
}
