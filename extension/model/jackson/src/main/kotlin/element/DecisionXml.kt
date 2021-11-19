package io.holunda.decision.model.jackson.element

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import io.holunda.decision.model.api.DecisionDefinitionKey
import io.holunda.decision.model.api.Name
import io.holunda.decision.model.api.VersionTag
import io.holunda.decision.model.jackson.CamundaDecisionJackson.Namespaces

data class DecisionXml(
  @JacksonXmlProperty(isAttribute = true)
  val id: DecisionDefinitionKey,

  @JacksonXmlProperty(isAttribute = true)
  val name: Name,

  @JacksonXmlProperty(isAttribute = true, namespace = Namespaces.NS_CAMUNDA)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  val versionTag:VersionTag? = null,

  @JacksonXmlProperty(localName = "informationRequirement", namespace = InformationRequirementXml.NAMESPACE)
  val informationRequirements: List<InformationRequirementXml> = listOf(),

  @JacksonXmlProperty(namespace = Namespaces.NS_DMN_13)
  val decisionTable: DecisionTableXml
)
