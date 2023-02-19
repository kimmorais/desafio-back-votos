package org.back_votos_plugins.dao.tables.mappers;

import org.back_votos_core.entities.Pauta;
import org.back_votos_core.use_cases.cadastrar_pauta.input.CadastrarPautaUseCaseInput;
import org.back_votos_plugins.dao.tables.PautaTable;

public interface PautaTableMapper {

    Pauta converterPautaTableParaPauta(PautaTable pautaTable);

    PautaTable converterPautaInputParaPautaTable(CadastrarPautaUseCaseInput pautaInput);
}
