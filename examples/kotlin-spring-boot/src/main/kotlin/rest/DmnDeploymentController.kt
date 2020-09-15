package io.holunda.decision.example.kotlin.rest

import io.holunda.decision.example.kotlin.inActive
import io.holunda.decision.example.kotlin.inCustomerAge
import io.holunda.decision.example.kotlin.outReasons
import io.holunda.decision.example.kotlin.outResult
import io.holunda.decision.model.CamundaDecisionGenerator
import io.holunda.decision.model.CamundaDecisionGenerator.rule
import io.holunda.decision.model.CamundaDecisionGenerator.table
import io.holunda.decision.model.FeelConditions.feelEqual
import io.holunda.decision.model.FeelConditions.feelFalse
import io.holunda.decision.model.FeelConditions.feelGreaterThanOrEqual
import io.holunda.decision.model.FeelConditions.feelTrue
import io.holunda.decision.model.FeelConditions.resultValue
import io.holunda.decision.model.api.CamundaDecisionRepositoryService
import io.holunda.decision.model.api.CamundaDecisionService
import io.holunda.decision.model.api.DmnDiagramDeployment
import io.holunda.decision.model.api.evaluation.DmnDiagramEvaluationModel
import io.holunda.decision.runtime.CamundaDecisionProcessEnginePlugin
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dmn")
class DmnDeploymentController(
  private val repositoryService: CamundaDecisionService,
  private val pl: CamundaDecisionProcessEnginePlugin
) {

  @GetMapping
  fun getEvaluationModels() : ResponseEntity<List<DmnDiagramEvaluationModel>> = ResponseEntity.ok(repositoryService.findAllModels())

  @PostMapping
  fun deploy() : ResponseEntity<DmnDiagramDeployment> {
    val diagram = CamundaDecisionGenerator.diagram("My Diagram")
      .addDecisionTable(
        table("decision_1")
          .versionTag("1")
          .addRule(rule()
            .condition(
              inCustomerAge.feelGreaterThanOrEqual(18)
            )
            .result(
              outReasons.resultValue("not adult")
            )
          )
      )
      .addDecisionTable(
        table("decision_2")
          .versionTag("2")
          .requiredDecision("decision_1")
          .addRule(rule()
            .condition(
              outReasons.toInputDefinition().feelEqual("adult"),
              inActive.feelTrue()
            )
            .result(
              outResult.resultValue("ok")
            )
          )
          .addRule(rule()
            .condition(
              outReasons.toInputDefinition().feelEqual("not adult"),
              inActive.feelFalse()
            )
            .result(
              outResult.resultValue("not ok")
            )
          )
      )
      .build()

    return ResponseEntity.ok(repositoryService.deploy(diagram))
  }

  @PostMapping("/deploy-single")
  fun deploySingle() : ResponseEntity<DmnDiagramDeployment> {
    val diagram = CamundaDecisionGenerator.diagram("My Single Diagram")
      .id("my-single-diagram")
      .addDecisionTable(
        table("single_1")
          .versionTag("99")
          .addRule(rule()
            .condition(
              inCustomerAge.feelGreaterThanOrEqual(18)
            )
            .result(
              outReasons.resultValue("not adult")
            )
          )
      )
      .build()

    return ResponseEntity.ok(repositoryService.deploy(diagram))
  }
}
