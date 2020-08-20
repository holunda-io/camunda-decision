package io.holunda.decision.generator.builder

import io.holunda.decision.model.CamundaDecisionModel
import io.holunda.decision.model.api.DecisionDefinitionKey
import io.holunda.decision.model.api.Name
import io.holunda.decision.model.api.VersionTag
import io.holunda.decision.model.api.element.DmnDecisionTable
import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.instance.DecisionTable
import java.lang.IllegalArgumentException

/**
 * Builds a decision table based on a given existing reference. Allows modification of meta data.
 */
class DmnDecisionTableReferenceBuilder : AbstractDmnDecisionTableBuilder() {

  lateinit var decisionTableReference: DecisionTableReference

  @JvmOverloads
  fun reference(dmnModelInstance: DmnModelInstance,
                decisionDefinitionKey: DecisionDefinitionKey? = null) = reference(DecisionTableReference(dmnModelInstance, decisionDefinitionKey))


  fun reference(decisionTableReference: DecisionTableReference) = apply {
    this.decisionTableReference = decisionTableReference
  }

  fun decisionDefinitionKey(decisionDefinitionKey: DecisionDefinitionKey) = apply { this.decisionDefinitionKey = decisionDefinitionKey }

  fun requiredDecision(requiredDecision: DecisionDefinitionKey) = apply { this.requiredDecision = requiredDecision }

  fun name(name: Name) = apply { this.decisionName = name }

  fun versionTag(versionTag: VersionTag) = apply { this.versionTag = versionTag }

  override fun build(): DmnDecisionTable {
    require(this::decisionTableReference.isInitialized) { "reference must be set!" }

    val table = decisionTableReference.decisionTable

    return table!!.copy(
      name = decisionName ?: table.name,
      decisionDefinitionKey = decisionDefinitionKey ?: table.decisionDefinitionKey,
      versionTag = versionTag ?: table.versionTag
    )
  }

  /**
   * Referencing a table in a given modelInstance.
   */
  data class DecisionTableReference(
    val dmnModelInstance: DmnModelInstance,
    val decisionDefinitionKey: DecisionDefinitionKey? = null) {

    internal val decisionTable : DmnDecisionTable by lazy {
      val diagram = CamundaDecisionModel.readDmnModelInstance(dmnModelInstance)
      require(decisionDefinitionKey != null || diagram.decisionTables.size == 1) { "more than one table in diagram: provide definitionKey"}


      if (decisionDefinitionKey == null && diagram.decisionTables.size == 1) {
        diagram.decisionTables.first()
      } else {
        diagram.findDecistionTable(decisionDefinitionKey!!) ?: throw IllegalArgumentException("no table found with key: $decisionDefinitionKey")
      }
    }
  }
}
