package org.back_votos_plugins.dao.tables.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Voto;
import org.back_votos_core.use_cases.votar.input.VotarUseCaseInput;
import org.back_votos_plugins.dao.tables.VotoTable;
import org.back_votos_plugins.dao.tables.mappers.VotoTableMapper;
import org.back_votos_plugins.factories.EntityFactories;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VotoTableMapperImpl implements VotoTableMapper {

    private final EntityFactories entityFactories;

    @Override
    public Voto converterVotoTableParaVoto(VotoTable votoTable) {
        var voto = entityFactories.votoFactory().makeNewInstance();
        voto.setIdVoto(votoTable.getId());
        voto.setIdAssociado(votoTable.getIdAssociado());
        voto.setVotoEnum(votoTable.getVotoEnum());
        return voto;
    }

    @Override
    public VotoTable converterVotoInputParaVotoTable(VotarUseCaseInput votoInput) {
        var votoTable = new VotoTable();
        votoTable.setIdAssociado(votoInput.idAssociado());
        votoTable.setIdAssociado(votoInput.idAssociado());
        votoTable.setVotoEnum(votoInput.votoEnum());
        return votoTable;
    }
}
