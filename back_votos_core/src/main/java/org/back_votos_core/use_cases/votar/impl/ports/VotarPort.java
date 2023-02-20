package org.back_votos_core.use_cases.votar.impl.ports;

import org.back_votos_core.entities.Voto;
import org.back_votos_core.use_cases.votar.input.VotarUseCaseInput;

public interface VotarPort {

    Voto votar(VotarUseCaseInput voto);
}
