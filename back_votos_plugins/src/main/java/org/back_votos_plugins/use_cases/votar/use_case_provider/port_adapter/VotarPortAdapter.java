package org.back_votos_plugins.use_cases.votar.use_case_provider.port_adapter;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Voto;
import org.back_votos_core.entities.constants.VotoEnum;
import org.back_votos_core.use_cases.votar.impl.ports.VotarPort;
import org.back_votos_core.use_cases.votar.input.VotarUseCaseInput;
import org.back_votos_plugins.dao.repositories.AssembleiaRepository;
import org.back_votos_plugins.dao.repositories.AssociadoRepository;
import org.back_votos_plugins.dao.repositories.VotoRepository;
import org.back_votos_plugins.dao.tables.AssembleiaTable;
import org.back_votos_plugins.dao.tables.VotoTable;
import org.back_votos_plugins.dao.tables.mappers.VotoTableMapper;
import org.back_votos_plugins.use_cases.votar.use_case_provider.port_adapter.exceptions.AssembleiaFinalizadaException;
import org.back_votos_plugins.common.exceptions.AssembleiaNaoEncontradaException;
import org.back_votos_plugins.use_cases.votar.use_case_provider.port_adapter.exceptions.AssociadoNaoCadastradoException;
import org.back_votos_plugins.use_cases.votar.use_case_provider.port_adapter.exceptions.AssociadoNaoPodeVotarException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.IntConsumer;

@Component
@RequiredArgsConstructor
public class VotarPortAdapter implements VotarPort {

    private final VotoTableMapper votoTableMapper;
    private final VotoRepository votoRepository;
    private final AssociadoRepository associadoRepository;
    private final AssembleiaRepository assembleiaRepository;

    @Override
    public Voto votar(VotarUseCaseInput voto) {
        verificarSeAssociadoEstaCadastrado(voto.idAssociado());
        var assembleia = verificarValidadeAssembleia(voto.idAssembleia());
        verificarSeEPossivelVotar(assembleia.getFimAssembleia(), voto.horarioVoto(), assembleia.getVotos(), voto.idAssociado());

        var votoTable = salvarVoto(voto);
        atualizarAssembleia(assembleia, votoTable);

        return this.votoTableMapper.converterVotoTableParaVoto(votoTable);
    }

    private void verificarSeAssociadoEstaCadastrado(UUID idAssociado) {
        var associado = this.associadoRepository.findById(idAssociado);

        if (associado.isEmpty()) {
            throw new AssociadoNaoCadastradoException(idAssociado);
        }
    }

    private AssembleiaTable verificarValidadeAssembleia(UUID idAssembleia) {
        return this.assembleiaRepository.findByIdWithPauta(idAssembleia).orElseThrow(() -> new AssembleiaNaoEncontradaException(idAssembleia));
    }

    private void verificarSeEPossivelVotar(LocalDateTime fimAssembleia, LocalDateTime horarioVoto, List<VotoTable> votos, UUID idAssociado) {
        if (horarioVoto.isAfter(fimAssembleia)) {
            throw new AssembleiaFinalizadaException(fimAssembleia, horarioVoto);
        }

        votos.stream().filter(votoTable -> idAssociado.equals(votoTable.getIdAssociado())).findFirst().ifPresent(voto -> {
            throw new AssociadoNaoPodeVotarException(voto.toString());
        });
    }

    private VotoTable salvarVoto(VotarUseCaseInput voto) {
        var votoTable = this.votoTableMapper.converterVotoInputParaVotoTable(voto);
        return this.votoRepository.save(votoTable);
    }

    private void atualizarAssembleia(AssembleiaTable assembleia, VotoTable voto) {

        computarVoto(assembleia::setQtdVotosSim, assembleia.getQtdVotosSim(), VotoEnum.SIM, voto.getVotoEnum());
        computarVoto(assembleia::setQtdVotosNao, assembleia.getQtdVotosNao(), VotoEnum.NAO, voto.getVotoEnum());
        assembleia.getVotos().add(voto);

        this.assembleiaRepository.save(assembleia);
    }

    private static void computarVoto(IntConsumer setter, Integer quantidadeVotos, VotoEnum votoEnum, VotoEnum novoVoto) {
        if (votoEnum.equals(novoVoto)) {
            setter.accept(quantidadeVotos == null ? 1 : quantidadeVotos + 1);
        }
    }
}
