package io.holunda.decision.model.jackson.element

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import io.holunda.decision.model.jackson.CamundaDecisionJackson.Namespaces

data class InformationRequirementXml(
  @JacksonXmlProperty(isAttribute = true)
  val id: String,

  @JacksonXmlProperty(namespace = Namespaces.NS_DMN_13)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  val requiredDecision: RequiredDecisionXml?
) {
  companion object {
    const val NAMESPACE = Namespaces.NS_DMN_13
  }

  data class RequiredDecisionXml(
    @JacksonXmlProperty(isAttribute = true)
    val href: String
  )
}
