package io.holunda.decision.model.api

import io.holunda.decision.model.api.element.DmnDiagram

interface DmnDiagramResourceProvider {

  fun find(query: DmnDiagramResourceProvider) : DmnDiagram?

}

interface DmnDiagramResourceQuery{

  val id:Id?

}
