package io.holunda.decision.model.builder

import io.holunda.decision.model.api.element.DmnRule
import io.holunda.decision.model.api.element.DmnRuleList
import io.holunda.decision.model.api.entry.OutputEntry
import io.holunda.decision.model.api.entry.toEntry
import io.holunda.decision.model.condition.jbool.JBoolExpressionConverter
import io.holunda.decision.model.condition.jbool.JBoolExpressionSupplier
import org.apache.commons.lang3.builder.Builder

/**
 * Though this will generally just create a list containing a single "and" connected rule,
 * it might become a list of rules when you use "or" with different values.
 */
class DmnBusinessRuleBuilder : Builder<DmnRuleList> {

  /**
   * An optional description of the business rule.
   */
  private var description: String? = null

  private lateinit var expression: JBoolExpressionSupplier
  private val result = mutableListOf<OutputEntry<*>>()

  fun description(description: String) = apply {
    this.description = description
  }

  fun condition(vararg expressions: JBoolExpressionSupplier) = apply {
    this.expression = JBoolExpressionSupplier.and(*expressions)
  }

  fun <T : Any> result(vararg outputs: OutputEntry<T>) = apply {
    this.result.addAll(outputs.toList())
  }

  override fun build(): DmnRuleList {
    require(this::expression.isInitialized) { "no input condition defined!" }
    require(result.isNotEmpty()) { "no results defined!" }

    val rules = JBoolExpressionConverter.convert(expression).mapIndexed { index, cols ->
      DmnRule(
        description = description?.let { "$it [$index]" },
        outputs = result,
        inputs = cols.map { it.definition.toEntry(it.condition.expression) }
      )
    }

    return DmnRuleList(rules)
  }

  override fun toString() = """DmnRulesBuilder(
    description=$description,
    inputs=${if (this::expression.isInitialized) expression.jbool() else "[]"},
    outputs=$result
    )"""

}
