package org.back_votos_plugins.use_cases.cadastrar_pauta.use_case_provider;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.use_cases.cadastrar_pauta.CadastrarPautaUseCase;
import org.back_votos_core.use_cases.cadastrar_pauta.factories.CadastrarPautaUseCaseFactory;
import org.back_votos_plugins.use_cases.cadastrar_pauta.use_case_provider.port_adapter.CadastrarPautaPortAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CadastrarPautaUseCaseProvider {

    private final CadastrarPautaPortAdapter cadastrarPautaPortAdapter;

    @Bean
    public CadastrarPautaUseCase cadastrarPautaUseCase() {
        return new CadastrarPautaUseCaseFactory(cadastrarPautaPortAdapter).makeInstance();
    }
}
