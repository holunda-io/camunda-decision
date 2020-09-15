package io.holunda.decision.runtime

import org.camunda.bpm.dmn.engine.DmnDecision
import org.camunda.bpm.dmn.engine.DmnDecisionRequirementsGraph
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableInputImpl
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableOutputImpl
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableRuleImpl
import org.camunda.bpm.dmn.engine.impl.spi.transform.DmnTransformListener
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl
import org.camunda.bpm.engine.impl.context.Context
import org.camunda.bpm.engine.impl.interceptor.Command
import org.camunda.bpm.model.dmn.instance.*

object CamundaDecisionRuntime {

  class CommandExecutorAdapter(private val configuration: ProcessEngineConfigurationImpl) {

    fun <T : Any> execute(cmd: Command<T>): T = if (Context.getCommandContext() == null) {
      configuration.commandExecutorTxRequired.execute(cmd);
    } else {
      cmd.execute(Context.getCommandContext());
    }
  }

}
