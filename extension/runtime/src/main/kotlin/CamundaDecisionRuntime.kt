package io.holunda.decision.runtime

import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl
import org.camunda.bpm.engine.impl.context.Context
import org.camunda.bpm.engine.impl.interceptor.Command

object CamundaDecisionRuntime {

  class CommandExecutorAdapter(private val configuration: ProcessEngineConfigurationImpl) {

    fun <T : Any> execute(cmd: Command<T>): T = if (Context.getCommandContext() == null) {
      configuration.commandExecutorTxRequired.execute(cmd);
    } else {
      cmd.execute(Context.getCommandContext());
    }
  }

}
