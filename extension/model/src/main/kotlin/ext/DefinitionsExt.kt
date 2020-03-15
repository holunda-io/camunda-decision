package io.holunda.decision.model.ext

import io.holunda.decision.model.DecisionDefinitionKey
import org.camunda.bpm.model.dmn.instance.Decision
import org.camunda.bpm.model.dmn.instance.Definitions


fun Definitions.decision(key: String, name: String, versionTag: String?): Decision {
  val decision = this.modelInstance.newInstance(Decision::class, key).apply {
    this.name = name
    this.versionTag = versionTag
  }
  addChildElement(decision)
  return decision
}

fun Definitions.findDecisionByKey(key: DecisionDefinitionKey): Decision? = this.getChildElementsByType(Decision::class.java)
  .find { it.id == key }

fun Definitions.findDecisions() = this.getChildElementsByType(Decision::class.java).toList()
