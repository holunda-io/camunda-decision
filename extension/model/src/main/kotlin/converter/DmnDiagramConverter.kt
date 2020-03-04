package io.holunda.decision.model.converter

import io.holunda.decision.model.element.DmnDiagram
import io.holunda.decision.model.ext.*
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.instance.ExtensionElements
import org.camunda.bpm.model.dmn.instance.InformationRequirement

object DmnDiagramConverter  {

  fun toModelInstance(dmnDiagram: DmnDiagram): DmnModelInstance = Dmn.createEmptyModel().apply {
    val definitions = definitions(dmnDiagram.name, dmnDiagram.id)
    dmnDiagram.decisionTables.forEach {
      DmnDecisionTableConverter.toModelInstance(definitions, it)
    }

    var bounds = Bounds(100,200)
    dmnDiagram.decisionDefinitionKeys.forEach {
      definitions.findDecisionByKey(it)?.addCoordinates(bounds)
      bounds = bounds.copy(x= bounds.x + 260)
    }

    dmnDiagram.requiredDecisions.forEach { target ->
      val t = requireNotNull(definitions.findDecisionByKey(target.key))

      target.value.forEach {sourceKey ->
        val decision = definitions.findDecisionByKey(sourceKey)
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
