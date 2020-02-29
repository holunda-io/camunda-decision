package io.holunda.decision.model.ext

import io.holunda.decision.model.element.DmnDecisionTable
import org.camunda.bpm.model.dmn.instance.DecisionTable

fun DecisionTable.toHeader() = DmnDecisionTable.Header(
  inputs = inputs.map { it.toInputDefinition() },
  outputs = outputs.map { it.toOutputDefinition() }
)
