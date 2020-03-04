package io.holunda.decision.model.converter

import io.holunda.decision.model.element.DmnDecisionTable
import io.holunda.decision.model.element.DmnDiagram
import io.holunda.decision.model.ext.*
import org.camunda.bpm.model.dmn.instance.Decision
import org.camunda.bpm.model.dmn.instance.Definitions
import org.camunda.bpm.model.dmn.instance.Rule


object DmnDecisionTableConverter {

  fun toModelInstance(table:DmnDecisionTable) = DmnDiagramConverter.toModelInstance(DmnDiagram(table))

  fun toModelInstance(definitions: Definitions, table: DmnDecisionTable) : Decision {
    val decision = definitions.decision(key = table.key, name = table.name, versionTag = table.versionTag)
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

}
