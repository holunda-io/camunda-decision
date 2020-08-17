package io.holunda.decision.model.xml.element

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import entry.DecisionTableXml
import io.holunda.decision.model.xml.CamundaDecisionXml.Namespaces

data class DecisionXml(
  @JacksonXmlProperty(isAttribute = true)
  val id: String,

  @JacksonXmlProperty(isAttribute = true)
  val name: String,

  @JacksonXmlProperty(localName = "informationRequirement", namespace = InformationRequirementXml.NAMESPACE)
  val informationRequirements: List<InformationRequirementXml> = listOf(),

  @JacksonXmlProperty(namespace = Namespaces.NS_DMN_13)
  val decisionTable: DecisionTableXml
)
