package org.back_votos_plugins.use_cases.iniciar_assembleia.use_case_provider.port_adapter;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.entities.Pauta;
import org.back_votos_core.use_cases.iniciar_assembleia.impl.ports.IniciarAssembleiaPort;
import org.back_votos_core.use_cases.iniciar_assembleia.input.IniciarAssembleiaUseCaseInput;
import org.back_votos_plugins.dao.repositories.AssembleiaRepository;
import org.back_votos_plugins.dao.repositories.PautaRepository;
import org.back_votos_plugins.dao.tables.AssembleiaTable;
import org.back_votos_plugins.dao.tables.PautaTable;
import org.back_votos_plugins.dao.tables.mappers.AssembleiaTableMapper;
import org.back_votos_plugins.use_cases.iniciar_assembleia.use_case_provider.port_adapter.exceptions.PautaNaoExistenteException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class IniciarAssembleiaPortAdapter implements IniciarAssembleiaPort {

    private final PautaRepository pautaRepository;
    private final AssembleiaRepository assembleiaRepository;
    private final AssembleiaTableMapper assembleiaTableMapper;

    @Override
    public Assembleia iniciarAssembleia(IniciarAssembleiaUseCaseInput input) {
        var pauta = obterPauta(input.pauta());
        var assembleiaTable = criarAssembleiaTable(pauta, input.fimAssembleia());
        return this.assembleiaTableMapper.converterAssembleiaTableParaAssembleia(this.assembleiaRepository.save(assembleiaTable));
    }

    private PautaTable obterPauta(Pauta pauta) {
        return this.pautaRepository.findByNome(pauta.getNome()).orElseThrow(() -> new PautaNaoExistenteException(pauta.getNome()));
    }

    private AssembleiaTable criarAssembleiaTable(PautaTable pauta, LocalDateTime fimAssembleia) {
        var assembleia = new AssembleiaTable();
        assembleia.setPauta(pauta);
        assembleia.setFimAssembleia(fimAssembleia);
        return assembleia;
    }
}
