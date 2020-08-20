package io.holunda.decision.model.converter

import io.holunda.decision.model.api.CamundaDecisionModelApi
import io.holunda.decision.model.api.DmnDiagramConverter
import io.holunda.decision.model.api.DmnXml
import io.holunda.decision.model.api.element.DmnDiagram
import io.holunda.decision.model.layout.DmnDiagramLayoutWorker
import io.holunda.decision.model.xml.CamundaDecisionXml
import io.holunda.decision.model.xml.element.DefinitionsXml
import io.holunda.decision.model.xml.element.LayoutXml

class JacksonDiagramConverter : DmnDiagramConverter {

  override fun toXml(diagram: DmnDiagram): DmnXml {

    val layout = DmnDiagramLayoutWorker(diagram.decisionDefinitionKeys, diagram.informationRequirements).layout()

    return CamundaDecisionXml.toXml(DefinitionsXml(
      id = diagram.id,
      name = diagram.name,
      decisions = diagram.decisionTables.map { JacksonDecisionTableConverter.convert(it) },
      layout = LayoutXml(LayoutXml.DiagramLayoutXml(
        dmnShapes = layout.decisionTableLayouts.map {
          LayoutXml.DiagramLayoutXml.DmnShapeXml(it.decisionDefinitionKey, LayoutXml.DiagramLayoutXml.DmnShapeXml.BoundsXml(it.box.height, it.box.width, it.box.x, it.box.y))
        },
        dmnEdges = layout.informationRequirementLayouts.map {
          LayoutXml.DiagramLayoutXml.DmnEdgeXml(
            dmnElementRef = it.informationRequirementId,
            waypoints = it.waypoints.map {
              LayoutXml.DiagramLayoutXml.DmnEdgeXml.WaypointXml(it.x,it.y)
            }
          )
        }
      ))
    ))
  }

  override fun fromXml(dmn: DmnXml): DmnDiagram {
    val definitions = CamundaDecisionXml.fromXml(dmn)

    return DmnDiagram(
      id = definitions.id,
      name = definitions.name,
      decisionTables = definitions.decisions.map { JacksonDecisionTableConverter.convert(it) }
    )
  }

}
