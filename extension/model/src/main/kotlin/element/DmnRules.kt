package io.holunda.decision.model.element

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
