package io.holunda.decision.model.converter

import io.holunda.decision.model.converter.DmnDiagramLayout.*
import io.holunda.decision.model.converter.DmnDiagramLayout.Point.Companion.distance
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DmnDiagramLayoutTest {


  @Test
  fun `foo depends on bar`() {
    val layout = DmnDiagramLayout(setOf("foo", "bar"), mapOf("foo" to setOf("bar"))).layout()

    println(layout)
  }

  @Test
  fun `box coordinates`() {
    val box = Box(key = "foo", x = 100, y = 200)

    assertThat(box.tl).isEqualTo(Point(100 , 200))
    assertThat(box.tr).isEqualTo(Point(280 , 200))
    assertThat(box.bl).isEqualTo(Point(100 , 280))
    assertThat(box.br).isEqualTo(Point(280 , 280))

    assertThat(box.cl).isEqualTo(Point(100 , 240))
    assertThat(box.cr).isEqualTo(Point(280 , 240))
    assertThat(box.ct).isEqualTo(Point(190 , 200))
    assertThat(box.cb).isEqualTo(Point(190 , 280))
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

    assertThat(shortest(foo,bar)).isEqualTo(Edge("foo", Point(10, 10), Point(20,20)))
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
