package io.holunda.decision.model.converter

import io.holunda.decision.model.element.DmnDiagram
import io.holunda.decision.model.ext.addChildElement
import io.holunda.decision.model.ext.definitions
import io.holunda.decision.model.ext.extensionElement
import io.holunda.decision.model.ext.findDecisionByKey
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.instance.InformationRequirement

/**
 * The DmnDiagramConverter converts DmnModelInstances to DmnDiagram representations and creates DmnModelInstances from DmnDiagram-
 */
object DmnDiagramConverter  {

  /**
   * Creates a camunda DmnModelInstance from a given DmnDiagram.
   */
  fun toModelInstance(dmnDiagram: DmnDiagram): DmnModelInstance = Dmn.createEmptyModel().apply {
    val definitions = definitions(dmnDiagram.name, dmnDiagram.id)

    // first, create all decision (tables) to the model instance.
    dmnDiagram.decisionTables.forEach {
      DmnDecisionTableConverter.toModelInstance(definitions, it)
    }

    // then: calculate the layout ...
    val layout = DmnDiagramLayout(dmnDiagram.decisionDefinitionKeys, dmnDiagram.requiredDecisions).layout().values

    // ... add coordinates to all decisions, if is is part of a graph: render arrow
    for (box in layout) {
      definitions.findDecisionByKey(box.key)?.extensionElement(box)
    }

    // ... and if the decision is part of a graph: add InformationRequirement.
    dmnDiagram.requiredDecisions.forEach { target ->
      val decision = requireNotNull(definitions.findDecisionByKey(target.key))

      target.value.forEach {sourceKey ->
        decision.addChildElement(InformationRequirement::class).apply {
          requiredDecision = requireNotNull(definitions.findDecisionByKey(sourceKey))
        }
      }
    }
  }

  fun fromModelInstance(dmnModelInstance: DmnModelInstance) : DmnDiagram {
    TODO("not implemented")
  }


}