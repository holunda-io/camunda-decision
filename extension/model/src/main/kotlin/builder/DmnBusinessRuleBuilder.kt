package io.holunda.decision.model.builder

import io.holunda.decision.model.api.element.DmnRule
import io.holunda.decision.model.api.element.DmnRuleList
import io.holunda.decision.model.api.element.InputEntry
import io.holunda.decision.model.api.element.OutputEntry
import org.apache.commons.lang3.builder.Builder

/**
 * Though this will generally just create a list containing a single "and" connected rule,
 * it might become a list of rules when you use "or" with different values.
 */
class DmnBusinessRuleBuilder : Builder<DmnRuleList> {

  private var description: String? = null
  private val inputs = mutableListOf<InputEntry<*>>()
  private val outputs = mutableListOf<OutputEntry<*>>()

  fun description(description: String) = apply {
    this.description = description
  }

  fun <T : Any> condition(input: InputEntry<T>) = apply {
    this.inputs.add(input)
  }

  fun <T : Any> and(input: InputEntry<T>) = condition(input)

  fun <T : Any> outputs(vararg outputs: OutputEntry<T>) = apply {
    this.outputs.addAll(outputs.toList())
  }

  override fun build(): DmnRuleList {
    require(inputs.isNotEmpty()) { "Must provide inputs: $this" }
    require(outputs.isNotEmpty()) { "Must provide outputs: $this" }

    val rule = DmnRule(
      description = description,
      inputs = inputs,
      outputs = outputs
    )
    return DmnRuleList(rule)
  }

  override fun toString() = """DmnRulesBuilder(
    description=$description,
    inputs=$inputs,
    outputs=$outputs
    )"""

}
