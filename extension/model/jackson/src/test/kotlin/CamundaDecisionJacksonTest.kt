package io.holunda.decision.model.jackson

import io.holunda.decision.lib.test.CamundaDecisionTestLib
import io.holunda.decision.lib.test.CamundaDecisionTestLib.readText
import io.holunda.decision.model.jackson.element.*
import io.holunda.decision.model.jackson.element.HeaderXml.InputXml
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.HitPolicy
import org.junit.Test


internal class CamundaDecisionJacksonTest {


  @Test
  fun `create via fluent kotlin`() {
    val definitions = DefinitionsXml(
      id = "d1",
      name = "DRD1",

      decisions = listOf(
        DecisionXml(
          id = "1",
          name = "Decision 1",
          decisionTable = DecisionTableXml(
            id = "dt1",
            hitPolicy = HitPolicy.FIRST,
            inputs = listOf(
              InputXml(
                id = "i1",
                label = "A1",
                inputExpression = InputXml.InputExpressionXml(
                  id = "ie1",
                  typeRef = CamundaDecisionJackson.TypeRef.string,
                  text = """"Hello""""
                )
              )
            ),
            outputs = listOf(HeaderXml.OutputXml(id = "o1", typeRef = CamundaDecisionJackson.TypeRef.string, name = "ky", label = "o1")),
            rules = listOf(RuleXml(
              id = "r1",
              inputEntries = listOf(RuleXml.EntryXml(id = "1", text = "\"xxxx\"")),
              outputEntries = listOf(RuleXml.EntryXml("iii1", "")

              ))
            )

          ),
          informationRequirements = listOf()

        ),
        DecisionXml(
          id = "2",
          name = "Decision 2",
          informationRequirements = listOf(InformationRequirementXml(
            id = "graph_dt1_dt2",
            requiredDecision = InformationRequirementXml.RequiredDecisionXml(href = "#dt1")
          )),
          decisionTable = DecisionTableXml(
            id = "dt2",
            hitPolicy = HitPolicy.FIRST,
            inputs = listOf(InputXml(
              id = "i88",
              label = "Hello",
              inputExpression = InputXml.InputExpressionXml(
                text = "xx",
                id = "111111",
                typeRef = CamundaDecisionJackson.TypeRef.boolean
              )
            )),
            outputs = listOf(
              HeaderXml.OutputXml(
                id = "1d111",
                label = "Das ists es",
                typeRef = CamundaDecisionJackson.TypeRef.long,
                name = "res"
              )
            ),
            rules = listOf(
              RuleXml(
                id = "98z7",
                inputEntries = listOf(
                  RuleXml.EntryXml("1ff2", "")
                ),
                outputEntries = listOf(
                  RuleXml.EntryXml("hh7h","")
                )
              )
            )
          )
        )
      ),
      layout = LayoutXml(
        dmnDiagram = LayoutXml.DiagramLayoutXml(
          dmnShapes = listOf(LayoutXml.DiagramLayoutXml.DmnShapeXml(
            dmnElementRef = "c1",
            bounds = LayoutXml.DiagramLayoutXml.DmnShapeXml.BoundsXml(1,2,3,4)
          )),
          dmnEdges = listOf(LayoutXml.DiagramLayoutXml.DmnEdgeXml(dmnElementRef = "c1", waypoints = listOf(LayoutXml.DiagramLayoutXml.DmnEdgeXml.WaypointXml(1,2))))
        )
      )
    )

//    <?xml version="1.0" encoding="UTF-8"?>
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


    val xml = CamundaDecisionJackson.toXml(definitions)
    println(xml)

    Dmn.validateModel(Dmn.readModelFromStream(xml.byteInputStream()))

    val d2 = CamundaDecisionJackson.fromXml(xml)

    println("object: $definitions")
    println(CamundaDecisionJackson.toXml(definitions))

    assertThat(d2).isEqualTo(definitions)
  }


  @Test
  fun `read xml from file`() {
    val fileContent = readText("decision2_uses_decision1.dmn")

    println(fileContent)

    val model = CamundaDecisionJackson.fromXml(fileContent)

    println(model)

    assertThat(model.id).isEqualTo("Definitions_1kwany6")
    assertThat(model.name).isEqualTo("DRD")
    assertThat(model.namespace).isEqualTo("http://camunda.org/schema/1.0/dmn")
    assertThat(model.exporter).isEqualTo("Camunda Modeler")
    assertThat(model.exporterVersion).isEqualTo("4.2.0")

    println("""

===============================

""".trimIndent())

    println(CamundaDecisionJackson.toXml(model))
  }

  @Test
  fun `read 4 tables`() {
    val dmn = CamundaDecisionJackson.fromXml(CamundaDecisionTestLib.DmnTestResource.FOUR_TABLES.load())


    println(dmn)

    println("""

${CamundaDecisionJackson.toXml(dmn)}

    """.trimIndent())
  }

  @Test
  fun `read simulation`() {
    val simulationDmn = CamundaDecisionJackson.fromXml(CamundaDecisionTestLib.DmnTestResource.DISHES_AND_DRINKS.load())

    assertThat(simulationDmn).isNotNull()
  }

  @Test
  fun `read table with version tag`() {
    val xml = CamundaDecisionTestLib.DmnTestResource.SINGLE_TABLE.load()
    val dmn = CamundaDecisionJackson.fromXml(xml)

    assertThat(dmn.decisions.first().versionTag).isEqualTo("666")
    assertThat(dmn.decisions.first().decisionTable.rules.first().description).isEqualTo("this is great")
  }
}
