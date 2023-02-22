package org.back_votos_plugins.use_cases.cadastrar_pauta.use_case_provider.port_adapter;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Pauta;
import org.back_votos_core.use_cases.cadastrar_pauta.impl.ports.CadastrarPautaPort;
import org.back_votos_core.use_cases.cadastrar_pauta.input.CadastrarPautaUseCaseInput;
import org.back_votos_plugins.dao.repositories.PautaRepository;
import org.back_votos_plugins.dao.tables.mappers.PautaTableMapper;
import org.back_votos_plugins.use_cases.cadastrar_pauta.use_case_provider.port_adapter.exceptions.PautaJaCadastradaException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastrarPautaPortAdapter implements CadastrarPautaPort {

    private final PautaTableMapper pautaTableMapper;
    private final PautaRepository pautaRepository;

    @Override
    public Pauta cadastrarPauta(CadastrarPautaUseCaseInput pauta) {
        verificarSePautaJaFoiCadastrada(pauta.nomePauta());
        var pautaTable = this.pautaTableMapper.converterPautaInputParaPautaTable(pauta);
        return this.pautaTableMapper.converterPautaTableParaPauta(this.pautaRepository.save(pautaTable));
    }

    private void verificarSePautaJaFoiCadastrada(String nomePauta) {
        var pauta = this.pautaRepository.findByNome(nomePauta);

        if (pauta.isPresent()) {
            throw new PautaJaCadastradaException(nomePauta, pauta.get().getId());
        }
    }
}
