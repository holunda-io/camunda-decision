package io.holunda.decision.example.camundacon2020.fn

import io.holunda.decision.example.camundacon2020.CamundaConExampleProperties
import io.holunda.decision.model.CamundaDecisionModel
import mu.KLogging
import org.junit.Test
import java.io.File


class DmnRepositoryLoaderTest {
  companion object : KLogging()

  private val loader = DmnRepositoryLoader(properties = CamundaConExampleProperties(
    repository = File("/Users/jangalinski/IdeaProjects/holunda/camunda-decision/examples/camundacon-2020/repository") // FIXME: no absPath
  ))

  @Test
  fun readDiagram() {
    val diagramName = "legal_age.dmn"

    val diagram = loader.loadDiagram(diagramName)

    logger.info { CamundaDecisionModel.render(diagram) }
  }
}
