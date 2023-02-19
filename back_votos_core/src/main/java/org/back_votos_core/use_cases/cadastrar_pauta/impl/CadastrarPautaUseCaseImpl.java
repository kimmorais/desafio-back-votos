package org.back_votos_core.use_cases.cadastrar_pauta.impl;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Pauta;
import org.back_votos_core.use_cases.cadastrar_pauta.CadastrarPautaUseCase;
import org.back_votos_core.use_cases.cadastrar_pauta.impl.ports.CadastrarPautaPort;
import org.back_votos_core.use_cases.cadastrar_pauta.input.CadastrarPautaUseCaseInput;

@RequiredArgsConstructor
public class CadastrarPautaUseCaseImpl implements CadastrarPautaUseCase {

    private final CadastrarPautaPort cadastrarPautaPort;

    @Override
    public Pauta execute(CadastrarPautaUseCaseInput pauta) {
        return this.cadastrarPautaPort.cadastrarPauta(pauta);
    }
}
