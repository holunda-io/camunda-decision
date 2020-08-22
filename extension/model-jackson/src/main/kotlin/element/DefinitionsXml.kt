package io.holunda.decision.model.jackson.element

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import io.holunda.decision.model.jackson.CamundaDecisionJackson.Namespaces

@JacksonXmlRootElement(
  localName = "definitions",
  namespace = Namespaces.NS_DMN_13
)
data class DefinitionsXml(

  /**
   * include the camunda namespace
   */
  @JacksonXmlProperty(isAttribute = true)
  val namespace: String = Namespaces.NS_CAMUNDA,

  /**
   * Id of the diagram.
   */
  @JacksonXmlProperty(isAttribute = true)
  val id: String,

  /**
   * Human readable name.
   */
  @JacksonXmlProperty(isAttribute = true)
  val name: String,

  /**
   * The tool used to create the diagram.
   */
  @JacksonXmlProperty(isAttribute = true)
  val exporter: String = "Camunda Decision Generator",

  /**
   * The tool version.
   */
  @JacksonXmlProperty(isAttribute = true)
  val exporterVersion: String = "n/a",

  /**
   * Diagram contains 1..n decisions (aka decision-tables).
   */
  @JacksonXmlProperty(localName = "decision", namespace = Namespaces.NS_DMN_13)
  val decisions: List<DecisionXml>,

  @JacksonXmlProperty(localName = LayoutXml.LOCAL_NAME, namespace = Namespaces.NS_DMNDI)
  val layout: LayoutXml
)
