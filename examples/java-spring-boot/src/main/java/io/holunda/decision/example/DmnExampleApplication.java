package io.holunda.decision.example;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class DmnExampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(DmnExampleApplication.class, args);
  }

//  @Bean
//  public DmnDeploymentService dmnDeploymentService(RepositoryService repositoryService) {
//    return new DmnDeploymentService(repositoryService);
//  }
//
//  @Bean
//  public DmnQueryService dmnQueryService(RepositoryService repositoryService) {
//    return new DmnQueryService(repositoryService);
//  }
}
