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

  private var diagramId: Id? = null
  private var diagramName: Name? = null
  private var decisionTableBuilders = mutableListOf<AbstractDmnDecisionTableBuilder>()

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
  fun addDecisionTable(builder: AbstractDmnDecisionTableBuilder) = apply { decisionTableBuilders.add(builder) }

  /**
   * Build the dmn diagram.
   */
  override fun build(): DmnDiagram {
    require(decisionTableBuilders.isNotEmpty()) { "Diagram needs to have at least one table." }

    return DmnDiagram(
      id = diagramId ?: generateId(DmnDiagram::class),
      name = diagramName ?: "DRD",
      decisionTables = decisionTableBuilders.map { it.build() }
    )
  }

  override fun toString() = "DmnDiagramBuilder(diagramId=$diagramId, diagramName=$diagramName, decisionTableBuilders=$decisionTableBuilders)"

}
