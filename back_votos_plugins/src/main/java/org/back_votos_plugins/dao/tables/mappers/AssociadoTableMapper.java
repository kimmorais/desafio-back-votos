package org.back_votos_plugins.dao.tables.mappers;

import org.back_votos_core.entities.Associado;
import org.back_votos_core.use_cases.cadastrar_associado.input.CadastrarAssociadoUseCaseInput;
import org.back_votos_plugins.dao.tables.AssociadoTable;

public interface AssociadoTableMapper {

    Associado converterAssociadoTableParaAssociado(AssociadoTable associadoTable);

    AssociadoTable converterAssociadoInputParaAssociadoTable(CadastrarAssociadoUseCaseInput associadoInput);
}
