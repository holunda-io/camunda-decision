package io.holunda.decision.model

import io.holunda.decision.lib.test.CamundaDecisionTestLib
import io.holunda.decision.model.ext.findDecisionByKey
import org.camunda.bpm.model.dmn.instance.ExtensionElements
import org.junit.Test

class Spike {


  @Test
  fun name() {
    val dmn = CamundaDecisionTestLib.readModel("drd-dec1-dec2.dmn")


    val decision = dmn.definitions.findDecisionByKey("decision2")!!


    val extensionElements = decision.getChildElementsByType(ExtensionElements::class.java).first()

    print(extensionElements)
  }
}
