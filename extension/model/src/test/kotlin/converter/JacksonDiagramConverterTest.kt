package converter

import io.holunda.decision.lib.test.CamundaDecisionTestLib
import io.holunda.decision.model.CamundaDecisionModel
import io.holunda.decision.model.api.DmnDiagramConverter
import io.holunda.decision.model.converter.JacksonDiagramConverter
import org.junit.Test

internal class JacksonDiagramConverterTest {

  @Test
  fun `re-convert diagram read from dmn`() {
    val dmnXml = CamundaDecisionTestLib.DmnTestResource.DRD_DEC1_DEC2.load()

    val dmnDiagram = JacksonDiagramConverter().fromXml(dmnXml)

    println(dmnDiagram)

    println(CamundaDecisionModel.render(dmnDiagram))

    val dmnXmlConverted = JacksonDiagramConverter().toXml(dmnDiagram)

    println(dmnXmlConverted)
  }
}
