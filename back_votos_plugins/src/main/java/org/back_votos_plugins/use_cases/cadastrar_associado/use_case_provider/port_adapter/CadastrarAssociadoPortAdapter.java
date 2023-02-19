package org.back_votos_plugins.use_cases.cadastrar_associado.use_case_provider.port_adapter;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Associado;
import org.back_votos_core.use_cases.cadastrar_associado.impl.ports.CadastrarAssociadoPort;
import org.back_votos_core.use_cases.cadastrar_associado.input.CadastrarAssociadoUseCaseInput;
import org.back_votos_plugins.dao.repositories.AssociadoRepository;
import org.back_votos_plugins.dao.tables.mappers.AssociadoTableMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastrarAssociadoPortAdapter implements CadastrarAssociadoPort {

    private final AssociadoTableMapper associadoTableMapper;
    private final AssociadoRepository associadoRepository;

    @Override
    public Associado cadastrarAssociado(CadastrarAssociadoUseCaseInput associado) {
        var associadoTable = associadoTableMapper.converterAssociadoInputParaAssociadoTable(associado);
        return associadoTableMapper.converterAssociadoTableParaAssociado(associadoRepository.save(associadoTable));
    }
}
