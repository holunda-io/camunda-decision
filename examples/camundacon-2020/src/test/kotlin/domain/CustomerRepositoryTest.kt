package domain

import io.holunda.decision.example.camundacon2020.domain.CustomerRepository
import io.holunda.decision.example.camundacon2020.domain.DummyCustomerRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import java.lang.IllegalArgumentException


class CustomerRepositoryTest {

  private val repository: CustomerRepository = DummyCustomerRepository()

  @Test
  fun `findAll gives 3`() {
    val all = repository.findAll()

    assertThat(all).hasSize(3)
  }

  @Test
  fun `find by id not exists`() {
    assertThat(repository.findById("xxx")).isEmpty

    assertThatThrownBy { repository.loadById("xxx") }.isInstanceOf(IllegalArgumentException::class.java)
  }
}
