package io.holunda.decision.model

import io.holunda.decision.model.converter.DmnDecisionTableConverter
import io.holunda.decision.model.converter.DmnDiagramConverter
import io.holunda.decision.model.element.DmnDecisionTable
import io.holunda.decision.model.element.DmnDiagram
import io.holunda.decision.model.io.DmnWriter
import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.impl.DmnModelConstants

/**
 * Camunda Decision Model - Model DTO for DMN.
 */
object CamundaDecisionModel {

  /**
   * Reads a DMN Model instance and converts it to diagram DTO.
   *
   * @see DmnDiagramConverter.fromModelInstance
   * @param dmnModelInstance: camunda model instance
   * @return diagram instance containing all dmn data
   */
  @JvmStatic
  fun readDiagram(dmnModelInstance: DmnModelInstance) = DmnDiagramConverter.fromModelInstance(dmnModelInstance)

  /**
   * Reads a given decision table from the DMN Model Instance.
   *
   * @param dmnModelInstance: camunda model instance
   * @param decisionDefinitionKey: select which table to read (optional)
   * @see readDiagram
   * @return decisionTable containing all dmn data
   */
  @JvmStatic
  @JvmOverloads
  fun readDecisionTable(dmnModelInstance: DmnModelInstance, decisionDefinitionKey: DecisionDefinitionKey? = null): DmnDecisionTable {
    val diagram = readDiagram(dmnModelInstance)

    return requireNotNull(
      if (decisionDefinitionKey == null) diagram.decisionTables.first()
      else diagram.decisionTables.find { it.decisionDefinitionKey == decisionDefinitionKey }
    ) { "no table found" }
  }

  /**
   * Creates a camunda model instance based on data provided by diagram.
   *
   * @param diagram the dmn data
   * @return a camunda model instance
   */
  @JvmStatic
  fun createModelInstance(diagram: DmnDiagram) = DmnDiagramConverter.toModelInstance(diagram)

  /**
   * Creates a camunda model instance based on data provided by table.
   *
   * @param table: the decision table data
   * @return a camunda model instance
   */
  @JvmStatic
  fun createModelInstance(table: DmnDecisionTable) = DmnDecisionTableConverter.toModelInstance(table)

  /**
   * Renders a given decision table to ASCII.
   * Useful for logging/testing.
   *
   * @param table the table to render
   * @return ascii text displaying the table
   */
  @JvmStatic
  fun render(table: DmnDecisionTable) = DmnWriter.render(table)

  /**
   * Renders all decision tables in diagram to ASCII.
   * @see render
   *
   * @param diagram the diagram to render
   * @return ascii text displaying all tables.
   */
  @JvmStatic
  fun render(diagram: DmnDiagram) = DmnWriter.render(diagram)


  object Meta {
    val version = CamundaDecisionModel::class.java.`package`.implementationVersion ?: "n/a"

    @Suppress("SpellCheckingInspection")
    @JvmStatic
    val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
  }
}

typealias Id = String
typealias Name = String
typealias DecisionDefinitionKey = String
typealias VersionTag = String

const val BIODI_NS = "http://bpmn.io/schema/dmn/biodi/1.0"
const val CAMUNDA_NS = DmnModelConstants.CAMUNDA_NS
