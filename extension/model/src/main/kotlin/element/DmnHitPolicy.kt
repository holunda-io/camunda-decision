package io.holunda.decision.model.element

import org.camunda.bpm.model.dmn.BuiltinAggregator
import org.camunda.bpm.model.dmn.HitPolicy

/**
 * Tuple combining camundas HitPolicy and the BuiltinAggregator (optional, for collection operations).
 */
enum class DmnHitPolicy(val hitPolicy: HitPolicy, val aggregator: BuiltinAggregator? = null) {
  UNIQUE(HitPolicy.UNIQUE),
  FIRST(HitPolicy.FIRST),
  PRIORITY(HitPolicy.PRIORITY),
  ANY(HitPolicy.ANY),
  RULE_ORDER(HitPolicy.RULE_ORDER),
  OUTPUT_ORDER(HitPolicy.OUTPUT_ORDER),
  COLLECT(HitPolicy.COLLECT, null),
  COLLECT_SUM(HitPolicy.COLLECT, BuiltinAggregator.SUM),
  COLLECT_COUNT(HitPolicy.COLLECT, BuiltinAggregator.COUNT),
  COLLECT_MAX(HitPolicy.COLLECT, BuiltinAggregator.MAX),
  COLLECT_MIN(HitPolicy.COLLECT, BuiltinAggregator.MIN);

  companion object {
    fun valueOf(hitPolicy: HitPolicy, aggregator: BuiltinAggregator?): DmnHitPolicy = requireNotNull(
      values().find { it.hitPolicy == hitPolicy && it.aggregator == aggregator }
    ) { "no DmnHitPolicy found for $hitPolicy,$aggregator" }
  }
}
