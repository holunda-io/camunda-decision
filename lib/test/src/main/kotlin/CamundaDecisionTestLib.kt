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
  fun readModel(resource: String): DmnModelInstance {
    val inputStream = CamundaDecisionTestLib::class.java.getResourceAsStream(resource.trailingSlash())

    return Dmn.readModelFromStream(inputStream)!!
  }

  fun readText(resource:String) : String = CamundaDecisionTestLib::class.java
    .getResource(resource.trailingSlash())
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

  private fun String.trailingSlash() = if (this.startsWith("/")) this else "/$this"
}
