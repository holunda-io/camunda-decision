package io.holunda.decision.model

import io.holunda.decision.model.api.DmnXml
import io.holunda.decision.model.api.element.DmnDecisionTable
import io.holunda.decision.model.api.element.DmnDiagram
import io.holunda.decision.model.ascii.DmnWriter
import io.holunda.decision.model.jackson.converter.JacksonDiagramConverter
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance

/**
 * Camunda Decision Model - Model DTO for DMN.
 */
object CamundaDecisionModel {

  fun createXml(dmnDiagram: DmnDiagram) = JacksonDiagramConverter.toXml(dmnDiagram)

  fun readDiagram(modelInstance: DmnModelInstance):DmnDiagram = readDiagram(Dmn.convertToString(modelInstance))
  fun readDiagram(xml: DmnXml) :DmnDiagram = JacksonDiagramConverter.fromXml(xml)

  /**
   * Reads a DMN Model instance and converts it to diagram DTO.
   *
   * @see DmnDiagramConverterBean.fromModelInstance
   * @param dmnModelInstance: camunda model instance
   * @return diagram instance containing all dmn data
   */
//  @JvmStatic
//  fun readDiagram(dmnModelInstance: DmnModelInstance) = DmnDiagramConverterBean.fromModelInstance(dmnModelInstance)

  /**
   * Reads a given decision table from the DMN Model Instance.
   *
   * @param dmnModelInstance: camunda model instance
   * @param decisionDefinitionKey: select which table to read (optional)
   * @see readDiagram
   * @return decisionTable containing all dmn data
   */
//  @JvmStatic
//  @JvmOverloads
//  fun readDecisionTable(dmnModelInstance: DmnModelInstance, decisionDefinitionKey: DecisionDefinitionKey? = null): DmnDecisionTable {
//    val diagram = readDiagram(dmnModelInstance)
//
//    return requireNotNull(
//      if (decisionDefinitionKey == null) diagram.decisionTables.first()
//      else diagram.decisionTables.find { it.decisionDefinitionKey == decisionDefinitionKey }
//    ) { "no table found" }
//  }

  /**
   * Creates a camunda model instance based on data provided by diagram.
   *
   * @param diagram the dmn data
   * @return a camunda model instance
   */
//  @JvmStatic
//  fun createModelInstance(diagram: DmnDiagram) = DmnDiagramConverterBean.toModelInstance(diagram)

  /**
   * Creates a camunda model instance based on data provided by table.
   *
   * @param table: the decision table data
   * @return a camunda model instance
   */
//  @JvmStatic
//  fun createModelInstance(table: DmnDecisionTable) = DmnDecisionTableConverterBean.toModelInstance(table)

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
