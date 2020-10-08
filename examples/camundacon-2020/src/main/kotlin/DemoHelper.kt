package io.holunda.decision.example.camundacon2020

import io.holunda.decision.example.camundacon2020.fn.DmnRepositoryLoader
import java.nio.file.Paths

object DemoHelper {
  val projectRoot = Paths.get(
    DemoHelper::class.java.protectionDomain.codeSource.location.toURI()
  ).resolve(
    Paths.get("../..")
  ).toFile()

  val properties = CamundaConExampleProperties(repository = projectRoot.resolve("repository"))

  val loader = DmnRepositoryLoader(properties)
}
