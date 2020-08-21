package io.holunda.decision.model.xml.element

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import io.holunda.decision.model.xml.CamundaDecisionXml.Namespaces

data class InformationRequirementXml(
  @JacksonXmlProperty(isAttribute = true)
  val id: String,

  @JacksonXmlProperty(namespace = Namespaces.NS_DMN_13)
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
