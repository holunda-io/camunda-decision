package io.holunda.decision.xml

import entry.DecisionTableXml
import entry.RuleXml
import io.holunda.decision.lib.test.CamundaDecisionTestLib
import io.holunda.decision.lib.test.CamundaDecisionTestLib.readText
import io.holunda.decision.model.xml.CamundaDecisionXml
import io.holunda.decision.model.xml.element.*
import io.holunda.decision.model.xml.element.HeaderXml.InputXml
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.model.dmn.HitPolicy
import org.junit.Test


internal class CamundaDecisionXmlTest {


  @Test
  fun `create via fluent kotlin`() {
    val definitions = DefinitionsXml(
      id = "1",
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
                  typeRef = CamundaDecisionXml.TypeRef.string,
                  text = """"Hello""""
                )
              )
            ),
            outputs = listOf(HeaderXml.OutputXml(id = "o1", typeRef = CamundaDecisionXml.TypeRef.string, name = "ky", label = "o1")),
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
                typeRef = CamundaDecisionXml.TypeRef.boolean
              )
            )),
            outputs = listOf(
              HeaderXml.OutputXml(
                id = "1d111",
                label = "Das ists es",
                typeRef = CamundaDecisionXml.TypeRef.long,
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

    val xml = CamundaDecisionXml.toXml(definitions)

    val d2 = CamundaDecisionXml.fromXml(xml)

    println("object: $definitions")
    println("""
<?xml version="1.0" encoding="UTF-8"?>
$xml
    """)

    assertThat(d2).isEqualTo(definitions)
  }


  @Test
  fun `read xml from file`() {
    val fileContent = readText("decision2_uses_decision1.dmn")

    println(fileContent)

    val model = CamundaDecisionXml.fromXml(fileContent)

    println(model)

    assertThat(model.id).isEqualTo("Definitions_1kwany6")
    assertThat(model.name).isEqualTo("DRD")
    assertThat(model.namespace).isEqualTo("http://camunda.org/schema/1.0/dmn")
    assertThat(model.exporter).isEqualTo("Camunda Modeler")
    assertThat(model.exporterVersion).isEqualTo("4.2.0")

    println("""

===============================

""".trimIndent())

    println(CamundaDecisionXml.toXml(model))
  }

  @Test
  fun `read 4 tables`() {
    val dmn = CamundaDecisionXml.fromXml(CamundaDecisionTestLib.DmnTestResource.FOUR_TABLES.load())


    println(dmn)

    println("""

${CamundaDecisionXml.toXml(dmn)}

    """.trimIndent())
  }
}
