package io.holunda.decision.runtime.spring

import org.springframework.context.annotation.Import
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS

@Target(CLASS)
@MustBeDocumented
@Retention(RUNTIME)
@Import(CamundaDecisionConfiguration::class)
annotation class EnableCamundaDecision
