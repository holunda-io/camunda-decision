package io.holunda.decision.runtime.deployment

import io.holunda.decision.model.api.DmnDiagramConverter
import io.holunda.decision.model.api.element.DmnDiagram
import org.camunda.bpm.engine.RepositoryService
import org.camunda.bpm.engine.repository.Deployment

/**
 * Deploys DmnDiagram using RepositoryService.
 */
class DmnDeploymentService(
  private val repositoryService: RepositoryService,
  private val diagramConverter: DmnDiagramConverter
) {

  /**
   * Vararg deploy for convenience.
   * @see deploy
   */
  fun deploy(vararg diagrams: DmnDiagram) = deploy(diagrams.toList())

  /**
   * Deploys all given diagrams in one deployment.
   */
  fun deploy(diagrams: List<DmnDiagram>): Deployment {
    val deploymentBuilder = repositoryService.createDeployment()



    diagrams.map { it.resourceName to diagramConverter.toXml(it) }
      .forEach {
        deploymentBuilder.addString(it.first, it.second)
      }

    return deploymentBuilder.deploy()!!
  }
}
