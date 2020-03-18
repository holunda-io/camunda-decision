package io.holunda.decision.model.ext

import io.holunda.decision.model.BIODI_NS
import io.holunda.decision.model.DecisionDefinitionKey
import io.holunda.decision.model.converter.DmnDiagramLayout
import org.camunda.bpm.model.dmn.BuiltinAggregator
import org.camunda.bpm.model.dmn.HitPolicy
import org.camunda.bpm.model.dmn.instance.Decision
import org.camunda.bpm.model.dmn.instance.DecisionTable
import org.camunda.bpm.model.dmn.instance.ExtensionElements

fun Decision.getDecisionTable(): DecisionTable = this.expression as DecisionTable

fun Decision.getRequiredDecisions() : Set<DecisionDefinitionKey> = this.informationRequirements
  .map { it.requiredDecision }
  .map { it.id }
  .toSet()

fun Decision.decisionTable(hitPolicy: HitPolicy, aggregation: BuiltinAggregator? = null) = this.addChildElement(DecisionTable::class).apply {
  this.hitPolicy = hitPolicy
  if (hitPolicy == HitPolicy.COLLECT && aggregation != null) { // FIXME: data class for hitPolicy and aggregator
    this.aggregation = aggregation
  }
}

fun Decision.extensionElement(box: DmnDiagramLayout.Box) {
  val extensionElements = this.addChildElement(ExtensionElements::class)

  extensionElements.addExtensionElement(BIODI_NS, "bounds").apply {
    setAttributeValue("x", "${box.x}")
    setAttributeValue("y", "${box.y}")
    setAttributeValue("width", "${box.width}")
    setAttributeValue("height", "${box.height}")
  }

  if (box.edge != null) {
    val edge = extensionElements.addExtensionElement(BIODI_NS, "edge")
    edge.setAttributeValue("source", box.edge.source)

    for (waypoint in box.edge.waypoints) {
      edge.domElement.appendChild(edge.domElement.document.createElement(BIODI_NS, "waypoints").apply {
        setAttribute("x", "${waypoint.x}")
        setAttribute("y", "${waypoint.y}")
      })
    }
  }
}
