package io.holunda.decision.model.api.element

data class DmnRuleList(private val list: List<DmnRule>) : List<DmnRule> by list {

  constructor(vararg rules: DmnRule) : this(rules.asList())

  val distinctInputs by lazy {
    this.flatMap { it.inputDefinitions }.distinct()//.sortedBy { it.key }
  }

  val distinctOutputs by lazy {
    this.flatMap { it.outputDefinitions }.distinct()//.sortedBy { it.key }
  }
}
