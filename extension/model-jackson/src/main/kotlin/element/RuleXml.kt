package io.holunda.decision.model.xml.element

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import io.holunda.decision.model.xml.CamundaDecisionXml

data class RuleXml(
  @JacksonXmlProperty(isAttribute = true)
  val id: String,

  @JacksonXmlProperty(localName = "inputEntry", namespace = CamundaDecisionXml.Namespaces.NS_DMN_13)
  val inputEntries: List<EntryXml>,

  @JacksonXmlProperty(localName = "outputEntry", namespace = CamundaDecisionXml.Namespaces.NS_DMN_13)
  val outputEntries: List<EntryXml>

) {

  data class EntryXml(
    @JacksonXmlProperty(isAttribute = true)
    val id: String,

    @JacksonXmlProperty(namespace = CamundaDecisionXml.Namespaces.NS_DMN_13)
    val text: String?
  )
}
