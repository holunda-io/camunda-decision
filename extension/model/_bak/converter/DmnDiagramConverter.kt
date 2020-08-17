package io.holunda.decision.model.converter

import io.holunda.decision.model.DmnXml
import io.holunda.decision.model.element.DmnDiagram
import io.holunda.decision.model.xml.CamundaDecisionXml
import io.holunda.decision.model.xml.element.DefinitionsXml
import io.holunda.decision.model.xml.element.LayoutXml
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance

/**
 * The DmnDiagramConverter converts DmnModelInstances to DmnDiagram representations and creates DmnModelInstances from DmnDiagram-
 */
interface DmnDiagramConverter {

  /**
   * Creates a camunda DmnModelInstance from a given DmnDiagram.
   */
  fun toModelInstance(dmnDiagram: DmnDiagram): DmnModelInstance

  /**
   * Parses a camunda DmnModelInstance to diagram pojo.
   */
  fun fromModelInstance(dmnModelInstance: DmnModelInstance): DmnDiagram

  fun toXml(dmnDiagram: DmnDiagram) : DmnXml
}

object DmnDiagramConverterBean : DmnDiagramConverter {

  override fun toModelInstance(dmnDiagram: DmnDiagram): DmnModelInstance = Dmn.createEmptyModel().apply {
    val definitions = definitions(dmnDiagram.name, dmnDiagram.id)

    // first, create all decision (tables) to the model instance.
    dmnDiagram.decisionTables.forEach {
      DmnDecisionTableConverterBean.toModelInstance(definitions, it)
    }

//    dmnDiagram.informationRequirements.forEach {
//      definitions.findDecisionByKey(it.decisionTable)
//        ?.addChildElement(InformationRequirement::class, it.id).apply {
//          this!!.setAttributeValue("id", it.id)
//          this!!.requiredDecision = definitions.findDecisionByKey(it.requiredDecisionTable)
//        }
//    }

    // then: calculate the layout ...
    val layout = DmnDiagramLayout(dmnDiagram.decisionDefinitionKeys, dmnDiagram.informationRequirements).layout().values

    // ... add coordinates to all decisions, if is is part of a graph: render arrow
//    for (box in layout) {
//      definitions.findDecisionByKey(box.key)?.extensionElement(box)
//    }
//



    // ... and if the decision is part of a graph: add InformationRequirement.
//    dmnDiagram.requiredDecisions.forEach { target ->
//      val decision = requireNotNull(definitions.findDecisionByKey(target.key))
//
//      target.value.forEach { sourceKey ->
//        decision.addChildElement(InformationRequirement::class).apply {
//          requiredDecision = requireNotNull(definitions.findDecisionByKey(sourceKey))
//        }
//      }
//    }


  }

  override fun fromModelInstance(dmnModelInstance: DmnModelInstance): DmnDiagram = DmnDiagram(
    id = dmnModelInstance.definitions.id,
    name = dmnModelInstance.definitions.name,
    decisionTables = dmnModelInstance.definitions.findDecisions()
      .map { DmnDecisionTableConverterBean.fromModelInstance(it) }
  )

  override fun toXml(dmnDiagram: DmnDiagram): DmnXml {
    return CamundaDecisionXml.toXml(DefinitionsXml(
      id = "1", // TODO real value
      name = "NAME", // TODO real value
      decisions = listOf(), // TODO real value
      layout = LayoutXml(
        LayoutXml.DiagramLayoutXml(
          dmnShapes = listOf(), // TODO real value
          dmnEdges = listOf() // TODO real value
        )
      )
    ))
  }
}
