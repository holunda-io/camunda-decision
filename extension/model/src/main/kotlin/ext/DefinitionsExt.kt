package io.holunda.decision.model.ext

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
