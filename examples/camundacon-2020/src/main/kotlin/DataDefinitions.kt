package io.holunda.decision.example.camundacon2020

import io.holunda.decision.example.camundacon2020.fn.DmnRepositoryLoader
import io.holunda.decision.model.CamundaDecisionGenerator
import io.holunda.decision.model.CamundaDecisionGenerator.rule
import io.holunda.decision.model.CamundaDecisionGenerator.table
import io.holunda.decision.model.CamundaDecisionGenerator.tableReference
import io.holunda.decision.model.CamundaDecisionModel
import io.holunda.decision.model.FeelConditions.feelBetween
import io.holunda.decision.model.FeelConditions.feelEqual
import io.holunda.decision.model.FeelConditions.feelFalse
import io.holunda.decision.model.FeelConditions.feelGreaterThanOrEqual
import io.holunda.decision.model.FeelConditions.feelMatchOne
import io.holunda.decision.model.FeelConditions.feelTrue
import io.holunda.decision.model.FeelConditions.resultFalse
import io.holunda.decision.model.FeelConditions.resultTrue
import io.holunda.decision.model.FeelConditions.resultValue
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.booleanInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.integerInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.stringInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.booleanOutput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.stringOutput
import io.holunda.decision.model.api.data.DmnHitPolicy
import io.holunda.decision.model.api.element.DmnDiagram
import io.holunda.decision.model.condition.jbool.JBoolExpressionSupplier.Companion.or
import io.holunda.decision.model.jackson.converter.JacksonDiagramConverter
import org.springframework.stereotype.Component

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
    const val file = "legal_age.dmn"


    object TableData {
      const val name = "Is adult?"
      const val decisionDefinitionKey = "dt_is_adult"
    }
  }
}

object ShipmentDefinitions {

  val inIsAdult = IsAdultDefinitions.outIsAdult.toInputDefinition()
  val inId = stringInput(key = "productId", label = "Product ID")
  val inName = stringInput(key = "productName", label = "Product Name")
  val inSize = stringInput(key = "productSize", label = "Product Size")

  val outShippingAllowed = booleanOutput(key = "isShipmentAllowed", label = "Shipment allowed?")
  val outAdditionalActions = stringOutput(key = "additionalActions", label = "Additional Actions")
}


/**
 * For demonstration purposes only!
 */
@Component
class CombinedLegalAndProductGenerator(
  private val loader: DmnRepositoryLoader
) {

  fun generate(): DmnDiagram {
    val isAdultDiagram = loader.loadDiagram("legal_age.dmn")

    return CamundaDecisionGenerator.diagram("Legal and Product")
      .id("legal_and_product")
      .addDecisionTable(
        tableReference(isAdultDiagram)
          .decisionDefinitionKey("lap_isAdult")
          .versionTag("2")
          .name("Is Adult (in diagram)")
      )
      .addDecisionTable(
        table("lap_shipmentAllowed")
          .hitPolicy(DmnHitPolicy.FIRST)
          .name("Sell product (in diagram)")
          .versionTag("1")
          .requiredDecision("lap_isAdult")
          .addRule(
            rule(
              ShipmentDefinitions.inIsAdult.feelTrue(),
              or(
                ShipmentDefinitions.inName.feelEqual("Car"),
                ShipmentDefinitions.inSize.feelEqual("L")
              )
            )
              .description("adult and (car or L)")
              .result(
                ShipmentDefinitions.outShippingAllowed.resultTrue(),
                ShipmentDefinitions.outAdditionalActions.resultValue("send gift-basket")
              )
          )
          .addRule(
            rule(
              ShipmentDefinitions.inIsAdult.feelFalse()
            )
              .description("no way")
              .result(
                ShipmentDefinitions.outShippingAllowed.resultFalse(),
                ShipmentDefinitions.outAdditionalActions.resultValue("call parents")
              )

          )
      ).build()
  }

}


fun main() {
  val diagram = CombinedLegalAndProductGenerator(DemoHelper.loader).generate()

  printDiagram(diagram)
}




//  val diagram = CombinedLegalAndProductGenerator(DemoHelper.loader).generate()

private fun printDiagram(diagram: DmnDiagram, xml:Boolean=false) = if (!xml) {
  println("""
# ASCII(${diagram.name})

${CamundaDecisionModel.render(diagram)}

""".trimIndent())
} else {
  println("""
# XML

${JacksonDiagramConverter.toXml(diagram)}
    """.trimIndent())
}
