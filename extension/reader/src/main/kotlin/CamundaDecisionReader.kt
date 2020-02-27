package io.holunda.decision.reader

import io.holunda.decision.model.DmnDecisionTable
import org.camunda.bpm.model.dmn.DmnModelInstance

object CamundaDecisionReader {


  fun readDecisionTable(modelInstance:DmnModelInstance) {


    return DmnDecisionTable(
      "key", "name"
    )
  }


}
