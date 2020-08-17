package io.holunda.decision.generator

import io.holunda.decision.model.api.element.DmnDiagram
import org.camunda.bpm.engine.repository.DeploymentBuilder
import org.camunda.bpm.engine.test.ProcessEngineRule

/**
 * Test helpers.
 */
object CamundaDecisionGeneratorHelper {

//  fun DeploymentBuilder.addModelInstance(dmnDiagram: DmnDiagram) = this.addModelInstance(
//    dmnDiagram.resourceName,
//    null // TODO
//  )

  fun ProcessEngineRule.manageDmnDeployment(vararg dmnDiagrams: DmnDiagram) {
    val builder = this.repositoryService.createDeployment()

//    dmnDiagrams.forEach { builder.addModelInstance(it) }

    this.manageDeployment(builder.deploy())
  }
}
