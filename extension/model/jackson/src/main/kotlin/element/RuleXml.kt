package io.holunda.decision.model.jackson.element

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import io.holunda.decision.model.jackson.CamundaDecisionJackson.Namespaces

data class RuleXml(
  @JacksonXmlProperty(isAttribute = true)
  val id: String,

  @JacksonXmlProperty(namespace = Namespaces.NS_DMN_13)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  val description: String? = null,

  @JacksonXmlProperty(localName = "inputEntry", namespace = Namespaces.NS_DMN_13)
  val inputEntries: List<EntryXml>,

  @JacksonXmlProperty(localName = "outputEntry", namespace = Namespaces.NS_DMN_13)
  val outputEntries: List<EntryXml>

) {

  data class EntryXml(
    @JacksonXmlProperty(isAttribute = true)
    val id: String,

    @JacksonXmlProperty(namespace = Namespaces.NS_DMN_13)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val text: String?
  )
}
