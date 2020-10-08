package io.holunda.decision.example.camundacon2020.domain

import org.springframework.stereotype.Component
import java.util.*

interface ProductRepository {

  fun loadById(productId: String) : ProductEntity = findById(productId).orElseThrow { IllegalArgumentException("product not found: $productId") }

  fun findById(productId: String) : Optional<ProductEntity>

  fun findAll() : List<ProductEntity>

}

@Component
class DummyProductRepository : ProductRepository {

  private val data = listOf(
    ProductEntity(id = "1001", name = "Car", size = ProductSize.XL),
    ProductEntity(id = "1002", name = "Car", size = ProductSize.S),
    ProductEntity(id = "1003", name = "Motorcycle", size = ProductSize.S),
    ProductEntity(id = "1004", name = "Motorcycle", size = ProductSize.L),
  ).map { it.id to it}.toMap()

  override fun findById(productId: String): Optional<ProductEntity> = Optional.ofNullable(data[productId])

  override fun findAll(): List<ProductEntity> = data.values.toList()

}

enum class ProductSize {
  S,M,L,XL
}

data class ProductEntity(
  val id: String,
  val name: String,
  val size: ProductSize
)
