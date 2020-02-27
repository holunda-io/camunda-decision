package io.holunda.decision.model.ext

import io.holunda.decision.model.OutputDefinition
import io.holunda.decision.model.data.DataType
import org.camunda.bpm.model.dmn.instance.Output

fun Output.toOutputDefinition() = OutputDefinition(
  key = name,
  label = label,
  type = DataType.valueOf(typeRef.toUpperCase())
)
