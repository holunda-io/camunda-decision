package io.holunda.decision.model.builder

import io.holunda.decision.model.api.element.DmnRule
import io.holunda.decision.model.api.element.DmnRuleList
import io.holunda.decision.model.api.element.OutputEntry
import io.holunda.decision.model.expression.FeelExpression
import org.apache.commons.lang3.builder.Builder

/**
 * Though this will generally just create a list containing a single "and" connected rule,
 * it might become a list of rules when you use "or" with different values.
 */
class DmnBusinessRuleBuilder : Builder<DmnRuleList> {

  private var description: String? = null
  private val inputs = mutableListOf<FeelExpression<*,*>>()
  private val outputs = mutableListOf<OutputEntry<*>>()

  fun description(description: String) = apply {
    this.description = description
  }

  fun <T : Any, SELF: FeelExpression<T, SELF>> condition(condition: FeelExpression<T, SELF>) = apply {
    this.inputs.add(condition)
  }

  fun <T : Any, SELF: FeelExpression<T, SELF>> and(condition: FeelExpression<T, SELF>) = condition(condition)

  fun <T : Any> outputs(vararg outputs: OutputEntry<T>) = apply {
    this.outputs.addAll(outputs.toList())
  }

  override fun build(): DmnRuleList {
    require(inputs.isNotEmpty()) { "Must provide inputs: $this" }
    require(outputs.isNotEmpty()) { "Must provide outputs: $this" }

    val rule = DmnRule(
      description = description,
      inputs = inputs.map { it.inputEntry },
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
