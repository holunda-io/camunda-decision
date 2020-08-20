package io.holunda.decision.model.api.data

import io.holunda.decision.model.api.AggregatorName
import io.holunda.decision.model.api.HitPolicyName


/**
 * Tuple combining camundas HitPolicy and the Aggregator (optional, for collection operations).
 */
enum class DmnHitPolicy {
  UNIQUE,
  FIRST,
  PRIORITY,
  ANY,
  RULE_ORDER,
  OUTPUT_ORDER,
  COLLECT,
  COLLECT_SUM,
  COLLECT_COUNT,
  COLLECT_MAX,
  COLLECT_MIN;

  companion object {
    fun valueOf(hitPolicy: HitPolicyName, aggregator: AggregatorName?): DmnHitPolicy = valueOf(
      hitPolicy + if (aggregator != null) "_${aggregator}" else ""
    )
  }

}
