package org.back_votos_plugins.use_cases.obter_resultado_assembleia.use_case_provider;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.use_cases.obter_resultado_assembleia.ObterResultadoAssembleiaUseCase;
import org.back_votos_core.use_cases.obter_resultado_assembleia.factories.ObterResultadoAssembleiaUseCaseFactory;
import org.back_votos_plugins.use_cases.obter_resultado_assembleia.use_case_provider.port_adapter.ObterResultadoAssembleiaPortAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ObterResultadoAssembleiaUseCaseProvider {

    private final ObterResultadoAssembleiaPortAdapter obterResultadoAssembleiaPortAdapter;

    @Bean
    public ObterResultadoAssembleiaUseCase obterResultadoAssembleiaUseCase() {
        return new ObterResultadoAssembleiaUseCaseFactory(this.obterResultadoAssembleiaPortAdapter).makeInstance();
    }
}
