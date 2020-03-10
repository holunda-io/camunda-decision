package io.holunda.decision.model.converter

import io.holunda.decision.model.DecisionDefinitionKey
import io.holunda.decision.model.converter.DmnDiagramLayout.*
import kotlin.math.abs
import kotlin.math.hypot

class DmnDiagramLayout(
  val decisionDefinitionKeys: Set<DecisionDefinitionKey>,
  val requiredDecisions: Map<DecisionDefinitionKey, Set<DecisionDefinitionKey>>
)  {



  init {
    require(decisionDefinitionKeys.isNotEmpty()) { "there must be at least one table"}
    require(decisionDefinitionKeys.size == 1 || requiredDecisions.isNotEmpty()) { "tables must be connected" }

  }

  fun layout(): Map<DecisionDefinitionKey, Box> {
    val boxes = decisionDefinitionKeys.mapIndexed { index, s -> Box(key = s, x= index*300,y= 0 ) }.associateBy { it.key }.toMutableMap()

    for (key in requiredDecisions.keys) {
      for (required in requiredDecisions[key]!!) {
        val edge = shortest(boxes[required]!!, boxes[key]!!)
        boxes.compute(key) { _, box  -> box!!.copy(edge= edge) }
      }
    }

    return boxes
  }


  data class Box(
    val key: DecisionDefinitionKey,
    val x: Int,
    val y: Int,
    val width: Int = 180,
    val height: Int = 80,
    val edge: Edge? = null
  ) {
    val tl = Point(x, y)
    val tr = Point(x + width, y)
    val bl = Point(x, y + height)
    val br = Point(x + width, y + height)

    val cl = Point(x, y + height / 2)
    val cr = Point(x + width, y + height / 2)
    val ct = Point(x + width / 2, y)
    val cb = Point(x + width / 2, y + height)

    val corners = setOf(tl,tr,bl,br)
    val edges = setOf(cl,cr,ct,cb)

    fun isCorner(p:Point) = corners.contains(p)
    fun isEdge(p:Point) = edges.contains(p)
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
  data class PointAndDistance(val start:Point, val end: Point) : Comparable<PointAndDistance> {
    val numberOfEdges = listOf(source,target).count { it.isEdge(start) || it.isEdge(end) }
    val distance = Point.distance(start,end)

    override fun compareTo(other: PointAndDistance): Int {
      return if (this.distance != other.distance) this.distance.compareTo(other.distance)
        else other.numberOfEdges.compareTo(this.numberOfEdges)
    }
  }

  return lazyCartesianProduct(source.corners + source.edges, target.corners + target.edges)
    .map { PointAndDistance(it.first, it.second) }
    .sorted()
    .first().let { Edge(source = source.key, waypointStart = it.start, waypointEnd =  it.end) }

}


private fun <A, B> lazyCartesianProduct(
  listA: Iterable<A>,
  listB: Iterable<B>
): Sequence<Pair<A, B>> =
  sequence {
    listA.forEach { a ->
      listB.forEach { b ->
        yield(a to b)
      }
    }
  }
