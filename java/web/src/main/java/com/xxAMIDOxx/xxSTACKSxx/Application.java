package com.xxAMIDOxx.xxSTACKSxx;

import com.microsoft.azure.spring.data.cosmosdb.repository.config.EnableCosmosRepositories;
import com.xxAMIDOxx.menu.repository.AzureMenuFacade;
import com.xxAMIDOxx.menu.repository.MenuFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@PropertySource("classpath:auth.properties")
@ComponentScan(basePackages = {"com.xxAMIDOxx.*"})
@EntityScan(basePackages = {"com.xxAMIDOxx.*"})
@EnableCosmosRepositories(basePackages = {"com.xxAMIDOxx.*"})
//@EnableJpaRepositories - to be used
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean(name = "menuFacade")
//  @ConditionalOnProperty(name = "cloud.provider", havingValue = "azure")
  public MenuFacade azureMenuFacadeBean() {
    return new AzureMenuFacade();
  }

//  @Bean(name = "menuFacade")
//  @ConditionalOnProperty(name = "cloud.provider", havingValue = "gcp")
//  public MenuFacade gcpMenuFacadeBean() {
//    return new GCPMenuFacade();
//  }
}
