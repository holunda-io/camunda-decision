package io.holunda.decision.model

import org.camunda.bpm.model.dmn.DmnModelInstance

object CamundaDecisionModel {

  interface ColumnDefinition {
    val key:String
    val label:String
    val type:DataType
  }

  fun readDecisionTable(dmnModelInstance: DmnModelInstance) : DmnDecisionTable {
    TODO("no implemented")
  }
}
