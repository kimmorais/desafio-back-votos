package org.back_votos_core.use_cases.iniciar_assembleia.input;

import org.back_votos_core.entities.Pauta;

import java.time.LocalDateTime;

public record IniciarAssembleiaUseCaseInput(Pauta pauta, LocalDateTime fimAssembleia) {
}
