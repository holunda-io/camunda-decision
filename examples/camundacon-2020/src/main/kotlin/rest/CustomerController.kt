package io.holunda.decision.example.camundacon2020.rest

import io.holunda.decision.example.camundacon2020.domain.CustomerEntity
import io.holunda.decision.example.camundacon2020.domain.CustomerRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customers")
class CustomerController(
  private val repository: CustomerRepository
) {

  @GetMapping
  fun findAll() : ResponseEntity<List<CustomerEntity>> = ResponseEntity.ok(repository.findAll())

  @GetMapping(path = ["/{customerId}"])
  fun findById(@PathVariable("customerId") customerId:String) : ResponseEntity<CustomerEntity> = ResponseEntity.of(repository.findById(customerId))
}
