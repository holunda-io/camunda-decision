package converter

import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.booleanInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.longInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.stringOutput
import io.holunda.decision.model.api.element.DmnRule
import io.holunda.decision.model.api.element.toEntry
import io.holunda.decision.model.api.element.toHeader
import io.holunda.decision.model.jackson.converter.JacksonRuleConverter
import io.holunda.decision.model.jackson.element.RuleXml
import io.holunda.decision.model.jackson.element.RuleXml.EntryXml
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class JacksonRuleConverterTest {

  private val ruleXmlWithDescription = RuleXml(
    id = "ruleId_1",
    description = "the description",
    inputEntries = listOf(
      EntryXml("r1", "> 0"),
      EntryXml("r2", "false")
    ),
    outputEntries = listOf(
      EntryXml("r3", null),
      EntryXml("r4", "\"XML\"")
    )
  )

  private val ruleXmlWithoutDescription = ruleXmlWithDescription.copy(description = null)

  private val in1 = longInput("in1")
  private val in2 = booleanInput("in2")
  private val out1 = stringOutput("out1")
  private val out2 = stringOutput("out2")


  internal val model = DmnRule(
    id = "ruleId_1",
    description = "the description",
    inputs = listOf(
      in1.toEntry("> 0"),
      in2.toEntry("false")
    ),
    outputs = listOf(
      out1.toEntry(null),
      out2.toEntry("\"XML\"")
    )
  )

  private val modelWithoutDescription = model.copy(description = null)

  @Test
  fun `convert from xml`() {
    val header = model.toHeader()
    assertThat(JacksonRuleConverter.convert(header, ruleXmlWithDescription)).isEqualTo(model)
    assertThat(JacksonRuleConverter.convert(header, ruleXmlWithoutDescription)).isEqualTo(modelWithoutDescription)
  }

  @Test
  fun `convert to xml`() {
    val xml = JacksonRuleConverter.convert(model)
    assertThat(xml)
      .isEqualToIgnoringGivenFields(ruleXmlWithDescription,
        "inputEntries",
        "outputEntries"
      )

    assertThat(xml.inputEntries[0])
      .isEqualToIgnoringGivenFields(ruleXmlWithDescription.inputEntries[0], "id")
    assertThat(xml.inputEntries[1])
      .isEqualToIgnoringGivenFields(ruleXmlWithDescription.inputEntries[1], "id")
    assertThat(xml.outputEntries[0])
      .isEqualToIgnoringGivenFields(ruleXmlWithDescription.outputEntries[0], "id")
    assertThat(xml.outputEntries[1])
      .isEqualToIgnoringGivenFields(ruleXmlWithDescription.outputEntries[1], "id")

    assertThat(JacksonRuleConverter.convert(modelWithoutDescription))
      .extracting("description")
      .isNull()
  }

  @Test
  fun `xml back and force`() {
    assertThat(JacksonRuleConverter.convert(
      model.toHeader(),
      JacksonRuleConverter.convert(model)
    )).isEqualTo(model)
  }
}
