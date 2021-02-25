package builder

import io.holunda.decision.model.CamundaDecisionModel
import io.holunda.decision.model.api.DecisionDefinitionKey
import io.holunda.decision.model.api.element.DmnDecisionTable
import io.holunda.decision.model.api.element.DmnDiagram
import org.camunda.bpm.model.dmn.DmnModelInstance

/**
 * Reference to a decision table for further use in diagram.
 */
interface DecisionTableReference {
  val decisionTable: DmnDecisionTable
}

/**
 * Referencing a table in a given modelInstance.
 */
data class DecisionTableModelInstanceReference(
  val dmnModelInstance: DmnModelInstance,
  val decisionDefinitionKey: DecisionDefinitionKey? = null) : DecisionTableReference {

  override val decisionTable: DmnDecisionTable by lazy {
    val diagram = CamundaDecisionModel.readDiagram(dmnModelInstance)
    DecisionTableDiagramReference(diagram, decisionDefinitionKey).decisionTable
  }
}

/**
 * Referencing a table in a given diagram.
 */
data class DecisionTableDiagramReference(
  val diagram: DmnDiagram,
  val decisionDefinitionKey: DecisionDefinitionKey? = null) : DecisionTableReference {

  override val decisionTable: DmnDecisionTable by lazy {
    require(decisionDefinitionKey != null || diagram.decisionTables.size == 1) { "more than one table in diagram: provide definitionKey" }

    if (decisionDefinitionKey == null && diagram.decisionTables.size == 1) {
      diagram.decisionTables.first()
    } else {
      diagram.findDecisionTable(decisionDefinitionKey!!)
        ?: throw IllegalArgumentException("no table found with key: $decisionDefinitionKey")
    }
  }
}
