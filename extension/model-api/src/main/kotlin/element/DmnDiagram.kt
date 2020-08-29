package io.holunda.decision.model.api.element

import io.holunda.decision.model.api.CamundaDecisionModelApi
import io.holunda.decision.model.api.DecisionDefinitionKey
import io.holunda.decision.model.api.Id
import io.holunda.decision.model.api.Name


/**
 * The diagram, corresponds to the dmn-xml resp. the DmnModelInstance.
 * Contains at least one DmnDecisionTable. If it contains more than one table, these must form a graph.
 */
data class DmnDiagram(
  val id: Id = idGenerator(),
  val name: Name,
  val decisionTables: List<DmnDecisionTable>
) {
  companion object {
    val idGenerator = { CamundaDecisionModelApi.generateId(DmnDiagram::class) }
  }

  constructor(decisionTable: DmnDecisionTable) : this(
    id = "${decisionTable.decisionDefinitionKey}_diagram",
    name = "${decisionTable.name} (Diagram)",
    decisionTables = listOf(decisionTable)
  )

  constructor(id: Id, name: Name, vararg decisionTables: DmnDecisionTable) : this(id, name, decisionTables.asList())

  init {
      require(decisionTables.isNotEmpty()) { "diagram must contain at least one decisionTable."}
  }

  fun findDecisionTable(decisionDefinitionKey: DecisionDefinitionKey) : DmnDecisionTable? = decisionTables.find { it.decisionDefinitionKey == decisionDefinitionKey }

  val resourceName = "diagram-${name.toLowerCase().replace("\\s".toRegex(), "-")}.dmn"

  val decisionDefinitionKeys = decisionTables.map { it.decisionDefinitionKey }.toSet()

  val informationRequirements: Set<DmnDecisionTable.InformationRequirement> = decisionTables
    .map { it.informationRequirement }
    .filterNotNull()
    .toSet()

}
