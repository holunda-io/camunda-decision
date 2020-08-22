package io.holunda.decision.model.jackson.converter

import io.holunda.decision.model.jackson.converter.JacksonDecisionTableConverter.convert
import io.holunda.decision.model.jackson.element.InformationRequirementXml
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


internal class JacksonDecisionTableConverterTest {


  @Test
  fun `convert from informationRequirementXmls`() {
    // no requirements
    assertThat(convert(listOf())).isEmpty()

    // null requirement
    assertThat(convert(listOf(InformationRequirementXml(id = "1", requiredDecision = null))))
      .isEmpty()

    // single requirement
    assertThat(convert(listOf(InformationRequirementXml(id = "1", requiredDecision = InformationRequirementXml.RequiredDecisionXml(href = "#fooTable")))))
      .containsOnly("fooTable")
  }


}

