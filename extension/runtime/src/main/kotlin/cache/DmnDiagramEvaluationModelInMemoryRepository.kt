package io.holunda.decision.runtime.cache

import io.holunda.decision.model.api.evaluation.DmnDiagramEvaluationModel

class DmnDiagramEvaluationModelInMemoryRepository(
  private val cache: MutableMap<String, DmnDiagramEvaluationModel> = HashMap()
) : DmnDiagramEvaluationModelRepository {
  override fun save(model: DmnDiagramEvaluationModel) {
    cache[model.decisionDefinitionId] = model
  }

  override fun findAll(): Collection<DmnDiagramEvaluationModel> {
    return cache.values
  }
}
