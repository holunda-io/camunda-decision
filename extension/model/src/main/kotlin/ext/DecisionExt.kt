package io.holunda.decision.model.ext

import io.holunda.decision.model.CamundaDecisionModel.BIODI_NS
import org.camunda.bpm.model.dmn.BuiltinAggregator
import org.camunda.bpm.model.dmn.HitPolicy
import org.camunda.bpm.model.dmn.instance.Decision
import org.camunda.bpm.model.dmn.instance.DecisionTable
import org.camunda.bpm.model.dmn.instance.ExtensionElements

fun Decision.getDecisionTable(): DecisionTable = this.expression as DecisionTable

fun Decision.decisionTable(hitPolicy: HitPolicy, aggregation: BuiltinAggregator? = null) = this.addChildElement(DecisionTable::class).apply {
  this.hitPolicy = hitPolicy
  if (hitPolicy == HitPolicy.COLLECT) {
    this.aggregation = aggregation
  }
}

fun Decision.addCoordinates(bounds: Bounds) = this.addChildElement(ExtensionElements::class).apply {
  addExtensionElement(BIODI_NS, "bounds").apply {
    setAttributeValue("x", "${bounds.x}")
    setAttributeValue("y", "${bounds.y}")
    setAttributeValue("width", "${bounds.width}")
    setAttributeValue("height", "${bounds.height}")
  }
}

data class Bounds(val x: Int, val y: Int, val width: Int = 180, val height: Int = 80)
