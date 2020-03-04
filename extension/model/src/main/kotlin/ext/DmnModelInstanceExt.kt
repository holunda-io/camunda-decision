package io.holunda.decision.model.ext

import io.holunda.decision.model.CamundaDecisionModel.CAMUNDA_NS
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.impl.DmnModelConstants
import org.camunda.bpm.model.dmn.instance.Definitions
import org.camunda.bpm.model.dmn.instance.DmnElement
import org.camunda.bpm.model.dmn.instance.DmnModelElementInstance
import org.camunda.bpm.model.xml.ModelInstance
import kotlin.reflect.KClass


@JvmOverloads
inline fun <reified T : DmnElement> DmnModelInstance.getModelElementByType(referencingClass: KClass<T>, id: String? = null): T = with(getModelElementsByType(referencingClass)) {
  require(this.size < 2 || id != null) { "more than one element found, specify 'id'" }

  return if (this.size == 1 && id == null) {
    requireNotNull(this.firstOrNull()) { "no decision found" }
  } else {
    requireNotNull(this.find { it.id == id }) { "no element found with id=$id" }
  }
}

inline fun <reified T : DmnElement> DmnModelInstance.getModelElementsByType(referencingClass: KClass<T>): Collection<T> = this.getModelElementsByType(referencingClass.java)

/**
 * Create a new instance of type elementClass on root modelInstance.
 */
fun <T : DmnModelElementInstance> ModelInstance.newInstance(elementClass: KClass<T>, id: String? = null): T = this.newInstance(
  elementClass.java,
  id ?: generateId(elementClass)
)


/**
 * Create a new instance of type elementClass.
 */
fun <T : DmnModelElementInstance> DmnModelElementInstance.newInstance(elementClass: KClass<T>, id: String? = null): T = this.modelInstance.newInstance(
  elementClass.java,
  id ?: generateId(elementClass)
)


fun DmnModelInstance.definitions(name: String = "DRD", id: String? = null): Definitions {
  val definitions = newInstance(Definitions::class, id).apply {
    this.namespace = CAMUNDA_NS
    this.name = name
  }

  this.definitions = definitions
  return definitions
}

/**
 * Creates a new instance of type elementClass and adds it as child element.
 */
fun <T : DmnModelElementInstance> DmnModelElementInstance.addChildElement(elementClass: KClass<T>, id: String = generateId(elementClass)): T {
  val element = newInstance(elementClass, id)
  addChildElement(element)
  return element
}

fun DmnModelInstance.toBpmn() = Dmn.convertToString(this)
