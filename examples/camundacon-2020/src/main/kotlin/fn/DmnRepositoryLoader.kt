package io.holunda.decision.example.camundacon2020.fn

import io.holunda.decision.example.camundacon2020.CamundaConExampleProperties
import io.holunda.decision.model.CamundaDecisionModel
import org.springframework.stereotype.Component

@Component
class DmnRepositoryLoader(
  val properties: CamundaConExampleProperties
) {

  fun loadDiagram(name: String) = CamundaDecisionModel.loadDiagram(properties.repository.resolve(name))
}
