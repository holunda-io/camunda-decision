package io.holunda.decision.lib.test

import org.camunda.bpm.engine.ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE
import org.camunda.bpm.engine.ProcessEngineConfiguration.HISTORY_FULL
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration
import org.camunda.bpm.engine.repository.DeploymentBuilder
import org.camunda.bpm.engine.test.ProcessEngineRule
import org.camunda.bpm.engine.test.mock.MockExpressionManager
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance


object CamundaDecisionTestLib {

  enum class DmnTestResource(val fileName:String) {
    FOUR_TABLES("dmn/four-tables.dmn"),
    DISHES_AND_DRINKS("dmn/simulation.dmn"),
    DRD_DEC1_DEC2("dmn/drd-dec1-dec2.dmn"),
    SINGLE_TABLE("dmn/example_single_table.dmn")
    ;

    fun load() : String = readText(fileName)
  }


  fun readModel(resource: String): DmnModelInstance {
    val inputStream = CamundaDecisionTestLib::class.java.getResourceAsStream(resource.addTrailingSlash())

    return Dmn.readModelFromStream(inputStream)!!
  }

  fun readText(resource:String) : String = CamundaDecisionTestLib::class.java
    .getResource(resource.addTrailingSlash())
    .readText()
    .trim()

  fun camunda() = ProcessEngineRule(
    StandaloneInMemProcessEngineConfiguration()
      .apply {
        history = HISTORY_FULL
        databaseSchemaUpdate = DB_SCHEMA_UPDATE_TRUE
        isJobExecutorActivate = false
        expressionManager = MockExpressionManager()
        this.customPostBPMNParseListeners = mutableListOf()
        customJobHandlers = mutableListOf()
      }
      .buildProcessEngine()
  )

  fun ProcessEngineRule.manageDmnDeployment(receiver : DeploymentBuilder.() -> DeploymentBuilder) = this.manageDeployment(
    this.repositoryService.createDeployment()
      .receiver()
      .deploy()
  )

  private fun String.addTrailingSlash() = if (this.startsWith("/")) this else "/$this"
}
