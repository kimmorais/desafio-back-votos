package org.back_votos_core.use_cases.obter_resultado_assembleia.input;

import java.time.LocalDateTime;
import java.util.UUID;

public record ObterResultadoAssembleiaUseCaseInput(UUID idAssembleia, LocalDateTime momentoRequest) {
}
