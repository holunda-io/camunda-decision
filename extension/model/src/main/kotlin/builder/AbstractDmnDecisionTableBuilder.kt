package io.holunda.decision.model.builder

import io.holunda.decision.model.api.DecisionDefinitionKey
import io.holunda.decision.model.api.Name
import io.holunda.decision.model.api.VersionTag
import io.holunda.decision.model.api.element.DmnDecisionTable
import org.apache.commons.lang3.builder.Builder

/**
 * Common superclass of reference builder and rules builder defining shared properties.
 */
abstract class AbstractDmnDecisionTableBuilder : Builder<DmnDecisionTable> {

  internal var decisionDefinitionKey: DecisionDefinitionKey? = null
  internal var decisionName: Name? = null
  internal var versionTag: VersionTag? = null
  internal var requiredDecision: DecisionDefinitionKey? = null

}
