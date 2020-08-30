package io.holunda.decision.model.api.data

sealed class ResultType {

  /**
   * The result is one single value.
   *
   * Examples:
   * * 1 output column, HitPolicy: FIRST
   * * HitPolicy COLLECT_SUM
   */
  object SingleResult : ResultType()

  /**
   * The result is a list of single values.
   *
   * Examples:
   * * 1 outputColumn, HitPolicy COLLECT
   */
  object ListResult : ResultType()

  /**
   * The result represents one single row, but has multiple values.
   *
   * Examples:
   * * 2 or more outputColumns, HitPolicy: FIRST
   */
  object TupleResult : ResultType()

  /**
   * The result is a list of tuples (aka maps)
   *
   * Examples:
   * * 2 or more outputColumns, HitPolicy: COLLECT
   */
  object TupleListResult: ResultType()
}
