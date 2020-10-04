package io.holunda.decision.model.api.data

import io.holunda.decision.model.api.AggregatorName
import io.holunda.decision.model.api.HitPolicyName
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
class DmnHitPolicyTest {

  @Test
  @Parameters(
    "UNIQUE, null, UNIQUE",
    "ANY, null, ANY",
    "FIRST, null, FIRST",
    "COLLECT, null, COLLECT",
    "RULE_ORDER, null, RULE_ORDER",
    "COLLECT, COUNT, COLLECT_COUNT",
    "COLLECT, SUM, COLLECT_SUM",
    "COLLECT, MAX, COLLECT_MAX",
    "COLLECT, MIN, COLLECT_MIN"
  )
  fun `resolve from hitPolicyName and aggregatorName`(
    hitPolicy: HitPolicyName, aggregator: AggregatorName, expected: DmnHitPolicy) {
    assertThat(DmnHitPolicy.valueOf(
      hitPolicy.trim(),
      if (aggregator.trim() == "null") null else aggregator)
    ).isEqualTo(expected);
  }


  @Test
  @Parameters(
    "UNIQUE,1,SINGLE",
    "UNIQUE,2,TUPLE",
    "ANY,1,SINGLE",
    "ANY,2,TUPLE",
    "FIRST,1,SINGLE",
    "FIRST,2,TUPLE",
    "COLLECT,1,LIST",
    "COLLECT,2,TUPLE_LIST",
    "COLLECT_COUNT,1,SINGLE",
    "COLLECT_COUNT,2,SINGLE"
  )
  fun determineResultType(hitPolicy: DmnHitPolicy, numOutputs: Int, expected: ResultType) {
    assertThat(hitPolicy.determineResultType(numOutputs)).isEqualTo(expected)
    assertThat(hitPolicy.determineResultType(numOutputs)).isEqualTo(expected)
  }
}

