package io.holunda.decision.model.io

import io.holunda.decision.model.*
import io.holunda.decision.model.ext.*
import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.instance.Decision
import org.camunda.bpm.model.dmn.instance.Rule

object DmnReader {

  fun readDecisionTable(modelInstance: DmnModelInstance, decisionDefinitionKey: String? = null): DmnDecisionTable = readDecisionTable(modelInstance.getModelElementByType(Decision::class, decisionDefinitionKey))

  fun readDecisionTable(decision: Decision): DmnDecisionTable {

    val decisionTable = decision.getDecision()

    val header = decisionTable.toHeader()

    val dmnRules = DmnRules(decisionTable.rules.map { rule ->
      var dmnRule = DmnRule(description = rule.description?.textContent?:"-")
      rule.inputEntries.forEachIndexed { i, e ->
        dmnRule = dmnRule.addInput(header.inputs.get(i), e.textContent)
      }
      rule.outputEntries.forEachIndexed { i, e ->
        dmnRule = dmnRule.addOutput(header.outputs.get(i), e.textContent)
      }
      dmnRule
    })

    val foo = InputDefinition.stringInput("foo")
    val bar = InputDefinition.booleanInput("bar")
    val res = OutputDefinition.stringOutput("result")



    return DmnDecisionTable(
      key = decision.id,
      name = decision.name,
      versionTag = decision.versionTag,
      hitPolicy = decisionTable.hitPolicy,
      header = header,
      rules = dmnRules
    )

  }
}
