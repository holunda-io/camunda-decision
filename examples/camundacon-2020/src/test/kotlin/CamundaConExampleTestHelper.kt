package io.holunda.decision.example.camundacon2020

import java.io.File

object CamundaConExampleTestHelper {

  /**
   * Abs path to project folder derived from properties location.
   */
  val projectRoot = File(CamundaConExampleTestHelper.javaClass.getResource("/application.yml").toURI()).absolutePath.substringBefore("camundacon-2020") + "camundacon-2020"

  val properties = CamundaConExampleProperties(repository = File("$projectRoot/repository"))
}


fun main(args: Array<String>) : Unit {
  println(CamundaConExampleTestHelper.properties)
}
