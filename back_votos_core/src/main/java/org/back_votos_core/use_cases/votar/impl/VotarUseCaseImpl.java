package org.back_votos_core.use_cases.votar.impl;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Voto;
import org.back_votos_core.use_cases.votar.VotarUseCase;
import org.back_votos_core.use_cases.votar.impl.ports.VotarPort;
import org.back_votos_core.use_cases.votar.input.VotarUseCaseInput;

@RequiredArgsConstructor
public class VotarUseCaseImpl implements VotarUseCase {

    private final VotarPort votarPort;

    @Override
    public Voto execute(VotarUseCaseInput voto) {
        return this.votarPort.votar(voto);
    }
}
