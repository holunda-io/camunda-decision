package io.holunda.decision.reader

import io.holunda.decision.lib.test.CamundaDecisionTestLib
import org.junit.Test

class CamundaDecisionReaderTest {

  val modelInstance = CamundaDecisionTestLib.readModel("/dummy_dmn.dmn")

  @Test
  fun playground() {
    println(modelInstance)
  }
}
