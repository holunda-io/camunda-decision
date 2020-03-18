package io.holunda.decision.example;

import io.holunda.decision.runtime.deployment.DmnDeploymentService;
import io.holunda.decision.runtime.query.DmnQueryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableProcessApplication
public class DmnExampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(DmnExampleApplication.class, args);
  }

  @Bean
  public DmnDeploymentService dmnDeploymentService(RepositoryService repositoryService) {
    return new DmnDeploymentService(repositoryService);
  }

  @Bean
  public DmnQueryService dmnQueryService(RepositoryService repositoryService) {
    return new DmnQueryService(repositoryService);
  }
}
