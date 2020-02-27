package io.holunda.decision.model

import org.camunda.bpm.model.dmn.HitPolicy

data class DmnDecisionTable(
  val key:String,
  val name:String,
  val versionTag: String?,
  val hitPolicy:HitPolicy = HitPolicy.FIRST,
  val header: Header,
  val rules: List<DmnRule>
) {

   data class Header(val inputs: List<InputDefinition>, val outputs:List<OutputDefinition>)
}
