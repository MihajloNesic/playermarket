package com.mihajlo.betbull.playermarket.transfer.config;

import com.mihajlo.betbull.playermarket.transfer.repository.RepositoryPackage;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = RepositoryPackage.class)
public class RepositoryConfig {
}