package io.holunda.decision.example.camundacon2020.fn

import io.holunda.decision.example.camundacon2020.CamundaConExampleTestHelper
import io.holunda.decision.model.CamundaDecisionModel
import mu.KLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class DmnRepositoryLoaderTest {
  companion object : KLogging()

  private val loader = DmnRepositoryLoader(properties = CamundaConExampleTestHelper.properties)

  @Test
  fun readDiagram() {
    val diagramName = "legal_age.dmn"

    val diagram = loader.loadDiagram(diagramName)

    logger.info { CamundaDecisionModel.render(diagram) }

    assertThat(diagram).isNotNull
  }
}
