package org.back_votos_core.use_cases.cadastrar_pauta.impl.ports;

import org.back_votos_core.entities.Pauta;
import org.back_votos_core.use_cases.cadastrar_pauta.input.CadastrarPautaUseCaseInput;

public interface CadastrarPautaPort {

    Pauta cadastrarPauta(CadastrarPautaUseCaseInput nome);
}
