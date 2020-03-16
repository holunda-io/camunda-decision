package io.holunda.decision.generator.builder

import io.holunda.decision.model.Id
import io.holunda.decision.model.Name
import io.holunda.decision.model.element.DmnDiagram
import io.holunda.decision.model.ext.generateId
import org.apache.commons.lang3.builder.Builder

/**
 * Builder to create DmnDiagram.
 */
class DmnDiagramBuilder : Builder<DmnDiagram> {

  lateinit var diagramId: Id
  lateinit var diagramName: Name
  var decisionTableBuilders = mutableListOf<DmnDecisionTableBuilder>()

  /**
   * Set the id.
   */
  fun id(id: Id) = apply { this.diagramId = id }

  /**
   * Set the name.
   */
  fun name(name: Name) = apply { this.diagramName = name }

  /**
   * Add builder.
   */
  fun addDecisionTableBuilder(builder: DmnDecisionTableBuilder) = apply { decisionTableBuilders.add(builder) }

  /**
   * Build the dmn diagram.
   */
  override fun build(): DmnDiagram {
    require(decisionTableBuilders.isNotEmpty()) { "Diagram needs to have at least one table." }

    val decisionTables = decisionTableBuilders.map { it.build() }

    return DmnDiagram(
      id = if (::diagramId.isInitialized) diagramId else generateId(DmnDiagram::class),
      name = if(::diagramName.isInitialized) diagramName else "DRD",
      decisionTables = decisionTables
    )
  }
}
