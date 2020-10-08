package cache

import io.holunda.decision.model.api.DmnDiagramConverter
import io.holunda.decision.runtime.cache.RefreshDmnDiagramEvaluationModelCacheConfiguration
import org.camunda.bpm.engine.RepositoryService
import org.camunda.bpm.model.dmn.Dmn

enum class DmnModelType {
  GRAPH {
    override fun retrieveModelData(repositoryService: RepositoryService, diagramConverter: DmnDiagramConverter, key: String): RefreshDmnDiagramEvaluationModelCacheConfiguration.ModelData {
      val def = repositoryService.createDecisionRequirementsDefinitionQuery()
        .decisionRequirementsDefinitionKey(key)
        .singleResult()

      val tableKeysToDefinitionIds = repositoryService.createDecisionDefinitionQuery()
        .decisionRequirementsDefinitionId(def.id)
        .list().map { it.key to it.id }.toMap()

      val diagram = diagramConverter.fromXml(Dmn.convertToString(repositoryService.getDmnModelInstance(tableKeysToDefinitionIds.values.first())))

      return RefreshDmnDiagramEvaluationModelCacheConfiguration.ModelData(
          tableKeysToDefinitionIds = tableKeysToDefinitionIds,
          diagram = diagram,
          resultDefinitionId = tableKeysToDefinitionIds[diagram.resultTable.decisionDefinitionKey]!!,
          deploymentId = def.deploymentId
      )
    }

  },
  TABLE {
    override fun retrieveModelData(repositoryService: RepositoryService, diagramConverter: DmnDiagramConverter, key: String): RefreshDmnDiagramEvaluationModelCacheConfiguration.ModelData {
      val def = repositoryService.createDecisionDefinitionQuery()
        .decisionDefinitionKey(key)
        .latestVersion()
        .singleResult()
      val tableKeysToDefinitionIds = mapOf(def.key to def.id)
      val diagram = diagramConverter.fromXml(Dmn.convertToString(repositoryService.getDmnModelInstance(def.id)))
      return RefreshDmnDiagramEvaluationModelCacheConfiguration.ModelData(
          tableKeysToDefinitionIds = tableKeysToDefinitionIds,
          diagram = diagram,
          resultDefinitionId = tableKeysToDefinitionIds[diagram.resultTable.decisionDefinitionKey]!!,
          deploymentId = def.deploymentId
      )
    }
  },
  ;

  internal abstract fun retrieveModelData(
      repositoryService: RepositoryService,
      diagramConverter: DmnDiagramConverter,
      key: String): RefreshDmnDiagramEvaluationModelCacheConfiguration.ModelData
}
