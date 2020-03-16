package io.holunda.decision.generator

import io.holunda.decision.generator.builder.DmnDecisionTableBuilder
import io.holunda.decision.lib.test.CamundaDecisionTestLib
import io.holunda.decision.model.converter.DmnDiagramConverter
import io.holunda.decision.model.ext.toXml
import io.holunda.decision.model.io.DmnWriter
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


internal class CamundaDecisionGeneratorTest {

  private val exampleSingleDmn = CamundaDecisionTestLib.readModel("example_single_table.dmn")

  @Test
  fun `build diagram from existing dmn`() {
    val diagram = CamundaDecisionGenerator.diagram()
      .addDecisionTableBuilder(DmnDecisionTableBuilder()
        .reference(exampleSingleDmn)
        .decisionDefinitionKey("fooTable") // change name from "example" to "fooTable"
        .name("Changed Name") // change name from "DMN Example" to "Changed Name"
        .versionTag("667") // increase version
      )
      .build()



//    println(DmnWriter.render(diagram))
//    println(DmnDiagramConverter.toModelInstance(diagram).toXml())

    assertThat(diagram.name).isEqualTo("DRD")
    val table = diagram.decisionTables.first()

    assertThat(table.decisionDefinitionKey).isEqualTo("fooTable")
    assertThat(table.name).isEqualTo("Changed Name")
    assertThat(table.versionTag).isEqualTo("667")

  }
}
