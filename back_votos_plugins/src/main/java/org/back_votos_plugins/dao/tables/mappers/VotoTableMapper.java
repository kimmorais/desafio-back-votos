package org.back_votos_plugins.dao.tables.mappers;

import org.back_votos_core.entities.Voto;
import org.back_votos_core.use_cases.votar.input.VotarUseCaseInput;
import org.back_votos_plugins.dao.tables.VotoTable;

public interface VotoTableMapper {

    Voto converterVotoTableParaVoto(VotoTable votoTable);

    VotoTable converterVotoInputParaVotoTable(VotarUseCaseInput votoInput);
}
