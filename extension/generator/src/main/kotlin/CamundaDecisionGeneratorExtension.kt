package io.holunda.decision.generator

import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.instance.DmnModelElementInstance


object CamundaDecisionGeneratorExtension {


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
    elementClass: Class<T>): T = createElement(parentElement, generateId(elementClass), elementClass)

  fun <T : DmnModelElementInstance> generateId(elementClass: Class<T>) = elementClass.simpleName + Integer.toString( ((2.147483647E9 * Math.random()).toInt()));
}
