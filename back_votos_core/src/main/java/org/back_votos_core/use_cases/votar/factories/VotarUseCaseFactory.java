package org.back_votos_core.use_cases.votar.factories;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.use_cases.votar.VotarUseCase;
import org.back_votos_core.use_cases.votar.impl.VotarUseCaseImpl;
import org.back_votos_core.use_cases.votar.impl.ports.VotarPort;

@RequiredArgsConstructor
public class VotarUseCaseFactory {

    private final VotarPort votarPort;

    public VotarUseCase makeInstance() { return new VotarUseCaseImpl(this.votarPort); }
}
