package org.back_votos_plugins.use_cases.votar.use_case_provider;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.use_cases.votar.VotarUseCase;
import org.back_votos_core.use_cases.votar.factories.VotarUseCaseFactory;
import org.back_votos_plugins.use_cases.votar.use_case_provider.port_adapter.VotarPortAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class VotarUseCaseProvider {

    private final VotarPortAdapter votarPortAdapter;

    @Bean
    public VotarUseCase votarUseCase() {
        return new VotarUseCaseFactory(this.votarPortAdapter).makeInstance();
    }
}
