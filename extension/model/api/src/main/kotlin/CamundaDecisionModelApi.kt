package io.holunda.decision.model.api

import io.holunda.camunda.bpm.data.builder.VariableMapBuilder
import io.holunda.decision.model.api.definition.*
import kotlin.reflect.KClass

typealias HitPolicyName = String
typealias AggregatorName = String

typealias Id = String

/**
 * Specialized [Id] that is used for diagrams, as this will be the key id when evaluating DRGs.
 */
typealias DiagramId = Id
typealias Name = String
typealias DecisionDefinitionKey = String
typealias DecisionDefinitionId = String
typealias DecisionRequirementsDefinitionId = String
typealias VersionTag = String
typealias DmnXml = String
typealias FeelString = String

/**
 * Static convenience methods to access all relevant model-api methods in one single import.
 */
object CamundaDecisionModelApi {

  /**
   * Creates a random id in the form `DecisionTable_123456`.
   *
   * There is no guarantee that the id is unique, but it is very unlikely, so good enough.
   */
  fun generateId(prefix: String) : Id = "${prefix}_${(2.147483647E9 * Math.random()).toInt()}"

  /**
   * Creates a random id in the form `DecisionTable_123456`.
   *
   * There is no guarantee that the id is unique, but it is very unlikely, so good enough.
   *
   * @see generateId
   * @param elementClass the klass to se (simpleName)
   */
  fun <T : Any> generateId(elementClass: KClass<T>) : Id = generateId("${elementClass.simpleName}")


  object InputDefinitions {

    @JvmStatic
    @JvmOverloads
    fun stringInput(key: String, label: String = key) = StringInputDefinition(key, label)

    @JvmStatic
    @JvmOverloads
    fun booleanInput(key: String, label: String = key) = BooleanInputDefinition(key, label)

    @JvmStatic
    @JvmOverloads
    fun integerInput(key: String, label: String = key) = IntegerInputDefinition(key, label)

    @JvmStatic
    @JvmOverloads
    fun longInput(key: String, label: String = key) = LongInputDefinition(key, label)

    @JvmStatic
    @JvmOverloads
    fun doubleInput(key: String, label: String = key) = DoubleInputDefinition(key, label)

    @JvmStatic
    @JvmOverloads
    fun dateInput(key: String, label: String = key) = DateInputDefinition(key, label)

    fun <T:Any> VariableMapBuilder.set(input: InputDefinition<T>, value: T) = set(input.variableFactory, value)
  }

  object OutputDefinitions {
    @JvmStatic
    @JvmOverloads
    fun stringOutput(key: String, label: String = key) = StringOutputDefinition(key, label)

    @JvmStatic
    @JvmOverloads
    fun booleanOutput(key: String, label: String = key) = BooleanOutputDefinition(key, label)

    @JvmStatic
    @JvmOverloads
    fun integerOutput(key: String, label: String = key) = IntegerOutputDefinition(key, label)

    @JvmStatic
    @JvmOverloads
    fun longOutput(key: String, label: String = key) = LongOutputDefinition(key, label)

    @JvmStatic
    @JvmOverloads
    fun doubleOutput(key: String, label: String = key) = DoubleOutputDefinition(key, label)

    @JvmStatic
    @JvmOverloads
    fun dateOutput(key: String, label: String = key) = DateOutputDefinition(key, label)

  }
}
