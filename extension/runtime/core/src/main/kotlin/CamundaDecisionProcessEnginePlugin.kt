package io.holunda.decision.runtime

import cache.DmnModelType
import io.holunda.decision.model.api.DmnDiagramConverter
import io.holunda.decision.model.api.evaluation.DmnDiagramEvaluationModelRepository
import io.holunda.decision.runtime.CamundaDecisionRuntime.CommandExecutorAdapter
import io.holunda.decision.runtime.cache.RefreshDmnDiagramEvaluationModelCacheCommand
import io.holunda.decision.runtime.cache.RefreshDmnDiagramEvaluationModelCacheJobHandler
import io.holunda.decision.runtime.cache.TransformListener
import org.camunda.bpm.dmn.engine.impl.DefaultDmnEngineConfiguration
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl
import org.camunda.bpm.engine.impl.jobexecutor.JobHandler
import org.slf4j.LoggerFactory

open class CamundaDecisionProcessEnginePlugin(
  private val evaluationModelRepository: DmnDiagramEvaluationModelRepository,
  private val diagramConverter: DmnDiagramConverter
) : AbstractProcessEnginePlugin() {
  companion object {
    private val logger = LoggerFactory.getLogger(CamundaDecisionProcessEnginePlugin::class.java)
  }

  lateinit var commandExecutorAdapter: CommandExecutorAdapter

  init {
    logger.info("registered ${this.javaClass.simpleName}")
  }

  override fun preInit(config: ProcessEngineConfigurationImpl) {
    if (config.customJobHandlers == null) {
      config.customJobHandlers = mutableListOf()
    }

    this.commandExecutorAdapter = CommandExecutorAdapter(config)


    config.customJobHandlers.add(RefreshDmnDiagramEvaluationModelCacheJobHandler(evaluationModelRepository, diagramConverter) as JobHandler<*>)

    config.dmnEngineConfiguration = DefaultDmnEngineConfiguration().apply {
      transformer.transformListeners.add(TransformListener(commandExecutorAdapter))
    }
  }

  override fun postProcessEngineBuild(processEngine: ProcessEngine) {
    val repositoryService = processEngine.repositoryService

    // 1: load all graphs (in camunda: diagrams with more than one table)
    val dmnGraphs = repositoryService.createDecisionRequirementsDefinitionQuery().latestVersion().list().toSet()

    val dmnGraphIds = dmnGraphs.map { it.id }

    // 2: build evaluation model for all graphs
    dmnGraphs.map { it.key }.forEach { key ->
      commandExecutorAdapter.execute(RefreshDmnDiagramEvaluationModelCacheCommand(key, DmnModelType.GRAPH))
    }

    // 3: collect all remaining single tables (all diagrams with only one decision table)
    val singleTables = repositoryService.createDecisionDefinitionQuery().latestVersion().list()
      .filterNot { dmnGraphIds.contains(it.decisionRequirementsDefinitionId) }
      .toSet()

    // 4. build evaluation model for all single tables
    singleTables.map { it.key }.forEach { key ->
      commandExecutorAdapter.execute(RefreshDmnDiagramEvaluationModelCacheCommand(key, DmnModelType.TABLE))
    }
  }
}
