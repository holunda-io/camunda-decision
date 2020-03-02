package io.holunda.decision.model.ext

import io.holunda.decision.model.element.InputDefinition
import io.holunda.decision.model.element.OutputDefinition
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.HitPolicy
import org.camunda.bpm.model.dmn.impl.DmnModelConstants
import org.camunda.bpm.model.dmn.instance.*
import org.camunda.bpm.model.xml.ModelInstance
import kotlin.reflect.KClass




/**
 * Creates a random id in the form `DecisionTable_123456`.
 *
 * There is no guarantee that the id is unique, but it is very unlikely, so good enough.
 */
fun <T : DmnModelElementInstance> generateId(elementClass: KClass<T>) = "${elementClass.simpleName}_${(2.147483647E9 * Math.random()).toInt()}"




fun DmnModelElementInstance.textContent(value: String?) = newInstance(Text::class).apply {
  textContent = value
}



fun Definitions.decision(key: String, name: String, versionTag: String?, requiredDecision:String?=null): Decision {
  val decision = this.modelInstance.newInstance(Decision::class, key).apply {
    this.name = name
    this.versionTag = versionTag
    if (requiredDecision != null) {
      val decision  = this.modelInstance.getModelElementById<Decision>(requiredDecision)
      this.addChildElement(InformationRequirement::class).apply {
        this.requiredDecision = decision
      }
    }
  }
  addChildElement(decision)
  return decision
}

fun Decision.decisionTable(hitPolicy: HitPolicy) = this.addChildElement(DecisionTable::class).apply {
  this.hitPolicy = hitPolicy
}

fun DecisionTable.output(column: OutputDefinition) = addChildElement(Output::class).apply {
  this.name = column.key
  this.label = column.label
  this.typeRef = column.type.typeRef
}

fun DecisionTable.input(column: InputDefinition) = addChildElement(Input::class).apply {
  this.label = column.label
  inputExpression = newInstance(InputExpression::class).apply {
    text = textContent(column.key)
    typeRef = column.type.typeRef
  }
}


fun DmnModelInstance.convertToString() = Dmn.convertToString(this)!!
