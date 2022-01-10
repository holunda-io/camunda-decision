package io.holunda.decision.model.jackson

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.holunda.decision.model.api.DmnXml
import io.holunda.decision.model.jackson.element.DefinitionsXml
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.impl.DmnModelConstants

object CamundaDecisionJackson {

  object Namespaces {
    const val NS_DMN_13 = DmnModelConstants.DMN13_NS
    const val NS_CAMUNDA = DmnModelConstants.CAMUNDA_NS
    const val NS_DMNDI = "https://www.omg.org/spec/DMN/20191111/DMNDI/"
    const val NS_DC = "http://www.omg.org/spec/DMN/20180521/DC/"
    const val NS_DI = "http://www.omg.org/spec/DMN/20180521/DI/"
  }


//    <definitions xmlns="https://www.omg.org/spec/DMN/20191111/MODEL/" xmlns:dmndi="https://www.omg.org/spec/DMN/20191111/DMNDI/" xmlns:dc="http://www.omg.org/spec/DMN/20180521/DC/" id="Definitions_1ijak9h" name="DRD" namespace="http://camunda.org/schema/1.0/dmn">
//    <decision id="Decision_1hwv7ki" name="Decision 1">
//    <decisionTable id="DecisionTable_0cetvgc">
//    <input id="Input_1">
//    <inputExpression id="InputExpression_1" typeRef="string">
//    <text></text>
//    </inputExpression>
//    </input>
//    <output id="Output_1" typeRef="string" />
//    </decisionTable>
//    </decision>
//    <dmndi:DMNDI>
//    <dmndi:DMNDiagram>
//    <dmndi:DMNShape dmnElementRef="Decision_1hwv7ki">
//    <dc:Bounds height="80" width="180" x="160" y="100" />
//    </dmndi:DMNShape>
//    </dmndi:DMNDiagram>
//    </dmndi:DMNDI>
//    </definitions>

  fun fromXml(dmnXml: DmnXml) = dmnXmlMapper.readValue<DefinitionsXml>(dmnXml)

  fun fromModelInstance(modelInstance:DmnModelInstance) = fromXml(Dmn.convertToString(modelInstance))

  // TODO: configure xmlMapper to use correct xml version and encoding
  fun toXml(model: DefinitionsXml) = """
<?xml version="1.0" encoding="UTF-8"?>
${dmnXmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(model)}
  """.trimIndent()



  private val dmnXmlMapper = XmlMapper(JacksonXmlModule().apply {
    setDefaultUseWrapper(false)
  }).registerKotlinModule()
    .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  enum class TypeRef {
    string,
    boolean,
    integer,
    long,
    double,
    date
  }
}
