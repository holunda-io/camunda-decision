package io.holunda.decision.model.jackson.element

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import io.holunda.decision.model.jackson.CamundaDecisionJackson
import io.holunda.decision.model.jackson.CamundaDecisionJackson.Namespaces

object HeaderXml {

  data class InputXml(
    @JacksonXmlProperty(isAttribute = true)
    val id: String,

    @JacksonXmlProperty(isAttribute = true)
    val label: String,

    @JacksonXmlProperty(namespace = Namespaces.NS_DMN_13)
    val inputExpression: InputExpressionXml
  ) {

    data class InputExpressionXml(
      @JacksonXmlProperty(isAttribute = true)
      val id: String,

      @JacksonXmlProperty(isAttribute = true)
      val typeRef: CamundaDecisionJackson.TypeRef,

      @JacksonXmlProperty(namespace = Namespaces.NS_DMN_13)
      val text: String
    )
  }

  data class OutputXml(
    @JacksonXmlProperty(isAttribute = true)
    val id: String,

    @JacksonXmlProperty(isAttribute = true)
    val label: String,

    @JacksonXmlProperty(isAttribute = true)
    val name: String,

    @JacksonXmlProperty(isAttribute = true)
    val typeRef: CamundaDecisionJackson.TypeRef
  )
}
