package io.holunda.decision.lib.test

import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance

object CamundaDecisionTestLib {
  fun readModel(resource: String): DmnModelInstance {
    val inputStream = CamundaDecisionTestLib::class.java.getResourceAsStream(resource)

    return Dmn.readModelFromStream(inputStream)!!
  }

}
