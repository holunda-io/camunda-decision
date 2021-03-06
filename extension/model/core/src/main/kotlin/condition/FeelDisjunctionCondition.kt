package io.holunda.decision.model.condition

/**
 * Disjunction (equal/oneOf). Lists allowed values.
 *
 * Example for Long: `1,2,3` is true if the input value is 1,2 or 3.
 */
data class FeelDisjunctionCondition<T : Comparable<T>>(
  val values: Set<T>
) : FeelCondition<T> {

  constructor(vararg values: T) : this(values = values.toSet())

  override val expression = if (values.isNotEmpty()) {
    values.sorted().joinToString(separator = ",") { FeelCondition.toFeelString(it) }
  } else null
}

