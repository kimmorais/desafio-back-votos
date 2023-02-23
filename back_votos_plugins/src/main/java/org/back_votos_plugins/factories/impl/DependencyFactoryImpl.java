package org.back_votos_plugins.factories.impl;

import org.back_votos_plugins.factories.DependencyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class DependencyFactoryImpl implements DependencyFactory {

    @Bean
    @Override
    public Clock criarClock() {

        return Clock.system(ZoneId.of("UTC-03:00"));
    }
}
