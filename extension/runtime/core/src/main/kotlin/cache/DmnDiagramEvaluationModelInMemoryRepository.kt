package io.holunda.decision.runtime.cache

import io.holunda.decision.model.api.Id
import io.holunda.decision.model.api.evaluation.DmnDiagramEvaluationModel
import io.holunda.decision.model.api.evaluation.DmnDiagramEvaluationModelRepository
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object DmnDiagramEvaluationModelInMemoryRepository : DmnDiagramEvaluationModelRepository {
  private val id: String = UUID.randomUUID().toString()
  private val cache: ConcurrentHashMap<String, DmnDiagramEvaluationModel> = ConcurrentHashMap()

  override fun save(model: DmnDiagramEvaluationModel): DmnDiagramEvaluationModel {
    cache[model.decisionDefinitionId] = model
    return model
  }

  override fun findById(diagramId: Id): Optional<DmnDiagramEvaluationModel> = cache.values
    .find { it.diagramId == diagramId }?.let { Optional.of(it) } ?: Optional.empty()

  override fun findAll(): Collection<DmnDiagramEvaluationModel> {
    return cache.values
  }

  override fun clear() {
    cache.clear()
  }

  override fun toString(): String {
    return "DmnDiagramEvaluationModelInMemoryRepository(id='$id', cache=$cache)"
  }


}
