package org.back_votos_plugins.use_cases.cadastrar_pauta.use_case_provider.port_adapter;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Pauta;
import org.back_votos_core.use_cases.cadastrar_pauta.impl.ports.CadastrarPautaPort;
import org.back_votos_core.use_cases.cadastrar_pauta.input.CadastrarPautaUseCaseInput;
import org.back_votos_plugins.dao.repositories.PautaRepository;
import org.back_votos_plugins.dao.tables.mappers.PautaTableMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastrarPautaPortAdapter implements CadastrarPautaPort {

    private final PautaTableMapper pautaTableMapper;
    private final PautaRepository pautaRepository;

    @Override
    public Pauta cadastrarPauta(CadastrarPautaUseCaseInput pauta) {
        var pautaTable = this.pautaTableMapper.converterPautaInputParaPautaTable(pauta);
        return this.pautaTableMapper.converterPautaTableParaPauta(this.pautaRepository.save(pautaTable));
    }
}
