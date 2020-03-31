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

  lateinit var outputs : List<OutputEntry>

  private val inputs = mutableListOf<InputEntry>()

  fun description(description:String) = apply {
    this.description = description
  }

  fun condition(input : InputEntry) = apply {
    this.inputs.add(input)
  }

  fun and(input : InputEntry) = apply {
    this.inputs.add(input)
  }

  fun outputs(vararg outputs:OutputEntry) = apply {
    this.outputs = outputs.toList()
  }

  override fun build(): DmnRules {
    val rule = DmnRule(
      id = null,
      description = description,
      inputs = inputs,
      outputs = outputs
    )
    return DmnRules(rule)
  }
}
