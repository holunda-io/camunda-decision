package io.holunda.decision.model.expression

/**
 * Name	Operator	Example	Description
 *
 * * Equal		"Steak"	Test that the input value is equal to the given value.
 * * Less	<	< 10	Test that the input value is less than the given value.
 * * Less or Equal	<=	<= 10	Test that the input value is less than or equal to the given value.
 * * Greater	>	> 10	Test that the input value is greater than the given value.
 * * Greater or Equal	>=	>= 10	Test that the input value is greater than or equal to the given value.
 *
 */
sealed class Comparision {

  object Equal : Comparision()

  object Less : Comparision()

  object LessOrEqual : Comparision()

  object Greater : Comparision()

  object GreaterOrEqual : Comparision()

}

/**
 * Range
 * Some FEEL data types, such as numeric types and date types, can be tested against a range of values. These ranges consist of a start value and an end value. The range specifies if the start and end value is included in the range.
 *
 * Start	End	Example	Description
 * * include	include	[1..10]	Test that the input value is greater than or equal to the start value and less than or equal to the end value.
 * * exclude	include	]1..10] or (1..10]	Test that the input value is greater than the start value and less than or equal to the end value.
 * * include	exclude	[1..10[ or [1..10)	Test that the input value is greater than or equal to the start value and less than the end value.
 * * exclude	exclude	]1..10[ or (1..10)	Test that the input value is greater than the start value and less than the end value.
 *
 */
sealed class Range {

  object Include
  object Exclude
  object IncludeExclude
  object ExcludeInclude

}

/**
 *
 * Disjunction
A FEEL simple unary test can be specified as conjunction of expressions. These expressions have to either have comparisons or ranges. The test is true if at least one of conjunct expressions is true.

Examples:

3,5,7: Test if the input is either 3, 5 or 7
<2,>10: Test if the input is either less than 2 or greater than 10
10,[20..30]: Test if the input is either 10 or between 20 and 30
"Spareribs","Steak","Stew": Test if the input is either the String Spareribs, Steak or Stew
date and time("2015-11-30T12:00:00"),date and time("2015-12-01T12:00:00"): Test if the input is either the date November 30th, 2015 at 12:00:00 o’clock or December 1st, 2015 at 12:00:00 o’clock
>customer.age,>21: Test if the input is either greater than the age property of the variable customer or greater than 21
 */

/**
 * Negation
A FEEL simple unary test can be negated with the not function. This means if the containing expression returns true, the test will return false. Please note that only one negation as first operator is allowed but it can contain a disjunction.

Examples:

not("Steak"): Test if the input is not the String Steak
not(>10): Test if the input is not greater than 10, which means it is less than or equal to 10
not(3,5,7): Test if the input is neither 3, 5 nor 7
not([20..30]): Test if the input is not between 20 and 30
 */
