package io.holunda.decision.model.jackson.converter

import io.holunda.decision.model.api.element.DmnDecisionTable
import io.holunda.decision.model.api.element.DmnRule
import io.holunda.decision.model.api.element.DmnRuleList
import io.holunda.decision.model.api.element.toEntry
import io.holunda.decision.model.jackson.element.RuleXml

object JacksonRuleConverter {

  fun convert(header: DmnDecisionTable.Header, xml: RuleXml): DmnRule {
    require(header.inputs.size == xml.inputEntries.size)
    require(header.outputs.size == xml.outputEntries.size)

    return DmnRule(
      id = xml.id,
      description = xml.description,
      inputs = header.inputs.mapIndexed { i, def -> def.toEntry(xml.inputEntries[i].text) },
      outputs = header.outputs.mapIndexed { i, def -> def.toEntry(xml.outputEntries[i].text) }
    )
  }

  fun convert(rule: DmnRule): RuleXml = RuleXml(
    id = rule.id,
    description = rule.description,
    inputEntries = rule.inputs.map { RuleXml.EntryXml(id = DmnRule.idGenerator(), text =  it.expression) },
    outputEntries = rule.outputs.map { RuleXml.EntryXml(id = DmnRule.idGenerator(), text =  it.expression) }
  )

  fun convert(header: DmnDecisionTable.Header, rules: List<RuleXml>) : DmnRuleList = DmnRuleList(rules.map { convert(header, it) })

  fun convert(rules: DmnRuleList) : List<RuleXml> = rules.map { convert(it) }

}
