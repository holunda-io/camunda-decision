package io.holunda.decision.model.ext

import org.camunda.bpm.model.dmn.instance.Description
import org.camunda.bpm.model.dmn.instance.InputEntry
import org.camunda.bpm.model.dmn.instance.OutputEntry
import org.camunda.bpm.model.dmn.instance.Rule

/**
 * Creates an InputEntry on Rule and sets the given text value.
 */
fun Rule.inputEntry(value: String?) = this.addChildElement(InputEntry::class).apply {
  text = textContent(value)
}

/**
 * Creates an OutputEntry on Rule and sets the given text value.
 */
fun Rule.outputEntry(value: String?) = this.addChildElement(OutputEntry::class).apply {
  text = textContent(value)
}

/**
 * Adds a description (aka annotation) to the Rule.
 */
fun Rule.description(value:String?) = this.addChildElement(Description::class).apply {
  textContent = value
}
