package io.holunda.decision.model.converter

import io.holunda.decision.model.CamundaDecisionModel
import io.holunda.decision.model.api.DecisionDefinitionKey
import io.holunda.decision.model.api.definition.InputDefinition
import io.holunda.decision.model.api.definition.OutputDefinition
import io.holunda.decision.model.api.element.DmnRule
import io.holunda.decision.model.api.layout.DmnDiagramLayout
import org.camunda.bpm.model.dmn.Dmn
import org.camunda.bpm.model.dmn.DmnModelInstance
import org.camunda.bpm.model.dmn.instance.*
import kotlin.reflect.KClass


fun createEmptyDmnModelInstance(name: String): DmnModelInstance = Dmn.createEmptyModel().apply {
  definitions = newInstance(Definitions::class).apply {
    this.name = name
    namespace = CamundaDecisionModel.Namespaces.NS_CAMUNDA
  }
}

fun Definitions.createLayout(dmn: DmnModelInstance, layout: DmnDiagramLayout) {
  val dmndi = this.domElement.document.createElement("dmdi", CamundaDecisionModel.Namespaces.NS_DMNDI)
  this.domElement.childElements.add(dmndi)



//  val document: Document = DocumentHelper.createDocument()
//  var documentRoot: Element? = null
//
//  var created: Element? = null
//
//  var parentNode: Node? = null
//  documentRoot = DocumentHelper.createElement()
//  parentNode = documentRoot.selectSingleNode("parentXPath")
//  val ns = Namespace("name", "value")
//  created = (parentNode as Element?).addElement(QName("elementName", ns1))
}

fun DmnModelInstance.text(value: String?) = newInstance(Text::class.java).apply {
  this.textContent = value
}


//<input id="Input_1" label="Alter" biodi:width="192">
//<inputExpression id="InputExpression_1" typeRef="integer">
//<text>age</text>
//</inputExpression>
//</input>


fun <T : DmnModelElementInstance> generateId(elementClass: KClass<T>) =
  "${elementClass.simpleName}_" + Integer.toString(((2.147483647E9 * Math.random()).toInt()));

//fun <T : DmnModelElementInstance> newInstance(elementClass: KClass<T>): T = newInstance(elementClass, generateId(elementClass))

fun <T : DmnModelElementInstance> DmnModelInstance.newInstance(elementClass: KClass<T>, id: String = generateId(elementClass)): T =
  this.newInstance(elementClass.java).apply {
    setAttributeValue("id", id)
  }

fun DecisionTable.createInput(dmn: DmnModelInstance, input: InputDefinition<*>): Input = dmn.newInstance(Input::class).apply {
  this.label = input.label
  this.inputExpression = dmn.inputExpression(input.typeRef, input.key)
}.apply {
  this@createInput.inputs.add(this)
}

fun DecisionTable.createOutput(dmn: DmnModelInstance, output: OutputDefinition<*>): Output = dmn.newInstance(Output::class).apply {
  label = output.label
  name = output.key
  typeRef = output.typeRef
}.apply {
  this@createOutput.outputs.add(this)
}

fun DecisionTable.createRule(dmn: DmnModelInstance, rule: DmnRule) {
  this.rules.add(dmn.newInstance(Rule::class).apply {
    rule.inputs.map(io.holunda.decision.model.api.entry.InputEntry<*>::expression)
      .map(dmn::inputEntry).forEach(this.inputEntries::add)

    rule.outputs.map(io.holunda.decision.model.api.entry.OutputEntry<*>::expression)
      .map(dmn::outputEntry).forEach(this.outputEntries::add)
  })
}

fun Decision.createInformationRequirement(dmn: DmnModelInstance, decisionDefinitionKey: DecisionDefinitionKey) = informationRequirements.add(dmn.newInstance(InformationRequirement::class).apply {
  this.requiredDecision = dmn.getModelElementById<Decision>(decisionDefinitionKey)
})

fun DmnModelInstance.outputEntry(expression: String?) = this.newInstance(OutputEntry::class).apply {
  text = text(expression ?: "")
}
fun DmnModelInstance.inputEntry(expression: String?) = this.newInstance(InputEntry::class).apply {
  text = text(expression ?: "")
}

fun DmnModelInstance.inputExpression(typeRef: String, expression: String?): InputExpression =
  this.newInstance(InputExpression::class).apply {
    this.typeRef = typeRef
    this.text = text(expression)
  }


fun <T : DmnModelElementInstance> DmnModelInstance.get(elementClass: KClass<T>): List<T> =
  this.getModelElementsByType(elementClass.java).toList()


var DmnModelElementInstance.name: String
  get() = this.getAttributeValue("name")
  set(name) = this.setAttributeValue("name", name)

var DmnModelElementInstance.id: String
  get() = this.getAttributeValue("id")
  set(name) = this.setAttributeValue("id", name)
