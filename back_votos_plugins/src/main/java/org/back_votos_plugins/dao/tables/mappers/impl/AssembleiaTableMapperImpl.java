package org.back_votos_plugins.dao.tables.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Assembleia;
import org.back_votos_plugins.dao.tables.AssembleiaTable;
import org.back_votos_plugins.dao.tables.mappers.AssembleiaTableMapper;
import org.back_votos_plugins.dao.tables.mappers.PautaTableMapper;
import org.back_votos_plugins.dao.tables.mappers.VotoTableMapper;
import org.back_votos_plugins.factories.EntityFactories;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssembleiaTableMapperImpl implements AssembleiaTableMapper {

    private final EntityFactories entityFactories;
    private final PautaTableMapper pautaTableMapper;
    private final VotoTableMapper votoTableMapper;

    @Override
    public Assembleia converterAssembleiaTableParaAssembleia(AssembleiaTable assembleiaTable) {
        var assembleia = this.entityFactories.assembleiaFactory().makeNewInstance();
        assembleia.setIdAssembleia(assembleiaTable.getId());
        assembleia.setPauta(this.pautaTableMapper.converterPautaTableParaPauta(assembleiaTable.getPauta()));
        assembleia.setVotos(assembleiaTable.getVotos().stream().map(this.votoTableMapper::converterVotoTableParaVoto).toList());
        assembleia.setQtdVotosSim(assembleiaTable.getQtdVotosSim());
        assembleia.setQtdVotosNao(assembleiaTable.getQtdVotosNao());
        assembleia.setVencedora(assembleiaTable.getVencedora());
        return assembleia;
    }
}
