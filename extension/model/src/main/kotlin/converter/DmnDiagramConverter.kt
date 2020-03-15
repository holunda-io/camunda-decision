package io.holunda.decision.model.converter

import io.holunda.decision.model.element.DmnDiagram
import io.holunda.decision.model.ext.*
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.instance.ExtensionElements
import org.camunda.bpm.model.dmn.instance.InformationRequirement

/**
 * The DmnDiagramConverter converts DmnModelInstances to DmnDiagram representations and creates DmnModelInstances from DmnDiagram-
 */
object DmnDiagramConverter  {

  fun toModelInstance(dmnDiagram: DmnDiagram): DmnModelInstance = Dmn.createEmptyModel().apply {
    val definitions = definitions(dmnDiagram.name, dmnDiagram.id)
    dmnDiagram.decisionTables.forEach {
      DmnDecisionTableConverter.toModelInstance(definitions, it)
    }

    val layout = DmnDiagramLayout(dmnDiagram.decisionDefinitionKeys, dmnDiagram.requiredDecisions).layout().values

    for (box in layout) {
      definitions.findDecisionByKey(box.key)?.extensionElement(box)
    }

    dmnDiagram.requiredDecisions.forEach { target ->
      val t = requireNotNull(definitions.findDecisionByKey(target.key))

      target.value.forEach {sourceKey ->
        t.addChildElement(InformationRequirement::class).apply {
          requiredDecision = requireNotNull(definitions.findDecisionByKey(sourceKey))
        }
      }
    }
  }

  fun fromModelInstance(dmnModelInstance: DmnModelInstance) : DmnDiagram {
    TODO("not implemented")
  }


}
