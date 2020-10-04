package io.holunda.decision.runtime.cache

import io.holunda.decision.runtime.cache.RefreshDmnDiagramEvaluationModelCacheConfiguration.ModelType
import org.camunda.bpm.dmn.engine.DmnDecision
import org.camunda.bpm.dmn.engine.DmnDecisionRequirementsGraph
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableInputImpl
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableOutputImpl
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableRuleImpl
import org.camunda.bpm.dmn.engine.impl.spi.transform.DmnTransformListener
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl
import org.camunda.bpm.model.dmn.instance.*

class TransformListener(
  private val processEngineConfigurationImpl: ProcessEngineConfigurationImpl
) : DmnTransformListener {
  override fun transformDecision(decision: Decision, dmnDecision: DmnDecision) {
  }

  override fun transformDecisionTableInput(input: Input?, dmnInput: DmnDecisionTableInputImpl?) {
  }

  override fun transformDecisionTableOutput(output: Output?, dmnOutput: DmnDecisionTableOutputImpl?) {
  }

  override fun transformDecisionTableRule(rule: Rule?, dmnRule: DmnDecisionTableRuleImpl?) {
  }

  override fun transformDecisionRequirementsGraph(definitions: Definitions, dmnDecisionRequirementsGraph: DmnDecisionRequirementsGraph) {
    val command = if (dmnDecisionRequirementsGraph.decisions.size == 1) {
      RefreshDmnDiagramEvaluationModelCacheCommand(dmnDecisionRequirementsGraph.decisionKeys.first(), ModelType.TABLE)
    } else {
      RefreshDmnDiagramEvaluationModelCacheCommand(dmnDecisionRequirementsGraph.key, ModelType.GRAPH)
    }
    processEngineConfigurationImpl.commandExecutorTxRequired.execute(command)
  }

}
