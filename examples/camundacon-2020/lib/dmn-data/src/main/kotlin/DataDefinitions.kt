package io.holunda.decision.example.camundacon2020.data

import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.booleanInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.integerInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.stringOutput

val inCustomerAge = integerInput("customerAge")

val outReasons = stringOutput("reasons")

val inActive = booleanInput("active")
val outResult = stringOutput("result")
