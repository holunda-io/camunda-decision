package io.holunda.decision.model.api

import io.holunda.decision.model.api.element.DmnDiagram

/**
 * Converter that can read and write valid DMN 1.3 xml diagram files.
 */
interface DmnDiagramConverter {

  /**
   * @param diagram the diagram model
   * @return dmn as xml
   */
  fun toXml(diagram: DmnDiagram): DmnXml

  /**
   * @param dmn as xml
   * @return diagram the diagram model
   */
  fun fromXml(dmn: DmnXml): DmnDiagram
}
