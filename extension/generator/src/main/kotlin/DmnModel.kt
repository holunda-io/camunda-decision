package io.holunda.decision.generator

import io.holunda.decision.generator.model.ColumnHeader
import io.holunda.decision.generator.model.DmnRule
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.HitPolicy
import org.camunda.bpm.model.dmn.impl.DmnModelConstants
import org.camunda.bpm.model.dmn.instance.*
import kotlin.reflect.KClass


class DmnModel(val dmn: DmnModelInstance, name: String) {
  companion object {
    fun create(name: String) = DmnModel(Dmn.createEmptyModel(), name)
    fun <T : DmnModelElementInstance> generateId(elementClass: KClass<T>) = "${elementClass.simpleName}_" + Integer.toString(((2.147483647E9 * Math.random()).toInt()));
  }

  val definitions = newInstance(Definitions::class).apply {
    this.namespace = DmnModelConstants.CAMUNDA_NS
    this.name = name
    dmn.definitions = this
  }

  fun convertToString() = Dmn.convertToString(dmn)!!

  private fun <T : DmnModelElementInstance> newInstance(elementClass: KClass<T>): T = newInstance(elementClass, generateId(elementClass))

  private fun input(column: ColumnHeader): Input = newInstance(Input::class).apply {
    this.label = column.label
    val inputExpression: InputExpression = newInstance(InputExpression::class)
    inputExpression.text = newText(column.key)
    inputExpression.typeRef = column.typeRef
    this.inputExpression = inputExpression
  }

  private fun output(column: ColumnHeader) = newInstance(Output::class).apply {
    this.name = column.key
    this.label = column.label
    this.typeRef = column.typeRef
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
      val inputs = rules.map { it.inputs }.flatMap { it.keys }.distinct().sortedBy { it.key }
      val outputs = rules.map { it.outputs }.flatMap { it.keys }.distinct().sortedBy { it.key }

      // add headers
      inputs.forEach { decisionTable.addChildElement(input(it))}
      outputs.forEach{ decisionTable.addChildElement(output(it))}

      rules.forEach { dmnRule ->
        val rule = newInstance(Rule::class)
        decisionTable.addChildElement(rule)

        inputs.forEach{ header ->
          rule.addChildElement(newInstance(InputEntry::class).apply {
            text = newText(dmnRule.inputs[header])
          })
        }

        outputs.forEach { header ->
          rule.addChildElement(newInstance(OutputEntry::class).apply {
            text = newText(dmnRule.outputs[header])
          })
        }

      }
    }
  }

}
