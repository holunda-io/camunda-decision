package io.holunda.decision.example.camundacon2020

import io.holunda.decision.example.camundacon2020.fn.DmnRepositoryLoader
import io.holunda.decision.model.CamundaDecisionModel
import io.holunda.decision.runtime.spring.EnableCamundaDecision
import mu.KLogging
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.io.File

fun main(args: Array<String>): Unit = runApplication<CamundaConExampleApplication>(*args)
  .let { Unit }

@SpringBootApplication
@EnableProcessApplication
@EnableCamundaDecision
@ConfigurationPropertiesScan
class CamundaConExampleApplication {
  companion object : KLogging()


  @Bean
  fun onStart(properties: CamundaConExampleProperties, loader : DmnRepositoryLoader): CommandLineRunner = CommandLineRunner {
    logger.info { "loaded with: $properties" }


    logger.info { "diagram: \n\n  ${CamundaDecisionModel.render(loader.loadDiagram("legal_age.dmn"))}" }
  }
}

@ConfigurationProperties(prefix = "application")
@ConstructorBinding
data class CamundaConExampleProperties(
  val appName: String = "camundaCon2020",
  val org: String = "holunda",
  val repository: File
)
