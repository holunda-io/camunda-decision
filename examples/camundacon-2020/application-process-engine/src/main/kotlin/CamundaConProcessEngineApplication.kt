package io.holunda.decision.example.camundacon2020.process

import io.holunda.decision.runtime.spring.EnableCamundaDecision
import mu.KLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

fun main(args:Array<String>) : Unit = runApplication<CamundaConProcessEngineApplication>(*args)
  .let { Unit }

@SpringBootApplication
@EnableCamundaDecision
class CamundaConProcessEngineApplication {
  companion object : KLogging()

}
