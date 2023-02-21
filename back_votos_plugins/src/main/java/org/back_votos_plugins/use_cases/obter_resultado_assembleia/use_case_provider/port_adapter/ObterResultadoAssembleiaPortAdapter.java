package org.back_votos_plugins.use_cases.obter_resultado_assembleia.use_case_provider.port_adapter;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.use_cases.obter_resultado_assembleia.impl.ports.ObterResultadoAssembleiaPort;
import org.back_votos_core.use_cases.obter_resultado_assembleia.input.ObterResultadoAssembleiaUseCaseInput;
import org.back_votos_plugins.common.exceptions.AssembleiaNaoEncontradaException;
import org.back_votos_plugins.dao.repositories.AssembleiaRepository;
import org.back_votos_plugins.dao.tables.AssembleiaTable;
import org.back_votos_plugins.dao.tables.mappers.AssembleiaTableMapper;
import org.back_votos_plugins.use_cases.obter_resultado_assembleia.use_case_provider.port_adapter.exceptions.AssembleiaEmAndamentoException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ObterResultadoAssembleiaPortAdapter implements ObterResultadoAssembleiaPort {

    private final AssembleiaRepository assembleiaRepository;
    private final AssembleiaTableMapper assembleiaTableMapper;

    @Override
    public Assembleia obterResultado(ObterResultadoAssembleiaUseCaseInput input) {

        var assembleiaTable = obterAssembleia(input.idAssembleia());
        verificarSeAssembleiaJaFoiFinalizada(assembleiaTable.getFimAssembleia(), input.momentoRequest());
        var assembleiaAtualizada = atualizarSeAssembleiaEVencedora(assembleiaTable);
        return this.assembleiaTableMapper.converterAssembleiaTableParaAssembleia(assembleiaAtualizada);
    }

    private AssembleiaTable obterAssembleia(UUID idAssembleia) {
        return this.assembleiaRepository.findById(idAssembleia).orElseThrow(() -> new AssembleiaNaoEncontradaException(idAssembleia));
    }

    private void verificarSeAssembleiaJaFoiFinalizada(LocalDateTime fimAssembleia, LocalDateTime momentoRequest) {
        if (fimAssembleia.isBefore(momentoRequest)) {
            throw new AssembleiaEmAndamentoException(fimAssembleia, momentoRequest);
        }
    }

    private AssembleiaTable atualizarSeAssembleiaEVencedora(AssembleiaTable assembleiaTable) {
        var qtdVotosSim = assembleiaTable.getQtdVotosSim();
        var qtdVotosNao = assembleiaTable.getQtdVotosNao();
        assembleiaTable.setVencedora(qtdVotosSim > qtdVotosNao);

        return this.assembleiaRepository.save(assembleiaTable);
    }
}
