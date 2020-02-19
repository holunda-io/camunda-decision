package io.holunda.decision.generator

import io.holunda.decision.model.InputDefinition
import io.holunda.decision.model.OutputDefinition
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.HitPolicy
import org.camunda.bpm.model.dmn.impl.DmnModelConstants
import org.camunda.bpm.model.dmn.instance.*
import org.camunda.bpm.model.xml.ModelInstance
import kotlin.reflect.KClass

/**
 * Helper methods, wrapped in object.
 */
object CamundaDecisionGenerator {

  fun model(name: String) = DmnModelBuilder(name)

  /**
   * Extensions to help working with the Dmn Model API.
   */
  object ModelExtension {
    /**
     * Create a new instance of type elementClass on root modelInstance.
     */
    fun <T : DmnModelElementInstance> ModelInstance.newInstance(elementClass: KClass<T>, id: String = generateId(elementClass)): T = this.newInstance(elementClass.java, id)

    /**
     * Create a new instance of type elementClass.
     */
    fun <T : DmnModelElementInstance> DmnModelElementInstance.newInstance(elementClass: KClass<T>, id: String = generateId(elementClass)): T = this.modelInstance.newInstance(elementClass.java, id)

    /**
     * Creates a new instance of type elementClass and adds it as child element.
     */
    fun <T : DmnModelElementInstance> DmnModelElementInstance.addChildElement(elementClass: KClass<T>, id: String = generateId(elementClass)): T {
      val element = newInstance(elementClass, id)
      addChildElement(element)
      return element
    }

    fun DmnModelElementInstance.textContent(value: String?) = newInstance(Text::class).apply {
      textContent = value
    }

    fun DmnModelInstance.definitions(name: String): Definitions {
      val definitions = newInstance(Definitions::class).apply {
        this.namespace = DmnModelConstants.CAMUNDA_NS
        this.name = name
      }

      this.definitions = definitions
      return definitions
    }

    fun Definitions.decision(key: String, name: String, versionTag: String?): Decision {
      val decision = this.modelInstance.newInstance(Decision::class, key).apply {
        this.name = name
        this.versionTag = versionTag
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
  }

  /**
   * Creates a random id in the form `DecisionTable_123456`.
   *
   * There is no guarantee that the id is unique, but it is very unlikely, so good enough.
   */
  fun <T : DmnModelElementInstance> generateId(elementClass: KClass<T>) = "${elementClass.simpleName}_${(2.147483647E9 * Math.random()).toInt()}"


  fun <T : DmnModelElementInstance> DmnModelInstance.createElement(
    parentElement: DmnModelElementInstance,
    id: String, elementClass: Class<T>): T {
    val element = this.newInstance(elementClass)!!
    element.setAttributeValue("id", id, true)
    parentElement.addChildElement(element)
    return element
  }

  fun <T : DmnModelElementInstance> DmnModelInstance.createElement(
    parentElement: DmnModelElementInstance,
    elementClass: Class<T>): T = createElement(parentElement, generateId(elementClass.kotlin), elementClass)

}
