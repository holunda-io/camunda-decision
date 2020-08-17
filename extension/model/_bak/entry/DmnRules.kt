package io.holunda.decision.model.element

import io.holunda.decision.model.element.row.DmnRule

/**
 * Immutable representation of a complex rule. Contains an ordered list of rules, that, combined, express one business rule.
 * This is required, because in dmn, the logical AND is achieved by columns, the logical OR is achieved by rows.
 *
 * Example: `A & (B | C)` will create the table
 *
 * ```
 *    A    |   B   |   C
 *   true  |  true |  -
 *   true  |  -    |  false
 * ```
 */
data class DmnRules(val rules: List<DmnRule> = listOf()) : List<DmnRule> by rules {

  constructor(vararg rules: DmnRule) : this(rules.asList())

  val distinctInputs by lazy {
    this.flatMap { it.inputDefinitions }.distinct().sortedBy { it.key }
  }

  val  distinctOutputs by lazy {
    this.flatMap { it.outputDefinitions }.distinct().sortedBy { it.key }
  }

  operator fun plus(other: DmnRules) = DmnRules(rules + other.rules)

}
