package io.holunda.decision.model.ext

import io.holunda.decision.model.data.DataType
import org.camunda.bpm.model.dmn.instance.Output

fun Output.toOutputDefinition() = DataType.valueOf(typeRef)
  .outputDefinition(
    key = name,
    label = label
  )
