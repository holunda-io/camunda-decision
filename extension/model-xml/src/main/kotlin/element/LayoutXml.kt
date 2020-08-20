package io.holunda.decision.model.xml.element

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import io.holunda.decision.model.xml.CamundaDecisionXml.Namespaces

data class LayoutXml(
  @JacksonXmlProperty(localName = DiagramLayoutXml.LOCAL_NAME, namespace = NAMESPACE)
  val dmnDiagram: DiagramLayoutXml = DiagramLayoutXml()
) {
  companion object {
    const val LOCAL_NAME = "DMNDI"
    const val NAMESPACE = Namespaces.NS_DMNDI
  }

  data class DiagramLayoutXml(
    @JacksonXmlProperty(localName = "DMNShape", namespace = NAMESPACE)
    val dmnShapes: List<DmnShapeXml> = listOf(),

    @JacksonXmlProperty(localName = "DMNEdge", namespace = NAMESPACE)
    val dmnEdges: List<DmnEdgeXml> = listOf()
  ) {
    companion object {
      const val LOCAL_NAME = "DMNDiagram"
    }

    data class DmnShapeXml(
      @JacksonXmlProperty(isAttribute = true)
      val dmnElementRef: String,

      @JacksonXmlProperty(localName = "Bounds", namespace = Namespaces.NS_DC)
      val bounds: BoundsXml
    ) {
      data class BoundsXml(
        @JacksonXmlProperty(isAttribute = true)
        val height: Int,
        @JacksonXmlProperty(isAttribute = true)
        val width: Int,
        @JacksonXmlProperty(isAttribute = true)
        val x: Int,
        @JacksonXmlProperty(isAttribute = true)
        val y: Int
      )
    }

    data class DmnEdgeXml(
      @JacksonXmlProperty(isAttribute = true)
      val dmnElementRef: String,

      @JacksonXmlProperty(localName = "waypoint", namespace = Namespaces.NS_DI)
      val waypoints: List<WaypointXml>
    ) {
      data class WaypointXml(
        @JacksonXmlProperty(isAttribute = true)
        val x: Int,
        @JacksonXmlProperty(isAttribute = true)
        val y: Int
      )
    }
  }
}
