package io.holunda.decision.generator

import io.holunda.decision.generator.CamundaDecisionGenerator.ModelExtension.decision
import io.holunda.decision.generator.CamundaDecisionGenerator.ModelExtension.decisionTable
import io.holunda.decision.generator.CamundaDecisionGenerator.ModelExtension.definitions
import io.holunda.decision.generator.CamundaDecisionGenerator.ModelExtension.input
import io.holunda.decision.generator.CamundaDecisionGenerator.ModelExtension.output
import io.holunda.decision.model.DmnRule
import io.holunda.decision.model.DmnRule.Companion.distinctInputs
import io.holunda.decision.model.DmnRule.Companion.distinctOutputs
import org.apache.commons.lang3.builder.Builder
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.HitPolicy

class DmnModelBuilder(private val name: String) : Builder<DmnModelInstance> {

  private val decisionTables = mutableListOf<DecisionTableBuilder>()

  fun decisionTable(key: String) = DecisionTableBuilder(this, key).apply {
    decisionTables.add(this)
  }

  override fun toString(): String = "DmnModelBuilder(name='$name', decisionTables=$decisionTables)"

  class DecisionTableBuilder(
    private val dmnModelBuilder: DmnModelBuilder,
    val key: String
  ) {

    var name: String = key
    var versionTag: String? = null
    var hitPolicy : HitPolicy = HitPolicy.UNIQUE
    val dmnRules : MutableList<DmnRule> = mutableListOf()

    fun name(value: String) = apply {
      name = value
    }

    fun versionTag(value: String) = apply { versionTag = value }

    fun hitPolicy(value:HitPolicy ) = apply { hitPolicy = value }

    fun rule(dmnRule: DmnRule) = apply {
      dmnRules.add(dmnRule)
    }

    fun done() = dmnModelBuilder
    override fun toString(): String {
      return "DecisionTableBuilder(key='$key', name='$name', versionTag=$versionTag, hitPolicy=$hitPolicy, dmnRules=$dmnRules)"
    }


  }


  override fun build(): DmnModelInstance {
    val dmn = Dmn.createEmptyModel()
    val definitions = dmn.definitions(name)

    for (builder in decisionTables) {
      val decisionTable = definitions
        .decision(builder.key, builder.name, builder.versionTag)
        .decisionTable(builder.hitPolicy)

      builder.dmnRules.distinctInputs().forEach { decisionTable.input(it) }
      builder.dmnRules.distinctOutputs().forEach { decisionTable.output(it) }
    }


    return dmn
  }
}
