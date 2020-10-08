package io.holunda.decision.example.camundacon2020.rest

import io.holunda.decision.example.camundacon2020.*
import io.holunda.decision.example.camundacon2020.fn.DmnRepositoryLoader
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
  private val loader: DmnRepositoryLoader,
  private val repo: DmnDiagramEvaluationModelInMemoryRepository,
  private val generator: CombinedLegalAndProductGenerator
) {

  /**
   * This returns the list of all [DmnDiagramEvaluationModel]s currently known to the system.
   */
  @GetMapping
  fun getEvaluationModels(): ResponseEntity<List<DmnDiagramEvaluationModel>> = ResponseEntity.ok(camundaDecisionService.findAllModels())

  /**
   * Deploys a dmn file located in the external repository directory configured via  [CamundaConExampleProperties.repository].
   */
  @PostMapping(path = ["diagrams/{diagramFile}"])
  fun deployFile(@PathVariable("diagramFile") diagramFile: String): ResponseEntity<DmnDiagramDeployment> {
    val diagram = loader.loadDiagram(diagramFile)

    val deployment = camundaDecisionService.deploy(diagram)

    return ResponseEntity.ok(deployment)
  }


  @PostMapping(path = ["/live-demo"])
  fun combinedProductAndLegal(): ResponseEntity<DmnDiagramDeployment> = ResponseEntity.ok(
    camundaDecisionService.deploy(
      generator.generate()
    )
  )

}
