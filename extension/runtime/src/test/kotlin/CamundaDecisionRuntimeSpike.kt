package io.holunda.decision.runtime

import io.holunda.decision.lib.test.CamundaDecisionTestLib
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.test.Deployment
import org.camunda.bpm.engine.variable.Variables
import org.junit.Rule
import org.junit.Test

@Deployment(resources = ["dmn/drd-dec1-dec2.dmn"])
class CamundaDecisionRuntimeSpike {

  val drd = "multiple_decisions";

  @get:Rule
  val camunda = CamundaDecisionTestLib.camunda()

  val decisionService by lazy {
    camunda.decisionService
  }

  @Test
  fun `evaluate decision`() {
    assertThat(camunda.repositoryService.createDecisionDefinitionQuery().decisionDefinitionKey("decision1").list()).isNotNull
    assertThat(camunda.repositoryService.createDecisionDefinitionQuery().decisionDefinitionKey("decision2").singleResult()).isNotNull


    val result = decisionService.evaluateDecisionTableByKey("decision1", Variables.putValue("foo", true)).singleResult

    println(result)

    val r = decisionService.evaluateDecisionTableByKey("decision2",
      Variables.putValue("foo", true).putValue("bar", true)).singleResult

    println("" + r.getSingleEntry())
  }
}
