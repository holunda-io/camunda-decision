package io.holunda.decision.runtime

import io.holunda.decision.lib.test.CamundaDecisionTestLib
import io.holunda.decision.model.CamundaDecisionGenerator
import io.holunda.decision.model.CamundaDecisionGenerator.rule
import io.holunda.decision.model.CamundaDecisionGenerator.table
import io.holunda.decision.model.FeelConditions.feelFalse
import io.holunda.decision.model.FeelConditions.feelGreaterThan
import io.holunda.decision.model.FeelConditions.feelLessThan
import io.holunda.decision.model.FeelConditions.feelTrue
import io.holunda.decision.model.FeelConditions.resultFalse
import io.holunda.decision.model.FeelConditions.resultTrue
import io.holunda.decision.model.FeelConditions.resultValue
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.integerInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.booleanOutput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.stringOutput
import io.holunda.decision.model.api.Name
import io.holunda.decision.model.ascii.DmnWriter
import io.holunda.decision.model.jackson.converter.JacksonDiagramConverter
import io.holunda.decision.runtime.cache.DmnDiagramEvaluationModelInMemoryRepository
import io.holunda.decision.runtime.cache.RefreshDmnDiagramEvaluationModelCacheJobHandler
import io.holunda.decision.runtime.cache.TransformListener
import mu.KLogging
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.await
import org.camunda.bpm.dmn.engine.impl.DefaultDmnEngineConfiguration
import org.camunda.bpm.engine.impl.jobexecutor.JobHandler
import org.camunda.bpm.engine.test.Deployment
import org.camunda.bpm.engine.variable.Variables
import org.junit.Before
import org.junit.Rule
import org.junit.Test

//@Deployment(resources = ["dmn/drd-dec1-dec2.dmn"])
class CamundaDecisionRuntimeSpike {
  companion object : KLogging()

  object DmnDiagrams {
    val inD11 = integerInput("inD11")
    val outD11 = booleanOutput("outD11")

    val inD21 = outD11.toInputDefinition()
    val inD22 = integerInput("inD22")

    val outD21 = stringOutput("outD21")

    fun generate(name: Name) = CamundaDecisionGenerator.diagram(name)
      .addDecisionTable(
        table("decision1")
          .addRule(
            rule()
              .condition(inD11.feelGreaterThan(17))
              .result(outD11.resultTrue())
          )
          .addRule(
            rule()
              .condition(inD11.feelGreaterThan(30))
              .result(outD11.resultFalse())
          )
      )
      .addDecisionTable(
        table("decision2")
          .requiredDecision("decision1")
          .addRule(
            rule()
              .condition(inD21.feelTrue(), inD22.feelGreaterThan(10))
              .result(outD21.resultValue("A"))
          )
          .addRule(
            rule()
              .condition(inD21.feelFalse(), inD22.feelLessThan(100))
              .result(outD21.resultValue("B"))
          )
      )
      .build()


    val diagram = generate("My Diagram")
  }


  val drd = "multiple_decisions"

  val dmnDiagramEvaluationModelInMemoryRepository = DmnDiagramEvaluationModelInMemoryRepository()

  @get:Rule
  val camunda = CamundaDecisionTestLib.camunda {
    isJobExecutorActivate = true

    processEnginePlugins.add(CamundaDecisionProcessEnginePlugin(
      dmnDiagramEvaluationModelInMemoryRepository,
      JacksonDiagramConverter)
    )
  }

  val runtimeContext by lazy {
    CamundaDecisionRuntimeContext.builder()
      .withProcessEngineServices(camunda)
      .withDmnDiagramEvaluationModelRepository(dmnDiagramEvaluationModelInMemoryRepository)
      .build()
  }

  val camundaDecisionService by lazy {
    runtimeContext.camundaDecisionService
  }

  val decisionService by lazy {
    camunda.decisionService
  }

  @Before
  fun setUp() {
    // no deployments at engine start
    // assertThat(camunda.repositoryService.createDecisionDefinitionQuery().list()).isEmpty()
  }

  @Test
  fun `deploy and load diagram`() {

    assertThat(camundaDecisionService.findAllModels()).isEmpty()
    logger.info { "\n${DmnWriter.render(DmnDiagrams.diagram)}" }

    val deploy = camundaDecisionService.deploy(DmnDiagrams.diagram)

    logger.info { deploy }

    val model = deploy.deployedDiagrams.first()

    val result = camunda.decisionService.evaluateDecisionTableById(model.decisionDefinitionId, Variables
      .putValue(DmnDiagrams.inD11.key, 18)
      .putValue(DmnDiagrams.inD22.key, 11))

    val o22: String = result.getSingleEntry()

    assertThat(o22).isEqualTo("A")

    val diagrams = camundaDecisionService.findAllModels()
    assertThat(diagrams).hasSize(1)
    logger.info { diagrams }
  }

  @Test
  @Deployment(resources = ["dmn/drd-dec1-dec2.dmn"])
  fun `deploy and get diagram from cache`() {
    await().untilAsserted { assertThat(dmnDiagramEvaluationModelInMemoryRepository.findAll()).hasSize(1) }

    camundaDecisionService.deploy(DmnDiagrams.diagram)

    await().untilAsserted { assertThat(dmnDiagramEvaluationModelInMemoryRepository.findAll()).hasSize(2) }
  }

  @Test
  fun `evaluate decision`() {
    assertThat(camunda.repositoryService.createDecisionDefinitionQuery().decisionDefinitionKey("decision1").list()).isNotNull
    assertThat(camunda.repositoryService.createDecisionDefinitionQuery().decisionDefinitionKey("decision2").singleResult()).isNotNull

    val result = decisionService.evaluateDecisionTableByKey("decision1", Variables.putValue("foo", true)).singleResult

    println(result)

    val r = decisionService.evaluateDecisionTableByKey("decision2",
      Variables.putValue("foo", true).putValue("bar", true)).singleResult

    println("" + r.getSingleEntry())
  }
}
