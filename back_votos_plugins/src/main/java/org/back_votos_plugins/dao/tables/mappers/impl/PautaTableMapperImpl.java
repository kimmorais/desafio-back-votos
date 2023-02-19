package org.back_votos_plugins.dao.tables.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Pauta;
import org.back_votos_core.use_cases.cadastrar_pauta.input.CadastrarPautaUseCaseInput;
import org.back_votos_plugins.dao.tables.PautaTable;
import org.back_votos_plugins.dao.tables.mappers.PautaTableMapper;
import org.back_votos_plugins.entity_factories.EntityFactories;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PautaTableMapperImpl implements PautaTableMapper {

    private final EntityFactories entityFactories;

    @Override
    public Pauta converterPautaTableParaPauta(PautaTable pautaTable) {
        var pautaImpl = entityFactories.pautaFactory().makeNewInstance();
        pautaImpl.setIdPauta(pautaTable.getId());
        pautaImpl.setNome(pautaTable.getNome());
        return pautaImpl;
    }

    @Override
    public PautaTable converterPautaInputParaPautaTable(CadastrarPautaUseCaseInput pautaInput) {
        var pautaTable = new PautaTable();
        pautaTable.setNome(pautaInput.nomePauta());
        return pautaTable;
    }
}
