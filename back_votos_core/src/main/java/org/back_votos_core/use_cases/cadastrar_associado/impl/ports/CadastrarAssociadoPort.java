package org.back_votos_core.use_cases.cadastrar_associado.impl.ports;

import org.back_votos_core.entities.Associado;
import org.back_votos_core.use_cases.cadastrar_associado.input.CadastrarAssociadoUseCaseInput;

public interface CadastrarAssociadoPort {

    Associado cadastrarAssociado(CadastrarAssociadoUseCaseInput associado);
}
