package io.holunda.decision.model

import io.holunda.decision.model.element.DmnDecisionTable
import io.holunda.decision.model.io.DmnReader
import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.impl.DmnModelConstants

typealias Id = String
typealias Key = String
typealias Name = String
typealias DecisionDefinitionKey = String
typealias VersionTag = String
typealias ConstraintMessage = String


object CamundaDecisionModel {

  const val BIODI_NS = "http://bpmn.io/schema/dmn/biodi/1.0"
  const val CAMUNDA_NS =  DmnModelConstants.CAMUNDA_NS

  fun readDecisionTable(dmnModelInstance: DmnModelInstance, key:String? = null) : DmnDecisionTable = DmnReader.readDecisionTable(dmnModelInstance, key)


}


