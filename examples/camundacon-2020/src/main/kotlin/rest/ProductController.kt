package io.holunda.decision.example.camundacon2020.rest

import io.holunda.decision.example.camundacon2020.domain.ProductEntity
import io.holunda.decision.example.camundacon2020.domain.ProductRepository
import org.camunda.bpm.engine.impl.persistence.entity.ResourceEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
  private val repository: ProductRepository
) {

  @GetMapping
  fun findAll(): ResponseEntity<List<ProductEntity>> = ResponseEntity.ok(repository.findAll())

  @GetMapping(path = ["/{productId}"])
  fun findById(@PathVariable("productId") productId:String) = ResponseEntity.of(repository.findById(productId))

}
