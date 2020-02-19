package io.holunda.decision.generator

import io.holunda.decision.generator.CamundaDecisionGenerator.generateId
import io.holunda.decision.model.DmnRule
import io.holunda.decision.model.DmnRule.Companion.distinctOutputs
import io.holunda.decision.model.DmnRule.Companion.distinctInputs
import io.holunda.decision.model.InputDefinition
import io.holunda.decision.model.OutputDefinition
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.HitPolicy
import org.camunda.bpm.model.dmn.impl.DmnModelConstants
import org.camunda.bpm.model.dmn.instance.*
import kotlin.reflect.KClass


class DmnModel(val dmn: DmnModelInstance, name: String) {
  companion object {
    fun create(name: String) = DmnModel(Dmn.createEmptyModel(), name)

  }

  val definitions = newInstance(Definitions::class).apply {
    this.namespace = DmnModelConstants.CAMUNDA_NS
    this.name = name
    dmn.definitions = this
  }

  fun convertToString() = Dmn.convertToString(dmn)!!

  private fun <T : DmnModelElementInstance> newInstance(elementClass: KClass<T>): T = newInstance(elementClass, generateId(elementClass))

  private fun input(column: InputDefinition): Input = newInstance(Input::class).apply {
    this.label = column.label
    val inputExpression: InputExpression = newInstance(InputExpression::class)
    inputExpression.text = newText(column.key)
    inputExpression.typeRef = column.type.typeRef
    this.inputExpression = inputExpression


  }

  private fun output(column: OutputDefinition) = newInstance(Output::class).apply {
    this.name = column.key
    this.label = column.label
    this.typeRef = column.type.typeRef
  }

  private fun <T : DmnModelElementInstance> newInstance(elementClass: KClass<T>, id: String): T = dmn.newInstance(elementClass.java, id)
  private fun newText(value: String?) = newInstance(Text::class).apply {
    textContent = value
  }

  inner class DecisionModel(
    decisionDefinitionKey: String,
    name: String,
    hitPolicy: HitPolicy,
    versionTag: String?
  ) {
    val decision = newInstance(Decision::class, decisionDefinitionKey).apply {
      this.name = name
      this.versionTag = versionTag
      definitions.addChildElement(this)
    }
    val decisionTable = newInstance(DecisionTable::class).apply {
      this.hitPolicy = hitPolicy
      decision.addChildElement(this)
    }

    fun rules(rules: List<DmnRule>) {
      val inputs = rules.distinctInputs()
      val outputs = rules.distinctOutputs()
//
//      // add headers
//      inputs.forEach { decisionTable.addChildElement(input(it))}
//      outputs.forEach{ decisionTable.addChildElement(output(it))}
//
//      rules.forEach { dmnRule ->
//        val rule = newInstance(Rule::class)
//        decisionTable.addChildElement(rule)
//
//        inputs.forEach{ header ->
//          rule.addChildElement(newInstance(InputEntry::class).apply {
//            text = newText(dmnRule.inputs[header])
//          })
//        }
//
//        outputs.forEach { header ->
//          rule.addChildElement(newInstance(OutputEntry::class).apply {
//            text = newText(dmnRule.outputs[header])
//          })
//        }


    }
  }

}
