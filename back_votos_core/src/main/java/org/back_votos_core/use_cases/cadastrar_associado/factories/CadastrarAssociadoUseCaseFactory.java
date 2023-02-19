package org.back_votos_core.use_cases.cadastrar_associado.factories;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.use_cases.cadastrar_associado.CadastrarAssociadoUseCase;
import org.back_votos_core.use_cases.cadastrar_associado.impl.CadastrarAssociadoUseCaseImpl;
import org.back_votos_core.use_cases.cadastrar_associado.impl.ports.CadastrarAssociadoPort;

@RequiredArgsConstructor
public class CadastrarAssociadoUseCaseFactory {

    private final CadastrarAssociadoPort cadastrarAssociadoPort;

    public CadastrarAssociadoUseCase makeInstance() { return new CadastrarAssociadoUseCaseImpl(this.cadastrarAssociadoPort); }
}
