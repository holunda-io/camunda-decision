package io.holunda.decision.model.api.data

/**
 * The result type of a dmn decision table evaluation.
 * Can be either single value, struct value, list or list of struct.
 */
enum class ResultType {

  /**
   * The result is one single value.
   *
   * Examples:
   * * 1 output column, HitPolicy: FIRST
   * * HitPolicy COLLECT_SUM
   */
  SINGLE,

  /**
   * The result is a list of single values.
   *
   * Examples:
   * * 1 outputColumn, HitPolicy COLLECT
   */
  LIST,

  /**
   * The result represents one single row, but has multiple values.
   *
   * Examples:
   * * 2 or more outputColumns, HitPolicy: FIRST
   */
  TUPLE,

  /**
   * The result is a list of tuples (aka maps)
   *
   * Examples:
   * * 2 or more outputColumns, HitPolicy: COLLECT
   */
  TUPLE_LIST
}
