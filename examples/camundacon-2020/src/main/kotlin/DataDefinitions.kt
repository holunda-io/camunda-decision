package io.holunda.decision.example.camundacon2020

import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.booleanInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.integerInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.stringInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.booleanOutput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.stringOutput
import io.holunda.decision.model.api.element.DmnDiagram

object IsAdultDefinitions {

  val inCustomerId = stringInput(key = "customerId", label = "Customer Id")
  val inCustomerCountry = stringInput(key = "customerCountry", label = "Country")
  val inCustomerState = stringInput(key = "customerState", label = "State")
  val inCustomerAge = integerInput(key = "customerAge", label = "Age")
  val inCustomerSex = stringInput(key = "customerSex", label = "Sex")

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

object ShipmentDefinitions {

  val inIsAdult = IsAdultDefinitions.outIsAdult.toInputDefinition()
  val inName = stringInput(key = "productName", label = "Product Name")
  val inSize = stringInput(key = "productSize", label = "Product Size")

  val outShippingAllowed = booleanOutput(key = "isShipmentAllowed", label = "Shipment allowed?")
  val outAdditionalActions = stringOutput(key = "additionalActions", label = "Additional Actions")
}



/**
 * For demonstartion purposes only!
 */
object CombinedLegalAndProductGenerator {

  fun generate() : DmnDiagram {
    TODO("do this live on camera")
  }

}


fun main() {

}


val inCustomerAge = integerInput("customerAge")

val outReasons = stringOutput("reasons")

val inActive = booleanInput("active")
val outResult = stringOutput("result")
