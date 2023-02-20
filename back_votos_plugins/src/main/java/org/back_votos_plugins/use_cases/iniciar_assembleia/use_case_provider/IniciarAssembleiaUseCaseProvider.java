package org.back_votos_plugins.use_cases.iniciar_assembleia.use_case_provider;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.use_cases.iniciar_assembleia.IniciarAssembleiaUseCase;
import org.back_votos_core.use_cases.iniciar_assembleia.factories.IniciarAssembleiaUseCaseFactory;
import org.back_votos_plugins.use_cases.iniciar_assembleia.use_case_provider.port_adapter.IniciarAssembleiaPortAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class IniciarAssembleiaUseCaseProvider {

    private final IniciarAssembleiaPortAdapter iniciarAssembleiaPortAdapter;

    @Bean
    public IniciarAssembleiaUseCase iniciarAssembleiaUseCase() {
        return new IniciarAssembleiaUseCaseFactory(this.iniciarAssembleiaPortAdapter).makeInstance();
    }
}
