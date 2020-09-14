package com.xxAMIDOxx.xxSTACKSxx;

import com.xxAMIDOxx.xxSTACKSxx.menu.repository.AzureMenuRepositoryAdapter;
import com.xxAMIDOxx.xxSTACKSxx.menu.repository.GCPMenuRepositoryAdapter;
import com.xxAMIDOxx.xxSTACKSxx.menu.repository.MenuRepositoryAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureConfig {
  @Bean(name = "menuRepositoryAdapter")
  @ConditionalOnProperty(name = "cloud.provider", havingValue = "azure")
  public MenuRepositoryAdapter azureMenuFacadeBean() {
    return new AzureMenuRepositoryAdapter();
  }

  @Bean(name = "menuRepositoryAdapter")
  @ConditionalOnProperty(name = "cloud.provider", havingValue = "GCP")
  public MenuRepositoryAdapter gCPMenuFacadeBean() {
    return new GCPMenuRepositoryAdapter();
  }
}
