package io.holunda.decision.generator

import io.holunda.decision.generator.builder.DmnDiagramBuilder


object CamundaDecisionGenerator {

  fun diagram() = DmnDiagramBuilder()

}


//
//fun main() {
//
//  val f = FormulaFactory()
//
//  val a = f.variable("A")
//  val b = f.variable("B")
//  val c = f.variable("C")
//
//  val g = f.and(a, f.or(b,f.and(b,c)))
//  println(g)
//  println(g.cnf())
//  println(g.nnf())
//
//
//}
