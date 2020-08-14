package io.holunda.decision.generator.builder

import io.holunda.decision.model.element.DmnRule
import io.holunda.decision.model.element.DmnRules
import io.holunda.decision.model.element.InputEntry
import io.holunda.decision.model.element.OutputEntry
import org.apache.commons.lang3.builder.Builder

/**
 * Though this will generally just create a list containing a single "and" connected rule,
 * it might become a list of rules when you use "or" with different values.
 */
class DmnRulesBuilder : Builder<DmnRules> {

  private var description : String? = null
  private val inputs = mutableListOf<InputEntry<*>>()
  private val outputs = mutableListOf<OutputEntry<*>>()

  fun description(description:String) = apply {
    this.description = description
  }

  fun <T:Any> condition(input : InputEntry<T>) = apply {
    this.inputs.add(input)
  }

  fun <T:Any> and(input : InputEntry<T>) = condition(input)

  fun <T:Any> outputs(vararg outputs: OutputEntry<T>) = apply {
    this.outputs.addAll(outputs.toList())
  }

  override fun build(): DmnRules {
    require(inputs.isNotEmpty()) { "Must provide inputs: $this" }
    require(outputs.isNotEmpty()) { "Must provide outputs: $this" }

    val rule = DmnRule(
      id = null,
      description = description,
      inputs = inputs,
      outputs = outputs
    )
    return DmnRules(rule)
  }

  override fun toString() = "DmnRulesBuilder(" +
    "description=$description, " +
    "inputs=$inputs, " +
    "outputs=$outputs" +
    ")"

}
