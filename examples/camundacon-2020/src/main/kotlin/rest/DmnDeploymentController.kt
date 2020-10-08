package io.holunda.decision.example.camundacon2020.rest

import io.holunda.decision.example.camundacon2020.fn.DmnRepositoryLoader
import io.holunda.decision.example.camundacon2020.inActive
import io.holunda.decision.example.camundacon2020.inCustomerAge
import io.holunda.decision.example.camundacon2020.outReasons
import io.holunda.decision.example.camundacon2020.outResult
import io.holunda.decision.model.CamundaDecisionGenerator
import io.holunda.decision.model.CamundaDecisionGenerator.rule
import io.holunda.decision.model.CamundaDecisionGenerator.table
import io.holunda.decision.model.FeelConditions.feelEqual
import io.holunda.decision.model.FeelConditions.feelFalse
import io.holunda.decision.model.FeelConditions.feelGreaterThanOrEqual
import io.holunda.decision.model.FeelConditions.feelTrue
import io.holunda.decision.model.FeelConditions.resultValue
import io.holunda.decision.model.api.CamundaDecisionService
import io.holunda.decision.model.api.DmnDiagramDeployment
import io.holunda.decision.model.api.evaluation.DmnDiagramEvaluationModel
import io.holunda.decision.runtime.cache.DmnDiagramEvaluationModelInMemoryRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dmn")
class DmnDeploymentController(
  private val camundaDecisionService: CamundaDecisionService,
  private val loader : DmnRepositoryLoader,
  private val repo : DmnDiagramEvaluationModelInMemoryRepository
) {

  @PostMapping(path = ["diagrams/{diagramFile}"])
  fun deployFile(@PathVariable("diagramFile") diagramFile: String): ResponseEntity<DmnDiagramDeployment> {
    val diagram = loader.loadDiagram(diagramFile)

    return ResponseEntity.ok(camundaDecisionService.deploy(diagram))
  }

  @GetMapping(path=["/cache"])
  fun getCache() = ResponseEntity.ok(repo)

  @GetMapping
  fun getEvaluationModels(): ResponseEntity<List<DmnDiagramEvaluationModel>> = ResponseEntity.ok(camundaDecisionService.findAllModels())

  @PostMapping
  fun deploy(): ResponseEntity<DmnDiagramDeployment> {
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

    return ResponseEntity.ok(camundaDecisionService.deploy(diagram))
  }

  @PostMapping("/deploy-single")
  fun deploySingle(): ResponseEntity<DmnDiagramDeployment> {
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

    return ResponseEntity.ok(camundaDecisionService.deploy(diagram))
  }
}
