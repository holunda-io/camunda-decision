package io.holunda.decision.model.element

data class DmnRule(
  val id: String? = null,
  val description: String? = null,
  val inputs: List<InputEntry<*>> = listOf(),
  val outputs: List<OutputEntry<*>> = listOf()
) {

  companion object {

    private fun <T : Any> add(list: List<T>, entry: T) = list.toMutableList()
      .apply { this.add(entry) }
      .toList()
  }

  val inputDefinitions by lazy {
    inputs.map { it.definition }
  }

  val outputDefinitions by lazy {
    outputs.map { it.definition }
  }

  fun addInput(entry: InputEntry<*>) = copy(inputs = add(inputs, entry))
  //fun addInput(definition: InputDefinition, expression: Expression) = addInput(InputEntry(definition, expression))
  fun <T:Any> addInput(definition: InputDefinition<T>, expression: String) = addInput(InputEntry(definition, expression))

  fun addOutput(entry: OutputEntry<*>) = copy(outputs = add(outputs, entry))
  //fun addOutput(definition: OutputDefinition, result: Result) = addOutput(OutputEntry(definition, result))
  fun <T:Any> addOutput(definition: OutputDefinition<T>, result: String) = addOutput(OutputEntry(definition, result))


  fun inputEntries(definitions: List<InputDefinition<*>>) = definitions.map { header ->
    inputs.find { it.definition == header }?.expression
  }

  fun outEntries(definitions: List<OutputDefinition<*>>) = definitions.map { header ->
    outputs.find { it.definition == header }?.expression
  }

}
