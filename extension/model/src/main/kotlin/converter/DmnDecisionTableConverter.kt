package io.holunda.decision.model.converter

import io.holunda.decision.model.element.DmnDecisionTable
import io.holunda.decision.model.element.DmnDiagram
import io.holunda.decision.model.element.DmnRule
import io.holunda.decision.model.element.DmnRules
import io.holunda.decision.model.ext.*
import org.camunda.bpm.model.dmn.instance.Decision
import org.camunda.bpm.model.dmn.instance.Definitions
import org.camunda.bpm.model.dmn.instance.Rule


object DmnDecisionTableConverter {

  fun toModelInstance(table: DmnDecisionTable) = DmnDiagramConverter.toModelInstance(DmnDiagram(table))

  fun toModelInstance(definitions: Definitions, table: DmnDecisionTable): Decision {
    val decision = definitions.decision(
      key = table.decisionDefinitionKey,
      name = table.name,
      versionTag = table.versionTag
    )

    val decisionTable = decision.decisionTable(table.hitPolicy) // TODO: aggregation, maybe Sealed class for DmnHitPolicy?

    table.header.inputs.forEach { decisionTable.input(it) }
    table.header.outputs.forEach { decisionTable.output(it) }

    for (dmnRule in table.rules) {
      val rule = decisionTable.addChildElement(Rule::class)

      dmnRule.inputs.map { it.expression }.forEach { rule.inputEntry(it) }
      dmnRule.outputs.map { it.expression }.forEach { rule.outputEntry(it) }

      rule.description(dmnRule.description)
    }

    return decision
  }

  fun fromModelInstance(decision: Decision): DmnDecisionTable {
    val decisionTable = decision.getDecisionTable()

    val header = decisionTable.toHeader()

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
      hitPolicy = decisionTable.hitPolicy,
      header = header,
      rules = dmnRules,
      requiredDecisions = decision.getRequiredDecisions()
    )
  }
}
