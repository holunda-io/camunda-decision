package io.holunda.decision.example.kotlin

import io.holunda.decision.runtime.spring.EnableCamundaDecision
import mu.KLogging
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener

fun main(args:Array<String>) : Unit = runApplication<DmnExampleKotlinApplication>(*args)
  .let { Unit }

@SpringBootApplication
@EnableCamundaDecision
class DmnExampleKotlinApplication {
  companion object : KLogging()

  @EventListener
  fun onStart(evt: PostDeployEvent) {
    logger.info { "up and running!" }
  }

}
