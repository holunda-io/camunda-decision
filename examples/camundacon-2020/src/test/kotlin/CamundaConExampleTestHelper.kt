package io.holunda.decision.example.camundacon2020

import java.nio.file.Paths

object CamundaConExampleTestHelper {

  /**
   * Abs path to project folder derived from properties location.
   */
  val projectRoot = Paths.get(
    CamundaConExampleTestHelper::class.java.protectionDomain.codeSource.location.toURI()
  ).resolve(
    Paths.get("../..")
  ).toFile()

  val properties = CamundaConExampleProperties(repository = projectRoot.resolve("repository"))
}


fun main() {
  println(CamundaConExampleTestHelper.properties)
}
