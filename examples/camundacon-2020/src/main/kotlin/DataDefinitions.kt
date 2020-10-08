package io.holunda.decision.example.camundacon2020

import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.booleanInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.dateInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.integerInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.stringInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.booleanOutput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.stringOutput

object IsAdultDefinitions {

  val inCustomerCountry = stringInput(key = "customerCountry", label = "Country")
  val inCustomerState = stringInput(key = "customerState", label = "State")
  val inCustomerAge = integerInput(key = "customerAge", label = "Age")
  val inCustomerSex = integerInput(key = "customerSex", label = "Sex")

  val outIsAdult = booleanOutput(key = "isAdult", label = "Is Adult?")

  object DiagramData {
    const val name = "Is Adult Diagram"
    const val id = "diagram_is_adult"

    object TableData {
      const val name = "Is adult?"
      const val decisionDefinitionKey = "dt_is_adult"
    }
  }
}

val inCustomerAge = integerInput("customerAge")

val outReasons = stringOutput("reasons")

val inActive = booleanInput("active")
val outResult = stringOutput("result")
