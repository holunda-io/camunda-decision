package io.holunda.decision.model

import io.holunda.decision.model.converter.DmnDecisionTableConverter
import io.holunda.decision.model.converter.DmnDiagramConverter
import io.holunda.decision.model.element.DmnDecisionTable
import io.holunda.decision.model.element.DmnDiagram
import io.holunda.decision.model.io.DmnWriter
import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.impl.DmnModelConstants


object CamundaDecisionModel {

  fun readDiagram(dmnModelInstance: DmnModelInstance) = DmnDiagramConverter.fromModelInstance(dmnModelInstance)

  fun readDecisionTable(dmnModelInstance: DmnModelInstance, key: String? = null): DmnDecisionTable {
    val diagram = readDiagram(dmnModelInstance)

    return requireNotNull(
      if (key == null) diagram.decisionTables.first()
      else diagram.decisionTables.find { it.key == key }
    ) { "no table found" }
  }

  fun createModelInstance(diagram: DmnDiagram) = DmnDiagramConverter.toModelInstance(diagram)

  fun createModelInstance(table: DmnDecisionTable) = DmnDecisionTableConverter.toModelInstance(table)

  fun render(diagram: DmnDiagram) = DmnWriter.render(diagram)

  fun render(table: DmnDecisionTable) = DmnWriter.render(table)
}



typealias Id = String
typealias Name = String
typealias DecisionDefinitionKey = String
typealias VersionTag = String

const val BIODI_NS = "http://bpmn.io/schema/dmn/biodi/1.0"
const val CAMUNDA_NS = DmnModelConstants.CAMUNDA_NS

