package io.holunda.decision.model.jackson.element

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import io.holunda.decision.model.jackson.CamundaDecisionJackson.Namespaces
import org.camunda.bpm.model.dmn.BuiltinAggregator
import org.camunda.bpm.model.dmn.HitPolicy

data class DecisionTableXml(
  @JacksonXmlProperty(isAttribute = true)
  val id: String,

  @JacksonXmlProperty(isAttribute = true)
  val hitPolicy: HitPolicy = HitPolicy.UNIQUE,

  @JacksonXmlProperty(isAttribute = true)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  val aggregation: BuiltinAggregator? = null,

  @JacksonXmlProperty(localName = "input", namespace = Namespaces.NS_DMN_13)
  val inputs: List<HeaderXml.InputXml>,

  @JacksonXmlProperty(localName = "output", namespace = Namespaces.NS_DMN_13)
  val outputs: List<HeaderXml.OutputXml>,

  @JacksonXmlProperty(localName = "rule", namespace = Namespaces.NS_DMN_13)
  val rules: List<RuleXml>
) {
  companion object {
    const val NAMESPACE = Namespaces.NS_DMN_13
  }
}
