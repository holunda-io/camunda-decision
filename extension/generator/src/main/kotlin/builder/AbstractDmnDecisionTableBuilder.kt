package io.holunda.decision.generator.builder

import io.holunda.decision.model.DecisionDefinitionKey
import io.holunda.decision.model.Name
import io.holunda.decision.model.VersionTag
import io.holunda.decision.model.element.DmnDecisionTable
import org.apache.commons.lang3.builder.Builder

/**
 * Common superclass of reference builder and rules builder defining shared properties.
 */
abstract class AbstractDmnDecisionTableBuilder : Builder<DmnDecisionTable> {

  internal var decisionDefinitionKey: DecisionDefinitionKey? = null
  internal var decisionName: Name? = null
  internal var versionTag: VersionTag? = null

}
