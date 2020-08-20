package io.holunda.decision.model.converter

import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.booleanInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.OutputDefinitions.longOutput
import io.holunda.decision.model.api.data.DmnHitPolicy
import io.holunda.decision.model.api.element.*
import io.holunda.decision.model.layout.DmnDiagramLayoutWorker
import io.holunda.decision.model.layout.DmnDiagramLayoutWorker.*
import io.holunda.decision.model.layout.DmnDiagramLayoutWorker.Point.Companion.distance
import io.holunda.decision.model.layout.shortest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DmnDiagramLayoutWorkerTest {

  @Test
  fun `layout for single table`() {
    val fooIn = booleanInput("foo", "Foo")
    val barOut = longOutput("bar", "The Result")

    val diagram = DmnDiagram(
      DmnDecisionTable(
        decisionDefinitionKey = "oneTable",
        name = "One Table",
        hitPolicy = DmnHitPolicy.COLLECT_SUM,
        header = DmnDecisionTable.Header(
          inputs = listOf(fooIn),
          outputs = listOf(barOut)
        ),
        rules = DmnRuleList(
          DmnRule(id = "r1", inputs = listOf(fooIn.toEntry("true")), outputs = listOf(barOut.toEntry("1"))),
          DmnRule(id = "r2", inputs = listOf(fooIn.toEntry("true")), outputs = listOf(barOut.toEntry("2"))),
          DmnRule(id = "r3", inputs = listOf(fooIn.toEntry("true")), outputs = listOf(barOut.toEntry("3"))),
          DmnRule(id = "r4", inputs = listOf(fooIn.toEntry("false")), outputs = listOf(barOut.toEntry("4")))
        )
      )
    )

    val layout = DmnDiagramLayoutWorker(diagram.decisionDefinitionKeys, diagram.informationRequirements).layout()

    assertThat(layout.informationRequirementLayouts).isEmpty()
    assertThat(layout.decisionTableLayouts).isNotEmpty().hasSize(1)

    val tableLayout = layout.decisionTableLayouts.first()

    assertThat(tableLayout.decisionDefinitionKey).isEqualTo("oneTable")
    assertThat(tableLayout.box).isEqualTo(Box(key = "oneTable", x=160, y= 160, width = 180, height = 80))
  }

  @Test
  fun `foo depends on bar`() {
    val ir = DmnDecisionTable.InformationRequirement("bar", "foo")
    val layout = DmnDiagramLayoutWorker(setOf("foo", "bar"), setOf(ir)).layout_Old()

    println(layout)
  }

  @Test
  fun `box coordinates`() {
    val box = Box(key = "foo", x = 100, y = 200)

    assertThat(box.points.tl).isEqualTo(Point(100, 200))
    assertThat(box.points.tr).isEqualTo(Point(280 , 200))
    assertThat(box.points.bl).isEqualTo(Point(100 , 280))
    assertThat(box.points.br).isEqualTo(Point(280 , 280))

    assertThat(box.points.cl).isEqualTo(Point(100 , 240))
    assertThat(box.points.cr).isEqualTo(Point(280 , 240))
    assertThat(box.points.ct).isEqualTo(Point(190 , 200))
    assertThat(box.points.cb).isEqualTo(Point(190 , 280))
  }

  @Test
  fun `is corner`() {
    val box = Box(key = "foo", x = 0, y = 0, width = 10, height = 10)
    assertThat(box.isCorner(Point(0,0))).isTrue()
    assertThat(box.isCorner(Point(0,10))).isTrue()
    assertThat(box.isCorner(Point(10,0))).isTrue()
    assertThat(box.isCorner(Point(10,10))).isTrue()
    assertThat(box.isCorner(Point(5,10))).isFalse()
  }

  @Test
  fun `edges br to ul`() {
    val foo = Box(key = "foo", x = 0, y = 0, width = 10, height = 10)
    val bar = Box(key = "bar", x = 20, y = 20, width = 10, height = 10)

    assertThat(shortest(foo,bar)).isEqualTo(Edge("foo", Point(10, 10), Point(20, 20)))
  }

  @Test
  fun `edges cr to cl`() {
    val foo = Box(key = "foo", x = 0, y = 0, width = 10, height = 10)
    val bar = Box(key = "bar", x = 20, y = 0, width = 10, height = 10)

    assertThat(shortest(foo,bar)).isEqualTo(Edge("foo", Point(10, 5), Point(20,5)))
  }
}

class PointTest {

  val p00 = Point(0, 0)
  val p34 = Point(3,4)
  val p68 = Point(6,8)


  @Test
  fun `distance between two points`() {
    assertThat(distance(p00,p00)).isEqualTo(0.0)
    assertThat(distance(p00,p34)).isEqualTo(5.0)
    assertThat(distance(p68,p34)).isEqualTo(5.0)
  }
}
