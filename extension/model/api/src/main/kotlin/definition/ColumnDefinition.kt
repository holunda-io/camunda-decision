package io.holunda.decision.model.api.definition

import com.fasterxml.jackson.annotation.JsonIgnore
import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.decision.model.api.data.DataType

/**
 * Each column in a table is described by:
 *
 * - the key (name of the variable)
 * - the type (string,boolean,integer,long,double,date)
 * - a label (displayed)
 */
interface ColumnDefinition<T : Any> {
  val key: String
  val label: String
  val dataType: DataType<T>

  @get: JsonIgnore
  val typeRef: String get() = dataType.typeRef

  @get: JsonIgnore
  val type: Class<T> get() = dataType.type

  @get: JsonIgnore
  val variableFactory: VariableFactory<T> get() = dataType.variableFactory(key)
}
