package io.holunda.decision.model.ext

import org.camunda.bpm.model.dmn.instance.Description
import org.camunda.bpm.model.dmn.instance.InputEntry
import org.camunda.bpm.model.dmn.instance.OutputEntry
import org.camunda.bpm.model.dmn.instance.Rule


fun Rule.inputEntry(value: String?) = this.addChildElement(InputEntry::class).apply {
  text = textContent(value)
}

fun Rule.outputEntry(value: String?) = this.addChildElement(OutputEntry::class).apply {
  text = textContent(value)
}

fun Rule.description(value:String?) = this.addChildElement(Description::class).apply {
  textContent = value
}
