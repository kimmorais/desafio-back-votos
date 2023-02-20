package org.back_votos_plugins.dao.tables.mappers;

import org.back_votos_core.entities.Assembleia;
import org.back_votos_plugins.dao.tables.AssembleiaTable;

public interface AssembleiaTableMapper {

    Assembleia converterAssembleiaTableParaAssembleia(AssembleiaTable assembleiaTable);
}
