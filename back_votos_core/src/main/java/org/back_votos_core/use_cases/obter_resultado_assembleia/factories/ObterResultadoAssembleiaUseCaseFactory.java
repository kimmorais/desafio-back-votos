package org.back_votos_core.use_cases.obter_resultado_assembleia.factories;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.use_cases.obter_resultado_assembleia.ObterResultadoAssembleiaUseCase;
import org.back_votos_core.use_cases.obter_resultado_assembleia.impl.ObterResultadoAssembleiaUseCaseImpl;
import org.back_votos_core.use_cases.obter_resultado_assembleia.impl.ports.ObterResultadoAssembleiaPort;

@RequiredArgsConstructor
public class ObterResultadoAssembleiaUseCaseFactory {

    private final ObterResultadoAssembleiaPort obterResultadoAssembleiaPort;

    public ObterResultadoAssembleiaUseCase makeInstance() {
        return new ObterResultadoAssembleiaUseCaseImpl(this.obterResultadoAssembleiaPort);
    }
}
