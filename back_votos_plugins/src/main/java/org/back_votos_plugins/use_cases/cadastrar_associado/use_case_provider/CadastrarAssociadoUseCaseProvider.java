package org.back_votos_plugins.use_cases.cadastrar_associado.use_case_provider;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.use_cases.cadastrar_associado.CadastrarAssociadoUseCase;
import org.back_votos_core.use_cases.cadastrar_associado.factories.CadastrarAssociadoUseCaseFactory;
import org.back_votos_plugins.use_cases.cadastrar_associado.use_case_provider.port_adapter.CadastrarAssociadoPortAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CadastrarAssociadoUseCaseProvider {

    private final CadastrarAssociadoPortAdapter cadastrarAssociadoPortAdapter;

    @Bean
    public CadastrarAssociadoUseCase cadastrarAssociadoUseCase() {
        return new CadastrarAssociadoUseCaseFactory(cadastrarAssociadoPortAdapter).makeInstance();
    }
}
