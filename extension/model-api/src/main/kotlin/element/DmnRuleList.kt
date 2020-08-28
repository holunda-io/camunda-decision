package io.holunda.decision.model.api.element

import io.holunda.decision.model.api.CamundaDecisionModelApi

data class DmnRuleList(
  private val list: List<DmnRule>
) : List<DmnRule> by list {
  companion object {
    val idGenerator = { CamundaDecisionModelApi.generateId(DmnRuleList::class) }
  }

  constructor(vararg rules: DmnRule) : this( rules.asList())

  val distinctInputs by lazy {
    this.flatMap { it.inputDefinitions }.distinct()//.sortedBy { it.key }
  }

  val distinctOutputs by lazy {
    this.flatMap { it.outputDefinitions }.distinct()//.sortedBy { it.key }
  }
}
