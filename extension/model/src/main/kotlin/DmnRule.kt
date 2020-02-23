package io.holunda.decision.model

data class DmnRule(
  val inputs: List<InputEntry>,
  val outputs: List<OutputEntry>
) {
  companion object {
    operator fun invoke() = DmnRule(listOf(), listOf())

    private fun <T : Any> add(list: List<T>, entry: T) = list.toMutableList()
      .apply { this.add(entry) }
      .toList()

    fun List<DmnRule>.distinctInputs() = this.flatMap { it.inputs }
      .map { it.definition }.distinct().sortedBy { it.key }

    fun List<DmnRule>.distinctOutputs() = this.flatMap { it.outputs }
      .map { it.definition }.distinct().sortedBy { it.key }

  }

  val inputDefinitions by lazy {
    inputs.map { it.definition }
  }

  val outputDefinitions by lazy {
    outputs.map { it.definition }
  }

  fun addInput(entry: InputEntry) = copy(inputs = add(inputs, entry))
  fun addInput(definition: InputDefinition, expression: Expression) = addInput(InputEntry(definition, expression))
  fun addInput(definition: InputDefinition, expression: String) = addInput(definition, Expression.FeelExpression(expression))

  fun addOutput(entry: OutputEntry) = copy(outputs = add(outputs, entry))
  fun addOutput(definition: OutputDefinition, result: Result) = addOutput(OutputEntry(definition, result))
  fun addOutput(definition: OutputDefinition, result: String) = addOutput(definition, Result.FeelResult(result))


  fun inputEntries(definitions: List<InputDefinition>) = definitions.map { header ->
    inputs.find { it.definition == header }?.expression?.getExpression()
  }

  fun outEntries(definitions: List<OutputDefinition>) = definitions.map { header ->
    outputs.find { it.definition == header }?.result?.getResult()
  }

}
