package io.holunda.decision.generator.builder

import io.holunda.decision.model.DecisionDefinitionKey
import io.holunda.decision.model.Name
import io.holunda.decision.model.VersionTag
import io.holunda.decision.model.element.DmnDecisionTable
import org.apache.commons.lang3.builder.Builder

abstract class DmnDecisionTableBuilder : Builder<DmnDecisionTable> {

  internal var decisionDefinitionKey: DecisionDefinitionKey? = null
  internal var decisionName: Name? = null
  internal var versionTag: VersionTag? = null

}
