package io.holunda.decision.generator.builder

import io.holunda.decision.model.converter.DmnDiagramConverter
import io.holunda.decision.model.element.DmnDiagram
import org.camunda.bpm.engine.repository.DeploymentBuilder
import org.camunda.bpm.engine.test.ProcessEngineRule

fun DeploymentBuilder.addModelInstance(dmnDiagram: DmnDiagram) = this.addModelInstance(
  dmnDiagram.resourceName,
  DmnDiagramConverter.toModelInstance(dmnDiagram)
)

fun ProcessEngineRule.manageDmnDeployment(vararg dmnDiagrams:DmnDiagram) {
  val builder = this.repositoryService.createDeployment()

  dmnDiagrams.forEach { builder.addModelInstance(it) }

  this.manageDeployment(builder.deploy())
}
