package io.holunda.decision.example.camundacon2020.rest

import io.holunda.decision.example.camundacon2020.IsAdultDefinitions
import io.holunda.decision.example.camundacon2020.domain.CustomerEntity
import io.holunda.decision.example.camundacon2020.domain.CustomerRepository
import io.holunda.decision.model.api.CamundaDecisionEvaluationService
import io.holunda.decision.model.api.evaluation.CamundaDecisionEvaluationRequest.Companion.request
import io.holunda.decision.model.api.evaluation.CamundaDecisionEvaluationResult
import mu.KLogging
import org.camunda.bpm.engine.variable.Variables
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/evaluation")
class DmnEvaluationController(
  private val customerRepository: CustomerRepository,
  private val evaluationService: CamundaDecisionEvaluationService
) {
  companion object : KLogging()

  @GetMapping(path = ["/isCustomerAdult/{customerId}"])
  fun evaluateIsAdult(@PathVariable("customerId") customerId: String): ResponseEntity<CamundaDecisionEvaluationResult> {
    val customer = customerRepository.loadById(customerId)

    val inputs = Variables.createVariables()
      .putValue(IsAdultDefinitions.inCustomerAge.key, customer.age)
      .putValue(IsAdultDefinitions.inCustomerSex.key, customer.sex)
      .putValue(IsAdultDefinitions.inCustomerCountry.key, customer.country)
      .putValue(IsAdultDefinitions.inCustomerState.key, customer.state)

    val isAdultResult = evaluationService.evaluateDiagram(request(
      IsAdultDefinitions.DiagramData.id,
      inputs
    ))


    val result = CustomerIsAdultDto(
      customer = customer,
      isAdultResult = isAdultResult
    )
    logger.info { "result for customer '$customerId': $result" }

    return ResponseEntity.ok(isAdultResult)
  }

  data class CustomerIsAdultDto(
    val customer: CustomerEntity,
    val isAdultResult: CamundaDecisionEvaluationResult
  )

}
