package org.back_votos_plugins.dao.tables.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Associado;
import org.back_votos_core.use_cases.cadastrar_associado.input.CadastrarAssociadoUseCaseInput;
import org.back_votos_plugins.dao.tables.AssociadoTable;
import org.back_votos_plugins.dao.tables.mappers.AssociadoTableMapper;
import org.back_votos_plugins.entity_factories.EntityFactories;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssociadoTableMapperImpl implements AssociadoTableMapper {

    private final EntityFactories entityFactories;

    @Override
    public Associado converterAssociadoTableParaAssociado(AssociadoTable associadoTable) {
        var associado = entityFactories.associadoFactory().makeNewInstance();
        associado.setIdAssociado(associadoTable.getId());
        associado.setCpf(associadoTable.getCpf());
        return associado;
    }

    @Override
    public AssociadoTable converterAssociadoInputParaAssociadoTable(CadastrarAssociadoUseCaseInput associadoInput) {
        var associadoTable = new AssociadoTable();
        associadoTable.setCpf(associadoInput.cpf());
        return associadoTable;
    }
}
