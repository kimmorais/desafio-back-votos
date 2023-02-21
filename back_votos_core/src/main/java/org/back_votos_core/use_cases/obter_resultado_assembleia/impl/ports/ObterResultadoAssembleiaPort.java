package org.back_votos_core.use_cases.obter_resultado_assembleia.impl.ports;

import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.use_cases.obter_resultado_assembleia.input.ObterResultadoAssembleiaUseCaseInput;

public interface ObterResultadoAssembleiaPort {

    Assembleia obterResultado(ObterResultadoAssembleiaUseCaseInput input);
}
