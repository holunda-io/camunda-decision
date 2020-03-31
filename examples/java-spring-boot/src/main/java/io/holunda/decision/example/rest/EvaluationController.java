package io.holunda.decision.example.rest;

import io.holunda.decision.generator.CamundaDecisionGenerator;
import io.holunda.decision.generator.builder.DmnDecisionTableReferenceBuilder;
import io.holunda.decision.model.element.DmnDiagram;
import io.holunda.decision.runtime.deployment.DmnDeploymentService;
import io.holunda.decision.runtime.query.DmnQueryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.DecisionDefinition;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dmn")
@RequiredArgsConstructor
public class EvaluationController {

  private final DmnDeploymentService dmnDeploymentService;
  private final DmnQueryService dmnQueryService;
  private final RepositoryService repositoryService;
  private final DecisionService decisionService;

  @GetMapping("/tables")
  public ResponseEntity<List<String>> listDeployedDmn() {
    return ResponseEntity.ok(repositoryService.createDecisionDefinitionQuery().latestVersion().list()
      .stream().map(DecisionDefinition::getKey)
      .collect(Collectors.toList())
    );
  }

  @PostMapping
  public ResponseEntity<Deployment> deploy() {
    DmnModelInstance legalRestrictions = dmnQueryService.findModelInstance("legal_restrictions");

    final DmnDiagram diagram = CamundaDecisionGenerator.diagram()
      .name("A copy of legal restrictions")
      .addDecisionTableBuilder(new DmnDecisionTableReferenceBuilder().reference(legalRestrictions))
      .build();


    return ResponseEntity.ok(dmnDeploymentService.deploy(diagram));
  }
}
