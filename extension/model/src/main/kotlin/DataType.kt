package io.holunda.decision.model

import java.util.*

/**
 * The allowed typeRefs used for input/output columns.
 */
enum class DataType(val type: Class<*>) {
  STRING(String::class.java),
  BOOLEAN(Boolean::class.java),
  INTEGER(Integer::class.java),
  LONG(Long::class.java),
  DOUBLE(Double::class.java),
  DATE(Date::class.java),
  ;

  val typeRef = name.toLowerCase()
}
