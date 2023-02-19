package org.back_votos_core.use_cases.cadastrar_associado.impl;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Associado;
import org.back_votos_core.use_cases.cadastrar_associado.CadastrarAssociadoUseCase;
import org.back_votos_core.use_cases.cadastrar_associado.impl.ports.CadastrarAssociadoPort;
import org.back_votos_core.use_cases.cadastrar_associado.input.CadastrarAssociadoUseCaseInput;

@RequiredArgsConstructor
public class CadastrarAssociadoUseCaseImpl implements CadastrarAssociadoUseCase {

    private final CadastrarAssociadoPort cadastrarAssociadoPort;

    @Override
    public Associado execute(CadastrarAssociadoUseCaseInput input) {
        return this.cadastrarAssociadoPort.cadastrarAssociado(input);
    }
}
