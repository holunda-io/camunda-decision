package io.holunda.decision.model.api.evaluation

import io.holunda.decision.model.api.Id
import java.util.*

/**
 * Access to evaluation models.
 */
interface DmnDiagramEvaluationModelRepository {

  /**
   * @param model - the model to save
   * @return the saved model
   */
  fun save(model: DmnDiagramEvaluationModel) : DmnDiagramEvaluationModel

  /**
   * @param diagramId the model to load
   * @return model for given id
   * @throws NoSuchElementException
   */
  fun loadForId(diagramId: Id) : DmnDiagramEvaluationModel = findById(diagramId).orElseThrow()

  /**
   * @param diagramId the id of the diagram to find
   * @return optional model
   */
  fun findById(diagramId: Id) : Optional<DmnDiagramEvaluationModel>

  /**
   * @return all models
   */
  fun findAll(): Collection<DmnDiagramEvaluationModel>
}
