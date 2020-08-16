package io.holunda.decision.model.ext

import io.holunda.decision.model.DecisionDefinitionKey
import org.camunda.bpm.model.dmn.instance.Decision
import org.camunda.bpm.model.dmn.instance.Definitions

/**
 * Creates a new decision element with name and optional version tag.
 *
 * @return the created element
 */
fun Definitions.decision(key: String, name: String, versionTag: String?): Decision {
  val decision = this.modelInstance.newInstance(Decision::class, key).apply {
    this.name = name
    this.versionTag = versionTag
  }
  addChildElement(decision)
  return decision
}

/**
 * Finds decision by key.
 */
fun Definitions.findDecisionByKey(key: DecisionDefinitionKey): Decision? = this.findDecisions()
  .find { it.id == key }

/**
 * @return list of all decisions
 */
fun Definitions.findDecisions() = this.getChildElementsByType(Decision::class.java).toList()
