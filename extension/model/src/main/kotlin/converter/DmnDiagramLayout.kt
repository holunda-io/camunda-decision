package io.holunda.decision.model.converter

import io.holunda.decision.model.DecisionDefinitionKey
import io.holunda.decision.model.converter.DmnDiagramLayout.*
import kotlin.math.abs
import kotlin.math.hypot

class DmnDiagramLayout(
  val decisionDefinitionKeys: Set<DecisionDefinitionKey>,
  val requiredDecisions: Map<DecisionDefinitionKey, Set<DecisionDefinitionKey>>
) {
  companion object {
    const val X_OFFSET = 300
  }

  init {
    require(decisionDefinitionKeys.isNotEmpty()) { "there must be at least one table" }
    require(decisionDefinitionKeys.size == 1 || requiredDecisions.isNotEmpty()) { "tables must be connected" }
  }

  fun layout(): Map<DecisionDefinitionKey, Box> {
    val boxes = decisionDefinitionKeys
      .mapIndexed { index, s -> Box(key = s, x = index * X_OFFSET, y = 0) }
      .associateBy { it.key }.toMutableMap()

    for (key in requiredDecisions.keys) {
      for (required in requiredDecisions[key]!!) {
        val edge = shortest(boxes[required]!!, boxes[key]!!)
        boxes.compute(key) { _, box -> box!!.copy(edge = edge) }
      }
    }

    return boxes
  }

  /**
   * A box represents a decision (table) in a decision graph. It has coordinates to draw the element via bpmn.io and contains edge/waypoints
   * data if the decision requires another decision.
   *
   * @param key the key of the decision
   * @param x upper left corner x value
   * @param y upper left corner y value
   * @param width the width in pixels
   * @param height the height in pixels
   * @param edge a connection to another box
   */
  data class Box(
    val key: DecisionDefinitionKey,
    val x: Int,
    val y: Int,
    val width: Int = 180,
    val height: Int = 80,
    val edge: Edge? = null
  ) {
    val points = Points()

    /**
     * Representing all corner and edge points of the box specified by x,y,width and height.
     */
    inner class Points {
      val tl = Point(x, y)
      val tr = Point(x + width, y)
      val bl = Point(x, y + height)
      val br = Point(x + width, y + height)

      val cl = Point(x, y + height / 2)
      val cr = Point(x + width, y + height / 2)
      val ct = Point(x + width / 2, y)
      val cb = Point(x + width / 2, y + height)

      val corners = setOf(tl, tr, bl, br)
      val edges = setOf(cl, cr, ct, cb)

      val all = corners + edges
    }

    fun isCorner(p: Point) = points.corners.contains(p)
    fun isEdge(p: Point) = points.edges.contains(p)
  }


  data class Edge(
    val source: DecisionDefinitionKey,
    val waypointStart: Point,
    val waypointEnd: Point
  ) {
    val waypoints = listOf(waypointStart, waypointEnd)
  }

  data class Point(val x: Int, val y: Int) {
    companion object {
      fun distance(first: Point, second: Point): Double = hypot(abs(first.x - second.x).toDouble(), abs(first.y - second.y).toDouble())
    }
  }
}

internal fun shortest(source: Box, target: Box): Edge {

  data class PointAndDistance(val start: Point, val end: Point) : Comparable<PointAndDistance> {
    val numberOfEdges = listOf(source, target).count { it.isEdge(start) || it.isEdge(end) }
    val distance = Point.distance(start, end)

    /**
     * When comparing, we first search the connections with shortest distance. For thos with the same distance, we prefer the highest
     * number of edge-connections (avoiding arrows from corner to corner).
     */
    override fun compareTo(other: PointAndDistance): Int =
      if (this.distance != other.distance) this.distance.compareTo(other.distance)
      else other.numberOfEdges.compareTo(this.numberOfEdges)
  }

  return lazyCartesianProduct(source.points.all, target.points.all)
    .map { PointAndDistance(it.first, it.second) }
    .sorted()
    .first().let { Edge(source = source.key, waypointStart = it.start, waypointEnd = it.end) }

}


private fun <A, B> lazyCartesianProduct(
  listA: Iterable<A>,
  listB: Iterable<B>
): Sequence<Pair<A, B>> = sequence {
  listA.forEach { a ->
    listB.forEach { b ->
      yield(a to b)
    }
  }
}
