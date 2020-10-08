package io.holunda.decision.model.builder

import builder.DecisionTableDiagramReference
import builder.DecisionTableModelInstanceReference
import builder.DecisionTableReference
import io.holunda.decision.model.api.DecisionDefinitionKey
import io.holunda.decision.model.api.Name
import io.holunda.decision.model.api.VersionTag
import io.holunda.decision.model.api.element.DmnDecisionTable
import io.holunda.decision.model.api.element.DmnDiagram
import org.camunda.bpm.model.dmn.DmnModelInstance

/**
 * Builds a decision table based on a given existing reference. Allows modification of meta data.
 */
class DmnDecisionTableReferenceBuilder : AbstractDmnDecisionTableBuilder() {

  lateinit var decisionTableReference: DecisionTableReference

  /**
   * Set the [DecisionTableReference] via [DmnModelInstance].
   */
  @JvmOverloads
  fun reference(dmnModelInstance: DmnModelInstance,
                decisionDefinitionKey: DecisionDefinitionKey? = null): DmnDecisionTableReferenceBuilder = reference(
    DecisionTableModelInstanceReference(dmnModelInstance, decisionDefinitionKey)
  )

  /**
   * Set the [DecisionTableReference] via [DmnDiagram].
   */
  @JvmOverloads
  fun reference(dmnDiagram: DmnDiagram,
                decisionDefinitionKey: DecisionDefinitionKey? = null): DmnDecisionTableReferenceBuilder = reference(
    DecisionTableDiagramReference(dmnDiagram, decisionDefinitionKey)
  )

  /**
   * Set the [DecisionTableReference].
   */
  fun reference(decisionTableReference: DecisionTableReference): DmnDecisionTableReferenceBuilder = apply {
    this.decisionTableReference = decisionTableReference
  }

  /**
   * Alter decisionDefinitionKey of specified [DecisionTableReference].
   */
  fun decisionDefinitionKey(decisionDefinitionKey: DecisionDefinitionKey) = apply { this.decisionDefinitionKey = decisionDefinitionKey }
  
  fun requiredDecision(requiredDecision: DecisionDefinitionKey) = apply { this.requiredDecision = requiredDecision }

  fun name(name: Name) = apply { this.decisionName = name }

  fun versionTag(versionTag: VersionTag) = apply { this.versionTag = versionTag }

  override fun build(): DmnDecisionTable {
    require(this::decisionTableReference.isInitialized) { "reference must be set!" }

    val table = decisionTableReference.decisionTable

    return table.copy(
      name = decisionName ?: table.name,
      decisionDefinitionKey = decisionDefinitionKey ?: table.decisionDefinitionKey,
      versionTag = versionTag ?: table.versionTag
    )
  }
}
