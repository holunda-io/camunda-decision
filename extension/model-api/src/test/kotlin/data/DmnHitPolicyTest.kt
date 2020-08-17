package io.holunda.decision.model.element

import io.holunda.decision.model.api.AggregatorName
import io.holunda.decision.model.api.HitPolicyName
import io.holunda.decision.model.api.data.DmnHitPolicy
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class DmnHitPolicyTest(
  private val hitPolicy: HitPolicyName,
  private val aggregator: AggregatorName?,
  private val expected: DmnHitPolicy
) {

  companion object {
    @JvmStatic
    @Parameterized.Parameters(name = "{0},{1}={2}")
    fun data() = listOf(
      arrayOf("ANY", null, DmnHitPolicy.ANY),
      arrayOf("FIRST", null, DmnHitPolicy.FIRST),
      arrayOf("OUTPUT_ORDER", null, DmnHitPolicy.OUTPUT_ORDER),
      arrayOf("RULE_ORDER", null, DmnHitPolicy.RULE_ORDER),
      arrayOf("PRIORITY", null, DmnHitPolicy.PRIORITY),
      arrayOf("UNIQUE", null, DmnHitPolicy.UNIQUE),
      arrayOf("COLLECT", null, DmnHitPolicy.COLLECT),
      arrayOf("COLLECT", "COUNT", DmnHitPolicy.COLLECT_COUNT),
      arrayOf("COLLECT", "SUM", DmnHitPolicy.COLLECT_SUM),
      arrayOf("COLLECT", "MAX", DmnHitPolicy.COLLECT_MAX),
      arrayOf("COLLECT", "MIN", DmnHitPolicy.COLLECT_MIN)
    )
  }

  @Test
  fun evaluate() {
    assertThat(DmnHitPolicy.valueOf(hitPolicy, aggregator)).isEqualTo(expected);
  }


}

