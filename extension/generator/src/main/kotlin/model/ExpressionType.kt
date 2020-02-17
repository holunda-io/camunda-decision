package io.holunda.decision.generator.model

import java.util.*

enum class ExpressionType(val type: Class<*>) {
  STRING(String::class.java),
  BOOLEAN(Boolean::class.java),
  INTEGER(Integer::class.java),
  LONG(Long::class.java),
  DOUBLE(Double::class.java),
  DATE(Date::class.java),
  ;

  val typeRef = name.toLowerCase()
}
