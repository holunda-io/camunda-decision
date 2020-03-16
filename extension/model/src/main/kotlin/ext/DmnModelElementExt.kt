package io.holunda.decision.model.ext

import io.holunda.decision.model.Id
import org.camunda.bpm.model.dmn.instance.DmnModelElementInstance
import org.camunda.bpm.model.dmn.instance.Text
import kotlin.reflect.KClass


/**
 * Creates a random id in the form `DecisionTable_123456`.
 *
 * There is no guarantee that the id is unique, but it is very unlikely, so good enough.
 */
fun <T : Any> generateId(elementClass: KClass<T>) = "${elementClass.simpleName}_${(2.147483647E9 * Math.random()).toInt()}"


fun DmnModelElementInstance.textContent(value: String?) = newInstance(Text::class).apply {
  textContent = value
}
