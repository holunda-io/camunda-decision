package io.holunda.decision.runtime

import io.holunda.decision.model.api.DmnDiagramConverter
import io.holunda.decision.model.api.evaluation.DmnDiagramEvaluationModel
import io.holunda.decision.model.jackson.converter.JacksonDiagramConverter
import io.holunda.decision.runtime.cache.DmnDiagramEvaluationModelRepository
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
    val logger = LoggerFactory.getLogger(CamundaDecisionProcessEnginePlugin::class.java)
  }

  init {
    logger.info("registered ${this.javaClass.simpleName}" )
  }

  override fun preInit(config: ProcessEngineConfigurationImpl) {
    if (config.customJobHandlers == null) {
      config.customJobHandlers = mutableListOf()
    }

    config.customJobHandlers.add(RefreshDmnDiagramEvaluationModelCacheJobHandler(evaluationModelRepository, diagramConverter) as JobHandler<*>)

    config.dmnEngineConfiguration = DefaultDmnEngineConfiguration().apply {
      transformer.transformListeners.add(TransformListener(config))
    }
  }

  override fun postProcessEngineBuild(processEngine: ProcessEngine?) {

  }
}
