package io.holunda.decision.model.converter

import io.holunda.decision.lib.test.CamundaDecisionTestLib
import io.holunda.decision.model.CamundaDecisionGenerator
import io.holunda.decision.model.CamundaDecisionGenerator.rule
import io.holunda.decision.model.CamundaDecisionGenerator.table
import io.holunda.decision.model.CamundaDecisionGenerator.tableReference
import io.holunda.decision.model.FeelConditions.feelTrue
import io.holunda.decision.model.FeelConditions.resultFalse
import io.holunda.decision.model.api.CamundaDecisionModelApi
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.booleanInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.booleanOutput
import io.holunda.decision.model.api.data.DmnHitPolicy
import org.camunda.bpm.model.dmn.Dmn
import org.junit.Test

internal class CamundaModelApiDiagramConverterTest {

  val diagram = CamundaDecisionGenerator.diagram("Legal and Product")
    .id("legal_and_product")
    .addDecisionTable(
      table("lap_shipmentAllowed")
        .hitPolicy(DmnHitPolicy.FIRST)
        .name("Sell product (in diagram)")
        .versionTag("1")
        .addRule(
          rule(
            booleanInput("key", "Key").feelTrue()
          )
            .description("adult and (car or L)")
            .result(
              booleanOutput("res", "Result").resultFalse()
            )
        )
    )
    .addDecisionTable(
      table("lap_shipmentAllowed1")
        .requiredDecision("lap_shipmentAllowed")
        .hitPolicy(DmnHitPolicy.FIRST)
        .name("Sell product (in diagram)")
        .versionTag("1")
        .addRule(
          rule(
            booleanInput("key", "Key").feelTrue()
          )
            .description("adult and (car or L)")
            .result(
              booleanOutput("res", "Result").resultFalse()
            )
        )
    )
    .build()

  private val converter = CamundaModelApiDiagramConverter()

  @Test
  fun name() {
    val modelInstance = diagramToModelInstance(diagram)

    println(Dmn.convertToString(modelInstance))
  }
}
