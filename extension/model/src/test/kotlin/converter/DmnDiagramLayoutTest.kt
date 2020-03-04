package io.holunda.decision.model.converter

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DmnDiagramLayoutTest {


  @Test
  fun `box coordinates`() {
    val box = DmnDiagramLayout.Box(key = "foo", x = 100, y = 200)

    assertThat(box.tl).isEqualTo(100 to 200)
    assertThat(box.tr).isEqualTo(280 to 200)
    assertThat(box.bl).isEqualTo(100 to 280)
    assertThat(box.br).isEqualTo(280 to 280)

    assertThat(box.cl).isEqualTo(100 to 240)
    assertThat(box.cr).isEqualTo(280 to 240)
    assertThat(box.ct).isEqualTo(190 to 200)
    assertThat(box.cb).isEqualTo(190 to 280)
  }
}
