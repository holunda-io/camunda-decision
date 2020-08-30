package io.holunda.decision.model.jackson.converter

import io.holunda.decision.model.api.data.DmnHitPolicy
import io.holunda.decision.model.jackson.converter.HitPolicyConverter.HitPolicyPair
import io.holunda.decision.model.jackson.converter.HitPolicyConverter.convert
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.model.dmn.BuiltinAggregator
import org.camunda.bpm.model.dmn.HitPolicy
import org.junit.Test

internal class HitPolicyConverterTest {


  @Test
  fun `to dmnHitPolicy`() {
    assertThat(convert(HitPolicy.UNIQUE, null)).isEqualTo(DmnHitPolicy.UNIQUE)
    assertThat(convert(HitPolicy.FIRST, null)).isEqualTo(DmnHitPolicy.FIRST)
    // assertThat(convert(HitPolicy.PRIORITY, null)).isEqualTo(DmnHitPolicy.PRIORITY) see #25
    assertThat(convert(HitPolicy.ANY, null)).isEqualTo(DmnHitPolicy.ANY)
    assertThat(convert(HitPolicy.RULE_ORDER, null)).isEqualTo(DmnHitPolicy.RULE_ORDER)
    // assertThat(convert(HitPolicy.OUTPUT_ORDER, null)).isEqualTo(DmnHitPolicy.OUTPUT_ORDER) see #24
    assertThat(convert(HitPolicy.COLLECT, BuiltinAggregator.SUM)).isEqualTo(DmnHitPolicy.COLLECT_SUM)
    assertThat(convert(HitPolicy.COLLECT, BuiltinAggregator.COUNT)).isEqualTo(DmnHitPolicy.COLLECT_COUNT)
    assertThat(convert(HitPolicy.COLLECT, BuiltinAggregator.MAX)).isEqualTo(DmnHitPolicy.COLLECT_MAX)
    assertThat(convert(HitPolicy.COLLECT, BuiltinAggregator.MIN)).isEqualTo(DmnHitPolicy.COLLECT_MIN)
  }

  @Test
  fun `from dmnHitPolicy`() {
    assertThat(convert(DmnHitPolicy.UNIQUE)).isEqualTo(HitPolicyPair(HitPolicy.UNIQUE, null))
    assertThat(convert(DmnHitPolicy.FIRST)).isEqualTo(HitPolicyPair(HitPolicy.FIRST, null))
    // assertThat(convert(DmnHitPolicy.PRIORITY)).isEqualTo(HitPolicyPair(HitPolicy.PRIORITY, null)) see #25
    assertThat(convert(DmnHitPolicy.ANY)).isEqualTo(HitPolicyPair(HitPolicy.ANY, null))
    assertThat(convert(DmnHitPolicy.RULE_ORDER)).isEqualTo(HitPolicyPair(HitPolicy.RULE_ORDER, null))
    // assertThat(convert(DmnHitPolicy.OUTPUT_ORDER)).isEqualTo(HitPolicyPair(HitPolicy.OUTPUT_ORDER, null)) see #24
    assertThat(convert(DmnHitPolicy.COLLECT)).isEqualTo(HitPolicyPair(HitPolicy.COLLECT, null))
    assertThat(convert(DmnHitPolicy.COLLECT_COUNT)).isEqualTo(HitPolicyPair(HitPolicy.COLLECT, BuiltinAggregator.COUNT))
    assertThat(convert(DmnHitPolicy.COLLECT_SUM)).isEqualTo(HitPolicyPair(HitPolicy.COLLECT, BuiltinAggregator.SUM))
    assertThat(convert(DmnHitPolicy.COLLECT_MAX)).isEqualTo(HitPolicyPair(HitPolicy.COLLECT, BuiltinAggregator.MAX))
    assertThat(convert(DmnHitPolicy.COLLECT_MIN)).isEqualTo(HitPolicyPair(HitPolicy.COLLECT, BuiltinAggregator.MIN))
  }
}
