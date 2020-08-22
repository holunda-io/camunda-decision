package io.holunda.decision.model.jackson.converter

import io.holunda.decision.model.api.CamundaDecisionModelApi.generateId
import io.holunda.decision.model.api.DecisionDefinitionKey
import io.holunda.decision.model.api.data.DataType
import io.holunda.decision.model.api.element.*
import io.holunda.decision.model.jackson.CamundaDecisionJackson
import io.holunda.decision.model.jackson.element.*

internal object JacksonDecisionTableConverter {

  fun convert(decisionTable: DmnDecisionTable): DecisionXml {
    val hitPolicyPair = HitPolicyConverter.convert(decisionTable.hitPolicy)

    val tableXml: DecisionTableXml = DecisionTableXml(
      id = generateId("DecisionTable"),
      hitPolicy = hitPolicyPair.hitPolicy,
      aggregation = hitPolicyPair.aggregator,
      inputs = decisionTable.header.inputs.map {
        HeaderXml.InputXml(
          id = generateId("InputClause"),
          label = it.label,
          inputExpression = HeaderXml.InputXml.InputExpressionXml(
            id = generateId("LiteralExpression"),
            typeRef = CamundaDecisionJackson.TypeRef.valueOf(it.typeRef),
            text = it.key
          )
        )
      },
      outputs = decisionTable.header.outputs.map {
        HeaderXml.OutputXml(
          id = generateId("OuputClause"),
          label = it.label,
          name = it.key,
          typeRef = CamundaDecisionJackson.TypeRef.valueOf(it.typeRef)
        )
      },
      rules = decisionTable.rules.map {
        RuleXml(
          id = generateId("row"),
          description = it.description,
          outputEntries = it.outputs.map {
            RuleXml.EntryXml(
              id = generateId("UnaryTests"),
              text = it.expression
            )
          },
          inputEntries = it.inputs.map {
            RuleXml.EntryXml(
              id = generateId("LiteralExpression"),
              text = it.expression
            )
          }
        )
      }
    )

    return DecisionXml(
      id = decisionTable.decisionDefinitionKey,
      name = decisionTable.name,
      versionTag = decisionTable.versionTag,
      informationRequirements = decisionTable.informationRequirement?.let {
        listOf(convert(it))
      } ?: listOf(), // TODO allow multiple
      decisionTable = tableXml
    )
  }

  fun convert(decisionXml: DecisionXml): DmnDecisionTable {
    val decisionTableXml = decisionXml.decisionTable

    val requiredDecisions: Set<String> = decisionXml.informationRequirements
      .filter { it.requiredDecision != null }
      .map { it.requiredDecision!!.href.removePrefix("#") }
      .toSet()

    val header: DmnDecisionTable.Header = DmnDecisionTable.Header(
      inputs = decisionTableXml.inputs.map {
        DataType.valueByTypeRef(it.inputExpression.typeRef.name).inputDefinition(it.inputExpression.text, it.label)
      },
      outputs = decisionTableXml.outputs.map {
        DataType.valueByTypeRef(it.typeRef.name).outputDefinition(it.name, it.label)
      }
    )

    return DmnDecisionTable(
      decisionDefinitionKey = decisionXml.id,
      name = decisionXml.name,
      versionTag = decisionXml.versionTag,
      hitPolicy = HitPolicyConverter.convert(decisionTableXml.hitPolicy, decisionTableXml.aggregation),
      requiredDecisions = requiredDecisions,
      header = header,
      rules = JacksonRuleConverter.convert(header, decisionTableXml.rules)
    )


//  rules: DmnRuleList
  }

  internal fun convert(informationRequirement: DmnDecisionTable.InformationRequirement): InformationRequirementXml = InformationRequirementXml(
    id = informationRequirement.id,
    requiredDecision = InformationRequirementXml.RequiredDecisionXml(href = "#${informationRequirement.requiredDecisionTable}")
  )

  // TODO: allow multiple table dependencies
  internal fun convert(informationRequirementXmls: List<InformationRequirementXml>): Set<DecisionDefinitionKey> {
    return if (informationRequirementXmls.isNotEmpty() && informationRequirementXmls.firstOrNull()?.requiredDecision != null) {
      setOf(informationRequirementXmls.first().requiredDecision!!.href.removePrefix("#"))
    } else {
      setOf()
    }
  }

}
