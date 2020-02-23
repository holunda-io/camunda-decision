package io.holunda.decision.model

object CamundaDecisionModel {

  interface ColumnDefinition {
    val key:String
    val label:String
    val type:DataType
  }

}
