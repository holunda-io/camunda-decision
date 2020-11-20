package io.holunda.decision.example.camundacon2020.rest

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.decision.example.camundacon2020.IsAdultDefinitions
import io.holunda.decision.example.camundacon2020.ShipmentDefinitions
import io.holunda.decision.example.camundacon2020.domain.CustomerEntity
import io.holunda.decision.example.camundacon2020.domain.CustomerRepository
import io.holunda.decision.example.camundacon2020.domain.ProductRepository
import io.holunda.decision.model.api.CamundaDecisionEvaluationService
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.set
import io.holunda.decision.model.api.evaluation.CamundaDecisionEvaluationRequest.Companion.request
import io.holunda.decision.model.api.evaluation.CamundaDecisionEvaluationResult
import mu.KLogging
import org.camunda.bpm.engine.variable.Variables
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/evaluation")
class DmnEvaluationController(
  private val customerRepository: CustomerRepository,
  private val productRepository: ProductRepository,
  private val evaluationService: CamundaDecisionEvaluationService
) {
  companion object : KLogging()

  @GetMapping(path = ["/isCustomerAdult/{customerId}"])
  fun evaluateIsAdult(@PathVariable("customerId") customerId: String): ResponseEntity<CamundaDecisionEvaluationResult> {
    val customer = customerRepository.loadById(customerId)

    val inputs = CamundaBpmData.builder()
      .set(IsAdultDefinitions.inCustomerId, customer.id)
      .set(IsAdultDefinitions.inCustomerAge, customer.age)
      .set(IsAdultDefinitions.inCustomerSex, customer.sex.name)
      .set(IsAdultDefinitions.inCustomerCountry, customer.country)
      .set(IsAdultDefinitions.inCustomerState, customer.state?:"")
      .build()

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

  @GetMapping(path = ["/isShipmentAllowed"])
  fun evaluateLegalAndProduct(
    @RequestParam("customerId") customerId: String,
    @RequestParam("productId") productId: String
  ) : ResponseEntity<CamundaDecisionEvaluationResult> {

    val customer = customerRepository.loadById(customerId)
    val product = productRepository.loadById(productId)

    return ResponseEntity.ok(evaluationService.evaluateDiagram(
      request(
        "legal_and_product",
        CamundaBpmData.builder()
          // first
          .set(IsAdultDefinitions.inCustomerId, customer.id)
          .set(IsAdultDefinitions.inCustomerAge, customer.age)
          .set(IsAdultDefinitions.inCustomerCountry, customer.country)
          .set(IsAdultDefinitions.inCustomerState, customer.state?:"")
          .set(IsAdultDefinitions.inCustomerSex, customer.sex.name)
          // second
          .set(ShipmentDefinitions.inId, product.id)
          .set(ShipmentDefinitions.inName, product.name)
          .set(ShipmentDefinitions.inSize, product.size.name)
          .build()
      )
    ))
  }

  data class CustomerIsAdultDto(
    val customer: CustomerEntity,
    val isAdultResult: CamundaDecisionEvaluationResult
  )

}
