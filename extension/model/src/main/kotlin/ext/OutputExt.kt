package io.holunda.decision.model.ext

import io.holunda.decision.model.DataType
import org.camunda.bpm.model.dmn.instance.Output

fun Output.toOutputDefinition() = DataType.valueByTypeRef(typeRef)
  .outputDefinition(
    key = name,
    label = label
  )
