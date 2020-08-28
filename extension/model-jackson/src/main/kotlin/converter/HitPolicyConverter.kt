package io.holunda.decision.model.jackson.converter

import io.holunda.decision.model.api.AggregatorName
import io.holunda.decision.model.api.HitPolicyName
import io.holunda.decision.model.api.data.DmnHitPolicy
import org.camunda.bpm.model.dmn.BuiltinAggregator
import org.camunda.bpm.model.dmn.HitPolicy

internal object HitPolicyConverter {

  private val FROM = mapOf(
    HitPolicyPair(HitPolicy.UNIQUE) to DmnHitPolicy.UNIQUE,
    HitPolicyPair(HitPolicy.FIRST) to DmnHitPolicy.FIRST,
    HitPolicyPair(HitPolicy.PRIORITY) to DmnHitPolicy.PRIORITY,
    HitPolicyPair(HitPolicy.ANY) to DmnHitPolicy.ANY,
    HitPolicyPair(HitPolicy.RULE_ORDER) to DmnHitPolicy.RULE_ORDER,
    HitPolicyPair(HitPolicy.OUTPUT_ORDER) to DmnHitPolicy.OUTPUT_ORDER,
    HitPolicyPair(HitPolicy.COLLECT) to DmnHitPolicy.COLLECT,
    HitPolicyPair(HitPolicy.COLLECT, BuiltinAggregator.SUM) to DmnHitPolicy.COLLECT_SUM,
    HitPolicyPair(HitPolicy.COLLECT, BuiltinAggregator.COUNT) to DmnHitPolicy.COLLECT_COUNT,
    HitPolicyPair(HitPolicy.COLLECT, BuiltinAggregator.MAX) to DmnHitPolicy.COLLECT_MAX,
    HitPolicyPair(HitPolicy.COLLECT, BuiltinAggregator.MIN) to DmnHitPolicy.COLLECT_MIN

  )

  private val TO = FROM.map { it.value to it.key }.toMap()

  fun convert(dmnHitPolicy: DmnHitPolicy): HitPolicyPair = TO[dmnHitPolicy] ?: throw IllegalArgumentException("not found for $dmnHitPolicy")

  fun convert(hitPolicyPair: HitPolicyPair): DmnHitPolicy = FROM[hitPolicyPair]
    ?: throw IllegalArgumentException("not found for $hitPolicyPair")

  fun convert(hitPolicy: HitPolicy, builtinAggregator: BuiltinAggregator?) = convert(HitPolicyPair(hitPolicy, builtinAggregator))

  fun convert(hitPolicyName: HitPolicyName, aggregatorName: AggregatorName? = null): HitPolicyPair = convert(DmnHitPolicy.valueOf(hitPolicyName, aggregatorName))

  data class HitPolicyPair(
    val hitPolicy: HitPolicy,
    val aggregator: BuiltinAggregator? = null
  ) {
    val hitPolicyName = hitPolicy.name
    val aggregatorName = aggregator?.name
  }

}
