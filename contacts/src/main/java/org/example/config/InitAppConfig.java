package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:application-init.properties")
@Profile("init")
public class InitAppConfig {
}
