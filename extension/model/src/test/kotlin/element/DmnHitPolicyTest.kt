package io.holunda.decision.model.element

import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.model.dmn.BuiltinAggregator
import org.camunda.bpm.model.dmn.HitPolicy
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class DmnHitPolicyTest(
  private val hitPolicy: HitPolicy,
  private val aggregator: BuiltinAggregator?,
  private val expected: DmnHitPolicy
) {

  companion object {
    @JvmStatic
    @Parameterized.Parameters(name = "{0},{1}={2}")
    fun data() = listOf(
      arrayOf(HitPolicy.ANY, null, DmnHitPolicy.ANY),
      arrayOf(HitPolicy.FIRST, null, DmnHitPolicy.FIRST),
      arrayOf(HitPolicy.OUTPUT_ORDER, null, DmnHitPolicy.OUTPUT_ORDER),
      arrayOf(HitPolicy.RULE_ORDER, null, DmnHitPolicy.RULE_ORDER),
      arrayOf(HitPolicy.PRIORITY, null, DmnHitPolicy.PRIORITY),
      arrayOf(HitPolicy.UNIQUE, null, DmnHitPolicy.UNIQUE),
      arrayOf(HitPolicy.COLLECT, null, DmnHitPolicy.COLLECT),
      arrayOf(HitPolicy.COLLECT, BuiltinAggregator.COUNT, DmnHitPolicy.COLLECT_COUNT),
      arrayOf(HitPolicy.COLLECT, BuiltinAggregator.SUM, DmnHitPolicy.COLLECT_SUM),
      arrayOf(HitPolicy.COLLECT, BuiltinAggregator.MAX, DmnHitPolicy.COLLECT_MAX),
      arrayOf(HitPolicy.COLLECT, BuiltinAggregator.MIN, DmnHitPolicy.COLLECT_MIN)
    )
  }

  @Test
  fun evaluate() {
    assertThat(DmnHitPolicy.valueOf(hitPolicy, aggregator)).isEqualTo(expected);
  }


}

