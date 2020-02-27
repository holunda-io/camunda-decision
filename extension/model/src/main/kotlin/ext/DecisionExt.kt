package io.holunda.decision.model.ext

import org.camunda.bpm.model.dmn.instance.Decision
import org.camunda.bpm.model.dmn.instance.DecisionTable

fun Decision.getDecision() : DecisionTable = this.expression as DecisionTable
