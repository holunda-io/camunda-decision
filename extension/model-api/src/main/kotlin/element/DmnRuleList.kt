package io.holunda.decision.model.api.element

import io.holunda.decision.model.api.CamundaDecisionModelApi

/**
 * Wraps a list of DmnRules and provides convenience access methods.
 */
data class DmnRuleList(
  private val list: List<DmnRule>
) : List<DmnRule> by list {
  companion object {
    val idGenerator = { CamundaDecisionModelApi.generateId(DmnRuleList::class) }
  }

  constructor(vararg rules: DmnRule) : this( rules.asList())

  /**
   * Distinct input definitions of all rules in this list, sorted by key.
   * Used to build the correct column design.
   */
  val distinctInputs by lazy {
    this.flatMap { it.inputDefinitions }.distinct().sortedBy { it.key }
  }


  /**
   * Distinct output definitions of all rules in this list, sorted by key.
   * Used to build the correct column design.
   */
  val distinctOutputs by lazy {
    this.flatMap { it.outputDefinitions }.distinct().sortedBy { it.key }
  }
}
