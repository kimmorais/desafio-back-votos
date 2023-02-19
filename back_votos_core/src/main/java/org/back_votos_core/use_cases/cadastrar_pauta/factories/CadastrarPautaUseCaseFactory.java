package org.back_votos_core.use_cases.cadastrar_pauta.factories;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.use_cases.cadastrar_pauta.CadastrarPautaUseCase;
import org.back_votos_core.use_cases.cadastrar_pauta.impl.CadastrarPautaUseCaseImpl;
import org.back_votos_core.use_cases.cadastrar_pauta.impl.ports.CadastrarPautaPort;

@RequiredArgsConstructor
public class CadastrarPautaUseCaseFactory {

    private final CadastrarPautaPort cadastrarPautaPort;

    public CadastrarPautaUseCase makeInstance() {
        return new CadastrarPautaUseCaseImpl(cadastrarPautaPort);
    }
}
