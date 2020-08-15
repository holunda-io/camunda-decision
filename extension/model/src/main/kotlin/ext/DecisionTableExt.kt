package io.holunda.decision.model.ext

import io.holunda.decision.model.element.DmnDecisionTable
import io.holunda.decision.model.element.DmnHitPolicy
import io.holunda.decision.model.element.InputDefinition
import io.holunda.decision.model.element.OutputDefinition
import org.camunda.bpm.model.dmn.HitPolicy
import org.camunda.bpm.model.dmn.instance.DecisionTable
import org.camunda.bpm.model.dmn.instance.Input
import org.camunda.bpm.model.dmn.instance.InputExpression
import org.camunda.bpm.model.dmn.instance.Output

val DecisionTable.header : DmnDecisionTable.Header get() = DmnDecisionTable.Header(
  inputs = inputs.map { it.toInputDefinition() },
  outputs = outputs.map { it.toOutputDefinition() }
)

val DecisionTable.dmnHitPolicy get() = DmnHitPolicy.valueOf(this.hitPolicy, this.aggregation)

fun DecisionTable.output(column: OutputDefinition<*>) = addChildElement(Output::class).apply {
  this.name = column.key
  this.label = column.label
  this.typeRef = column.typeRef
}

fun DecisionTable.input(column: InputDefinition<*>) = addChildElement(Input::class).apply {
  this.label = column.label
  inputExpression = newInstance(InputExpression::class).apply {
    text = textContent(column.key)
    typeRef = column.typeRef
  }
}
