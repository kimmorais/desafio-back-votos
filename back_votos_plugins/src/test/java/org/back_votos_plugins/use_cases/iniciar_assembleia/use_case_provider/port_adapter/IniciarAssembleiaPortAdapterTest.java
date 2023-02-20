package org.back_votos_plugins.use_cases.iniciar_assembleia.use_case_provider.port_adapter;

import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.entities.Pauta;
import org.back_votos_core.entities.impl.AssembleiaImpl;
import org.back_votos_core.entities.impl.PautaImpl;
import org.back_votos_core.use_cases.iniciar_assembleia.input.IniciarAssembleiaUseCaseInput;
import org.back_votos_plugins.dao.repositories.AssembleiaRepository;
import org.back_votos_plugins.dao.repositories.PautaRepository;
import org.back_votos_plugins.dao.tables.AssembleiaTable;
import org.back_votos_plugins.dao.tables.PautaTable;
import org.back_votos_plugins.dao.tables.mappers.AssembleiaTableMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IniciarAssembleiaPortAdapterTest {

    static final UUID ID_ASSEMBLEIA = UUID.fromString("16d14880-90f6-4a6f-b20c-2dacfee8e11d");
    static final UUID ID_PAUTA = UUID.fromString("846af96e-e305-4c8a-b372-55ae5b3c579b");
    static final String NOME_PAUTA = "TESTE";
    static final LocalDateTime FIM_ASSEMBLEIA = LocalDateTime.of(2023, 2, 19, 20, 0, 0, 0);

    @Mock
    PautaRepository pautaRepository;

    @Mock
    AssembleiaRepository assembleiaRepository;

    @Mock
    AssembleiaTableMapper assembleiaTableMapper;

    @InjectMocks
    IniciarAssembleiaPortAdapter adapter;

    @Test
    @DisplayName("Deve iniciar uma nova assembleia e retorna-la")
    void iniciarAssembleia_dadosValidos_deveIniciarAssembleia() {

        var assembleiaEsperada = criarAssembleiaEsperada();
        var assembleiaInput = criarAssembleiaInput();
        var pautaRetornada = criarPautaTable();
        var assembleiaTableRetornada = criarAssembleiaTable();

        when(this.pautaRepository.findByNome(NOME_PAUTA)).thenReturn(Optional.of(pautaRetornada));
        when(this.assembleiaRepository.save(any(AssembleiaTable.class))).thenReturn(assembleiaTableRetornada);
        when(this.assembleiaTableMapper.converterAssembleiaTableParaAssembleia(assembleiaTableRetornada)).thenReturn(assembleiaEsperada);

        var retorno = this.adapter.iniciarAssembleia(assembleiaInput);

        assertEquals(assembleiaEsperada, retorno);
    }

    private AssembleiaTable criarAssembleiaTable() {
        var assembleia = new AssembleiaTable();
        assembleia.setId(ID_ASSEMBLEIA);
        assembleia.setPauta(criarPautaTable());
        assembleia.setFimAssembleia(FIM_ASSEMBLEIA);
        return assembleia;
    }

    private IniciarAssembleiaUseCaseInput criarAssembleiaInput() {
        return new IniciarAssembleiaUseCaseInput(criarPauta(null), FIM_ASSEMBLEIA);
    }

    private Assembleia criarAssembleiaEsperada() {
        var assembleia = new AssembleiaImpl();
        assembleia.setIdAssembleia(ID_ASSEMBLEIA);
        assembleia.setPauta(criarPauta(ID_PAUTA));
        assembleia.setVotos(new ArrayList<>());
        return assembleia;
    }

    private Pauta criarPauta(UUID idPauta) {
        var pauta = new PautaImpl();
        pauta.setIdPauta(idPauta);
        pauta.setNome(NOME_PAUTA);
        return pauta;
    }

    private PautaTable criarPautaTable() {
        var pauta = new PautaTable();
        pauta.setId(ID_PAUTA);
        pauta.setNome(NOME_PAUTA);
        return pauta;
    }
}