package io.holunda.decision.example.rest;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.repository.Deployment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dmn")
@RequiredArgsConstructor
public class EvaluationController {

//  private final DmnDeploymentService dmnDeploymentService;
//  private final DmnQueryService dmnQueryService;
//  private final RepositoryService repositoryService;
//  private final DecisionService decisionService;

  @GetMapping("/tables")
  public ResponseEntity<List<String>> listDeployedDmn() {
    // FIXME
    throw new UnsupportedOperationException();
//    return ResponseEntity.ok(repositoryService.createDecisionDefinitionQuery().latestVersion().list()
//      .stream().map(DecisionDefinition::getKey)
//      .collect(Collectors.toList())
//    );
  }

  @PostMapping
  public ResponseEntity<Deployment> deploy() {
    // FIXME
    throw new UnsupportedOperationException();
//    DmnModelInstance legalRestrictions = dmnQueryService.findModelInstance("legal_restrictions");
//
//    final DmnDiagram diagram = CamundaDecisionGenerator.diagram()
//      .name("A copy of legal restrictions")
//      .addDecisionTable(new DmnDecisionTableReferenceBuilder().reference(legalRestrictions))
//      .build();
//
//
//    return ResponseEntity.ok(dmnDeploymentService.deploy(diagram));
//
  }
}
