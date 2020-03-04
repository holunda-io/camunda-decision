package io.holunda.decision.model.converter

import io.holunda.decision.model.DecisionDefinitionKey
import io.holunda.decision.model.Point

class DmnDiagramLayout(
  val decisionDefinitionKeys: Set<DecisionDefinitionKey>,
  val requiredDecisions: Map<DecisionDefinitionKey, Set<DecisionDefinitionKey>>
) {

  data class Box(
    val key: DecisionDefinitionKey,
    val x: Int,
    val y: Int,
    val width: Int = 180,
    val height: Int = 80
  ) {
    val tl = Point(x,y)
    val tr = Point(x+width,y)
    val bl = Point(x,y+height)
    val br = Point(x+width,y+height)

    val cl = Point(x,y + height/2)
    val cr = Point(x+width,y+ height/2)
    val ct = Point(x + width/2,y)
    val cb = Point(x + width/2,y+height)
  }


}
