package io.holunda.decision.model.ext

import org.camunda.bpm.model.dmn.BuiltinAggregator
import org.camunda.bpm.model.dmn.HitPolicy
import org.camunda.bpm.model.dmn.instance.Decision
import org.camunda.bpm.model.dmn.instance.DecisionTable

fun Decision.getDecision() : DecisionTable = this.expression as DecisionTable

fun Decision.decisionTable(hitPolicy: HitPolicy, aggregation:BuiltinAggregator? = null ) = this.addChildElement(DecisionTable::class).apply {
  this.hitPolicy = hitPolicy
  if (hitPolicy == HitPolicy.COLLECT) {
    this.aggregation = aggregation
  }
}
