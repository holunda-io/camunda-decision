package io.holunda.decision.runtime.cache

import io.holunda.decision.model.api.evaluation.DmnDiagramEvaluationModel

interface DmnDiagramEvaluationModelRepository {
  fun save(model: DmnDiagramEvaluationModel)
  fun findAll(): Collection<DmnDiagramEvaluationModel>
}
