package io.holunda.decision.model.element

import io.holunda.decision.model.Id
import io.holunda.decision.model.Name

data class DmnDiagram(
  val id: Id,
  val name: Name,
  val decisionTables: List<DmnDecisionTable>
) {

  constructor(id: Id, name: Name, vararg decisionTables: DmnDecisionTable) : this(id, name, decisionTables.asList())

  val decisionDefinitionKeys = decisionTables.map { it.key }.toSet()

  val requiredDecisions = decisionTables.map { it.key to it.requiredDecisions }
    .filter { it.second.isNotEmpty() }
    .toMap()
}
