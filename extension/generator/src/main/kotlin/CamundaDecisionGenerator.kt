package io.holunda.decision.generator

import io.holunda.decision.generator.builder.DmnDecisionTableRulesBuilder
import io.holunda.decision.generator.builder.DmnDiagramBuilder
import io.holunda.decision.generator.builder.DmnRulesBuilder
import io.holunda.decision.model.DecisionDefinitionKey

object CamundaDecisionGenerator {

  @JvmStatic
  fun diagram() = DmnDiagramBuilder()

  @JvmStatic
  fun table() = DmnDecisionTableRulesBuilder()

  @JvmStatic
  fun rule() = DmnRulesBuilder()
}
