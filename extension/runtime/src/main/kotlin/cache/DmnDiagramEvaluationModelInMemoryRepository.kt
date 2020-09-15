package io.holunda.decision.runtime.cache

import io.holunda.decision.model.api.Id
import io.holunda.decision.model.api.evaluation.DmnDiagramEvaluationModel
import java.util.*
import kotlin.collections.HashMap

class DmnDiagramEvaluationModelInMemoryRepository(
  private val cache: MutableMap<String, DmnDiagramEvaluationModel> = HashMap()
) : DmnDiagramEvaluationModelRepository {

  override fun save(model: DmnDiagramEvaluationModel): DmnDiagramEvaluationModel {
    cache[model.decisionDefinitionId] = model
    return model
  }

  override fun findById(diagramId: Id): Optional<DmnDiagramEvaluationModel> = cache.values
    .find { it.diagramId == diagramId }?.let { Optional.of(it) }?: Optional.empty()

  override fun findAll(): Collection<DmnDiagramEvaluationModel> {
    return cache.values
  }
}
