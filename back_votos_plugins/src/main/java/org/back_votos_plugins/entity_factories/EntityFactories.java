package org.back_votos_plugins.entity_factories;

import org.back_votos_core.entities.factories.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityFactories {

    @Bean
    public AssembleiaFactory assembleiaFactory() { return new AssembleiaFactory(); }

    @Bean
    public AssociadoFactory associadoFactory() { return new AssociadoFactory(); }

    @Bean
    public PautaFactory pautaFactory() { return new PautaFactory(); }

    @Bean
    public VotoFactory votoFactory() { return new VotoFactory(); }
}
