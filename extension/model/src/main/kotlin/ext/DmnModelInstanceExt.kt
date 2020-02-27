package io.holunda.decision.model.ext

import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.instance.DmnElement
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


