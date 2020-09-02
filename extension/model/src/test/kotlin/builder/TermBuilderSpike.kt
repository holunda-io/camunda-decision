package io.holunda.decision.model.builder

import com.bpodgursky.jbool_expressions.*
import io.holunda.decision.model.FeelExpressions.exprBetween_old
import io.holunda.decision.model.FeelExpressions.exprGreaterThan_old
import io.holunda.decision.model.FeelExpressions.exprTrue_old
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.booleanInput
import io.holunda.decision.model.api.CamundaDecisionModelApi.InputDefinitions.integerInput
import org.junit.Test

class TermBuilderSpike  {

  @Test
  fun name() {
    val inFoo = integerInput("foo")
    val inBar = integerInput("bar")
    val inBaz = booleanInput("baz")

    val fooExpr = inFoo.exprBetween_old(10,20)
    val fooExpr2 = inFoo.exprBetween_old(30,40)
    val barExpr = inBar.exprGreaterThan_old(19)
    val bazExpr = inBaz.exprTrue_old()

    val f1 = fooExpr

//    println(f1)
//    println(f1.term())
//
//    val expression = And.of(
//      fooExpr.term(),
//      barExpr.term(),
//      fooExpr2.term(),
//      Or.of(
//        bazExpr.term(),
//        Not.of(bazExpr.term())
//      )
//    )
//
//    println(expression)
//
//    println("=====")
//
//    println(RuleSet.toDNF(expression))


  }

  @Test
  fun name1() {
    val a = Not.of(Variable.of("A"))
    println(a)
  }
}

