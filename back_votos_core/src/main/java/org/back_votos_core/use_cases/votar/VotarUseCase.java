package org.back_votos_core.use_cases.votar;

import org.back_votos_core.contratos.FunctionUseCase;
import org.back_votos_core.entities.Voto;
import org.back_votos_core.use_cases.votar.input.VotarUseCaseInput;

public interface VotarUseCase extends FunctionUseCase<VotarUseCaseInput, Voto> {
}
