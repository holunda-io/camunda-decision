package io.holunda.decision.model.io

import io.holunda.decision.lib.test.CamundaDecisionTestLib
import io.holunda.decision.model.InputDefinition.Companion.booleanInput
import io.holunda.decision.model.InputDefinition.Companion.integerInput
import io.holunda.decision.model.OutputDefinition.Companion.stringOutput
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.model.dmn.HitPolicy
import org.junit.Test


internal class DmnReaderTest {

  val dmn = CamundaDecisionTestLib.readModel("example_single_table.dmn")

  @Test
  fun `read dummy_dmn model with single table`() {
    val foo = integerInput("foo", "Foo Value")
    val bar = booleanInput("bar", "Bar Value")
    val status = stringOutput("status", "VIP Status")

    val decisionTable = DmnReader.readDecisionTable(dmn)

    println(decisionTable)

    assertThat(decisionTable.key).isEqualTo("example")
    assertThat(decisionTable.name).isEqualTo("DMN Example")
    assertThat(decisionTable.versionTag).isEqualTo("666")
    assertThat(decisionTable.hitPolicy).isEqualTo(HitPolicy.UNIQUE)

    assertThat(decisionTable.header.inputs).containsExactly(foo,bar)
    assertThat(decisionTable.header.outputs).containsExactly(status)
  }
}
