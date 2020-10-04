package io.holunda.decision.model.api.element

import io.holunda.decision.model.api.*
import io.holunda.decision.model.api.definition.InputDefinition


/**
 * The diagram, corresponds to the dmn-xml resp. the DmnModelInstance.
 * Contains at least one DmnDecisionTable. If it contains more than one table, these must form a graph.
 */
data class DmnDiagram(
  val id: DiagramId = idGenerator(),
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
    require(decisionTables.isNotEmpty()) { "diagram must contain at least one decisionTable." }
  }

  fun findDecisionTable(decisionDefinitionKey: DecisionDefinitionKey): DmnDecisionTable? = decisionTables.find { it.decisionDefinitionKey == decisionDefinitionKey }

  val resourceName = "diagram-${name.toLowerCase().replace("\\s".toRegex(), "-")}.dmn"

  val decisionDefinitionKeys = decisionTables.map { it.decisionDefinitionKey }.toSet()

  val informationRequirements: Set<DmnDecisionTable.InformationRequirement> = decisionTables
    .map { it.informationRequirement }
    .filterNotNull()
    .toSet()

  // Set containing all definitionKeys that are required by other tables
  private val allRequiredTables = informationRequirements.map { it.requiredDecisionTable }.toSet()

  /**
   * The effective resultTable, either the only table in the diabram or the last in a chain of dependant tables.
   */
  val resultTable: DmnDecisionTable by lazy { decisionTables.single { !allRequiredTables.contains(it.decisionDefinitionKey) } }

  /**
   * All inputs required to evaluate the whole diagram.
   *
   * Example, 2 tables: table 1 requires a,b and has result c. table 2 requires c (from t1) and d and returns e.  to evaluate the
   * diagram, the user has to pass values for a,b and d.
   */
  val requiredInputs : Set<InputDefinition<*>> by lazy {
    val definitions = mutableSetOf<InputDefinition<*>>()

    fun processTable(decisionDefinitionKey: DecisionDefinitionKey) {
      val decisionTable = findDecisionTable(decisionDefinitionKey)?:throw IllegalStateException("table '$decisionDefinitionKey' not exists in diagram '$resourceName'")

      definitions.addAll(decisionTable.header.inputs)
      decisionTable.header.outputs.distinct().map { it.variableFactory }.forEach {output ->
        definitions.removeIf { it.variableFactory == output  }
      }

      decisionTable.requiredDecisions.forEach { processTable(it) }
    }

    processTable(resultTable.decisionDefinitionKey)

    //resultTable.requiredDecisions.forEach { processTable(it) }
    definitions.toSet()
  }

}
