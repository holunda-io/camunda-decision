package io.holunda.decision.example.camundacon2020.domain

import io.holunda.decision.example.camundacon2020.domain.CustomerSex.female
import io.holunda.decision.example.camundacon2020.domain.CustomerSex.male
import org.springframework.stereotype.Component
import java.util.*

interface CustomerRepository {

  fun loadById(customerId: String) : CustomerEntity = findById(customerId).orElseThrow { IllegalArgumentException("customer not found: $customerId") }

  fun findById(customerId: String) : Optional<CustomerEntity>

  fun findAll() : List<CustomerEntity>

}

@Component
class DummyCustomerRepository : CustomerRepository{
  private val data = listOf(
    CustomerEntity(id = "1", name = "Peter", age = 19, sex = male, country = "USA", state = "Alabama"),
    CustomerEntity(id = "2", name = "Paul", age = 17, sex = male, country = "Pakistan"),
    CustomerEntity(id = "3", name = "Mary", age = 17, sex = female, country = "Pakistan"),
  ).associateBy { it.id }

  override fun findById(customerId: String)  = Optional.ofNullable(data[customerId])

  override fun findAll(): List<CustomerEntity> = data.values.toList()
}

enum class CustomerSex {
  male, female
}

data class CustomerEntity(
  val id: String,
  val name: String,
  val age: Int,
  val sex: CustomerSex,
  val country: String,
  val state: String? = null
)


