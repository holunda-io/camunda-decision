package io.holunda.decision.model.converter

import io.holunda.decision.model.element.*
import io.holunda.decision.model.element.row.DmnRule
import io.holunda.decision.model.ext.*
import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.instance.Decision
import org.camunda.bpm.model.dmn.instance.Definitions
import org.camunda.bpm.model.dmn.instance.Rule

interface DmnDecisionTableConverter {
  fun toModelInstance(table: DmnDecisionTable): DmnModelInstance
  fun toModelInstance(definitions: Definitions, table: DmnDecisionTable): Decision
  fun fromModelInstance(decision: Decision): DmnDecisionTable
}

object DmnDecisionTableConverterBean : DmnDecisionTableConverter{

  override fun toModelInstance(table: DmnDecisionTable) = DmnDiagramConverterBean.toModelInstance(DmnDiagram(table))

  override fun toModelInstance(definitions: Definitions, table: DmnDecisionTable): Decision {
    val decision = definitions.decision(
      key = table.decisionDefinitionKey,
      name = table.name,
      versionTag = table.versionTag
    )

    val decisionTable = decision.decisionTable(table.hitPolicy)

    table.header.inputs.forEach { decisionTable.input(it) }
    table.header.outputs.forEach { decisionTable.output(it) }


    for (dmnRule in table.rules) {
      val rule = decisionTable.addChildElement(Rule::class)

      val inputMap = dmnRule.inputs.associateBy { it.definition }
      table.header.inputs.map { inputMap[it]!! }.map { it.expression }.forEach{ rule.inputEntry(it) }

      val outputMap = dmnRule.outputs.associateBy { it.definition }
      table.header.outputs.map { outputMap[it]!! }.map { it.expression }.forEach { rule.outputEntry(it) }

      rule.description(dmnRule.description)
    }

    return decision
  }

  override fun fromModelInstance(decision: Decision): DmnDecisionTable {
    val decisionTable = decision.getDecisionTable()

    val header = decisionTable.header

    val dmnRules = DmnRules(decisionTable.rules.map { rule ->
      var dmnRule = DmnRule(description = rule.description?.textContent ?: "-")
      rule.inputEntries.forEachIndexed { i, e ->
        dmnRule = dmnRule.addInput(header.inputs.get(i), e.textContent)
      }
      rule.outputEntries.forEachIndexed { i, e ->
        dmnRule = dmnRule.addOutput(header.outputs.get(i), e.textContent)
      }
      dmnRule
    })

    return DmnDecisionTable(
      decisionDefinitionKey = decision.id,
      name = decision.name,
      versionTag = decision.versionTag,
      hitPolicy = decisionTable.dmnHitPolicy,
      header = header,
      rules = dmnRules,
      requiredDecisions = decision.getRequiredDecisions()
    )
  }
}
