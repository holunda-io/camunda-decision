package io.holunda.decision.model

import io.holunda.decision.model.element.DmnDecisionTable
import io.holunda.decision.model.io.DmnReader
import org.camunda.bpm.model.dmn.DmnModelInstance

object CamundaDecisionModel {

  fun readDecisionTable(dmnModelInstance: DmnModelInstance, key:String? = null) : DmnDecisionTable = DmnReader.readDecisionTable(dmnModelInstance, key)


}
