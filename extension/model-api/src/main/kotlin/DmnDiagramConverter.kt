package io.holunda.decision.model.api

import io.holunda.decision.model.api.element.DmnDiagram

interface DmnDiagramConverter {

  fun toXml(diagram: DmnDiagram): DmnXml

  fun fromXml(dmn: DmnXml): DmnDiagram
}
