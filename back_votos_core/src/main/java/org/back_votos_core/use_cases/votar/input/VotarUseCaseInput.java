package org.back_votos_core.use_cases.votar.input;

import org.back_votos_core.entities.constants.VotoEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record VotarUseCaseInput(UUID idAssembleia, UUID idAssociado, VotoEnum votoEnum, LocalDateTime horarioVoto) { }