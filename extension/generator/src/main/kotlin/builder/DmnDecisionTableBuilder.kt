package io.holunda.decision.generator.builder

import io.holunda.decision.model.CamundaDecisionModel
import io.holunda.decision.model.DecisionDefinitionKey
import io.holunda.decision.model.Name
import io.holunda.decision.model.VersionTag
import io.holunda.decision.model.element.DmnDecisionTable
import org.apache.commons.lang3.builder.Builder
import org.camunda.bpm.model.dmn.DmnModelInstance

class DmnDecisionTableBuilder : Builder<DmnDecisionTable> {

  private var decisionDefinitionKey: DecisionDefinitionKey? = null
  private var decisionName: Name? = null
  private var decisionTableReference: DecisionTableReference? = null
  private var versionTag: VersionTag? = null

  @JvmOverloads
  fun reference(dmnModelInstance: DmnModelInstance,
                decisionDefinitionKey: DecisionDefinitionKey? = null) = apply {
    this.decisionTableReference = DecisionTableReference(dmnModelInstance, decisionDefinitionKey)
  }

  fun decisionDefinitionKey(decisionDefinitionKey: DecisionDefinitionKey) = apply { this.decisionDefinitionKey = decisionDefinitionKey }

  fun name(name: Name) = apply { this.decisionName = name }

  fun versionTag(versionTag: VersionTag) = apply { this.versionTag = versionTag }

  override fun build(): DmnDecisionTable {
    if (decisionTableReference != null) {
      val table = decisionTableReference!!.decisionTable

      return table.copy(
        name = decisionName ?: table.name,
        decisionDefinitionKey = decisionDefinitionKey ?: table.decisionDefinitionKey,
        versionTag = versionTag ?: table.versionTag
      )
    }
    TODO()
  }

  /**
   * Referencing a table in a given modelInstance.
   */
  data class DecisionTableReference(val dmnModelInstance: DmnModelInstance, val decisionDefinitionKey: DecisionDefinitionKey? = null) {

    internal val decisionTable by lazy {
      CamundaDecisionModel.readDecisionTable(dmnModelInstance, decisionDefinitionKey)
    }

  }
}
