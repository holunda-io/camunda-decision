package io.holunda.decision.example.camundacon2020.app.dmn

import io.holunda.decision.runtime.spring.EnableCamundaDecision
import mu.KLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener

fun main(args:Array<String>) : Unit = runApplication<CamundaConDmnRepositoryApplication>(*args)
  .let { Unit }

@SpringBootApplication
@EnableCamundaDecision
class CamundaConDmnRepositoryApplication {
  companion object : KLogging()

  @EventListener
  fun onStart(evt: ApplicationReadyEvent) {
    logger.info { "up and running!" }
  }
}
