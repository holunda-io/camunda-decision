package io.holunda.decision.model.api.data

import io.holunda.decision.model.api.AggregatorName
import io.holunda.decision.model.api.HitPolicyName
import io.holunda.decision.model.api.data.ResultType.*


/**
 * Tuple combining camundas HitPolicy and the Aggregator (optional, for collection operations).
 *
 * A hit policy specifies how many rules of a decision table can be satisfied and which of the satisfied rules are included
 * in the decision table result. The hit policies Unique, Any and First will always return a maximum of one satisfied rule.
 *
 * The hit policies Rule Order and Collect can return multiple satisfied rules.
 */
enum class DmnHitPolicy(val determineResultType: (Int) -> ResultType) {
  /**
   * Only a single rule can be satisfied. The decision table result contains the output entries of the satisfied rule.
   * If more than one rule is satisfied, the Unique hit policy is violated.
   */
  UNIQUE({ if (it > 1) TupleResult else SingleResult }),

  /**
   * Multiple rules can be satisfied. The decision table result contains only the output of the first satisfied rule.
   */
  FIRST({ if (it > 1) TupleResult else SingleResult }),

  /**
   * TODO: documentation? What is this good for?
   */
  PRIORITY({ TODO("implement") }),

  /**
   * Multiple rules can be satisfied. However, all satisfied rules must generate the same output. The decision table result contains only the output of one of the satisfied rules.
   *
   * If multiple rules are satisfied which generate different outputs, the hit policy is violated.
   */
  ANY({ if (it > 1) TupleResult else SingleResult }),

  /**
   * Multiple rules can be satisfied. The decision table result contains the output of all satisfied rules in
   * the order of the rules in the decision table.
   */
  RULE_ORDER({ if (it > 1) ListResult else TupleListResult }),

  /**
   * TODO: documentation? What is this good for?
   */
  OUTPUT_ORDER({ TODO("implement") }),

  /**
   * Multiple rules can be satisfied.
   * The decision table result contains the output of all satisfied rules in an arbitrary order as a list.
   */
  COLLECT({ if (it > 1) ListResult else TupleListResult }),

  /**
   * The SUM aggregator sums up all outputs from the satisfied rules.
   *
   * TODO: can this be done for multiple outputs?
   */
  COLLECT_SUM({ SingleResult }),

  /**
   * The COUNT aggregator can be use to return the count of satisfied rules.
   */
  COLLECT_COUNT({ SingleResult }),

  /**
   * The MAX aggregator can be used to return the largest output value of all satisfied rules.
   */
  COLLECT_MAX({ SingleResult }), // TODO: correct?

  /**
   * The MIN aggregator can be used to return the smallest output value of all satisfied rules.
   */
  COLLECT_MIN({ SingleResult }), // TODO: correct?
  ;

  companion object {
    fun valueOf(hitPolicy: HitPolicyName, aggregator: AggregatorName?): DmnHitPolicy = valueOf(
      hitPolicy + if (aggregator != null) "_${aggregator}" else ""
    )

  }
}
