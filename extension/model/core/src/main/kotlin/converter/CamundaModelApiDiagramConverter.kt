package io.holunda.decision.model.converter

import io.holunda.decision.model.api.DmnDiagramConverter
import io.holunda.decision.model.api.DmnXml
import io.holunda.decision.model.api.element.DmnDecisionTable
import io.holunda.decision.model.api.element.DmnDiagram
import io.holunda.decision.model.api.layout.DmnDiagramLayoutWorker
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.instance.*



val diagramToModelInstance: (DmnDiagram) -> DmnModelInstance = { diagram ->
  val dmn = createEmptyDmnModelInstance(diagram.name)
  val layoutWorker = DmnDiagramLayoutWorker(diagram)

  diagram.decisionTables.forEach { dmnDecisionTable ->
    val (hitPolicy, aggregator) = HitPolicyConverter.convert(dmnDecisionTable.hitPolicy)

    val decision = dmn.newInstance(Decision::class, dmnDecisionTable.decisionDefinitionKey).apply {
      dmn.definitions.addChildElement(this)
      name = dmnDecisionTable.name
      dmnDecisionTable.versionTag?.also { this.versionTag = it }
    }
    val decisionTable = dmn.newInstance(DecisionTable::class).apply {
      decision.addChildElement(this)
      this.hitPolicy = hitPolicy
      aggregator?.also { this.aggregation = it }

      dmnDecisionTable.header.inputs.forEach {
        this.createInput(dmn, it)
      }

      dmnDecisionTable.header.outputs.forEach {
        this.createOutput(dmn, it)
      }

      dmnDecisionTable.rules.forEach {
        this.createRule(dmn, it)
      }
    }
  }
  diagram.decisionTables.filter { it.requiredDecisions.isNotEmpty() }.forEach { dmnDecisionTable ->
    val targetDecision = dmn.getModelElementById<Decision>(dmnDecisionTable.decisionDefinitionKey)

    dmnDecisionTable.requiredDecisions.forEach {
      targetDecision.createInformationRequirement(dmn, it)
    }
  }



  dmn
}


class CamundaModelApiDiagramConverter : DmnDiagramConverter {


  fun toModelInstance(diagram: DmnDiagram): DmnModelInstance {
    val modelInstance = createEmptyDmnModelInstance(diagram.name)

    return modelInstance
  }

  override fun toXml(diagram: DmnDiagram): DmnXml = Dmn.convertToString(toModelInstance(diagram))

  override fun fromXml(dmn: DmnXml): DmnDiagram {
    TODO("Not yet implemented")
  }
}
