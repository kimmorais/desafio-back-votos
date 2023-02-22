package org.back_votos_plugins.use_cases.obter_resultado_assembleia.use_case_provider.port_adapter;

import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.entities.Pauta;
import org.back_votos_core.entities.Voto;
import org.back_votos_core.entities.constants.VotoEnum;
import org.back_votos_core.entities.impl.AssembleiaImpl;
import org.back_votos_core.entities.impl.PautaImpl;
import org.back_votos_core.entities.impl.VotoImpl;
import org.back_votos_core.use_cases.obter_resultado_assembleia.input.ObterResultadoAssembleiaUseCaseInput;
import org.back_votos_plugins.common.exceptions.AssembleiaNaoEncontradaException;
import org.back_votos_plugins.dao.repositories.AssembleiaRepository;
import org.back_votos_plugins.dao.tables.AssembleiaTable;
import org.back_votos_plugins.dao.tables.PautaTable;
import org.back_votos_plugins.dao.tables.VotoTable;
import org.back_votos_plugins.dao.tables.mappers.AssembleiaTableMapper;
import org.back_votos_plugins.use_cases.obter_resultado_assembleia.use_case_provider.port_adapter.exceptions.AssembleiaEmAndamentoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ObterResultadoAssembleiaPortAdapterTest {

    static final UUID ID_ASSEMBLEIA = UUID.fromString("e9543ff7-4957-4bef-b644-a898ac154a96");
    static final UUID ID_PAUTA = UUID.fromString("f3213a7f-95f1-473d-8ffc-7651154d5f41");
    static final UUID ID_VOTO = UUID.fromString("261903ea-d50a-4225-8a30-a0da4ab1f1c9");
    static final UUID ID_ASSOCIADO = UUID.fromString("dc495b7b-061c-4767-b1e7-bc87a33701dd");
    static final String NOME_PAUTA = "Teste";
    static final VotoEnum VOTO_ENUM = VotoEnum.SIM;
    static final Boolean VENCEDORA = true;
    static final Integer VOTOS_SIM = 1;
    static final Integer VOTOS_NAO = 0;
    static final LocalDateTime MOMENTO_REQUEST = LocalDateTime.of(2023, 2, 19, 15, 10, 0, 0);
    static final LocalDateTime FIM_ASSEMBLEIA = LocalDateTime.of(2023, 2, 19, 15, 0, 0, 0);
    static final LocalDateTime FIM_ASSEMBLEIA_EM_ANDAMENTO = LocalDateTime.of(2023, 2, 19, 15, 30, 0, 0);
    static final String MENSAGEM_ASSEMBLEIA_NAO_ENCONTRADA = "Não foi possível encontrar uma assembleia com ID " + ID_ASSEMBLEIA;
    static final String MENSAGEM_ASSEMBLEIA_EM_ANDAMENTO = "Esta assembleia ainda está em andamento. Tente novamente " +
            "após " + FIM_ASSEMBLEIA_EM_ANDAMENTO + " para saber o resultado. \nMomento da solicitação: " + MOMENTO_REQUEST;

    @Mock
    AssembleiaRepository assembleiaRepository;

    @Mock
    AssembleiaTableMapper assembleiaTableMapper;

    @InjectMocks
    ObterResultadoAssembleiaPortAdapter adapter;

    @Test
    @DisplayName("Ao obter o resultado de uma nova assembleia, deve atualizar se ela é vencedora e retornar")
    void obterResultado_assembleiaInputValido_retornarAssembleia() {

        var assembleiaEsperada = criarAssembleiaEsperada();
        var assembleiaTableRetornada = criarAssembleiaTableRetornada(FIM_ASSEMBLEIA);
        var assembleiaInput = criarAssembleiaInput();
        var assembleiaTableAtualizada = criarAssembleiaTableAtualizada();

        when(this.assembleiaRepository.findById(ID_ASSEMBLEIA)).thenReturn(Optional.of(assembleiaTableRetornada));
        when(this.assembleiaRepository.save(any(AssembleiaTable.class))).thenReturn(assembleiaTableAtualizada);
        when(this.assembleiaTableMapper.converterAssembleiaTableParaAssembleia(assembleiaTableAtualizada)).thenReturn(assembleiaEsperada);

        var retorno = this.adapter.obterResultado(assembleiaInput);

        assertEquals(assembleiaEsperada, retorno);
    }

    @Test
    @DisplayName("Ao tentar obter o resultado de uma Assembleia que não existe, deve lançar AssembleiaNaoEncontradaException")
    void obterResultado_assembleiaNaoExiste_lancarAssembleiaNaoEncontradaException() {

        var assembleiaInput = criarAssembleiaInput();

        when(this.assembleiaRepository.findById(ID_ASSEMBLEIA)).thenReturn(Optional.empty());

        assertThatExceptionOfType(AssembleiaNaoEncontradaException.class)
                .isThrownBy(() -> this.adapter.obterResultado(assembleiaInput))
                .withMessage(MENSAGEM_ASSEMBLEIA_NAO_ENCONTRADA);
    }

    @Test
    @DisplayName("Ao tentar obter o resultado de uma assembleia em andamento, deve lancar AssembleiaEmAndamentoException")
    void obterResultado_assembleiaEmAndamento_lancarAssembleiaEmAndamentoException() {

        var assembleiaTableRetornada = criarAssembleiaTableRetornada(FIM_ASSEMBLEIA_EM_ANDAMENTO);
        var assembleiaInput = criarAssembleiaInput();

        when(this.assembleiaRepository.findById(ID_ASSEMBLEIA)).thenReturn(Optional.of(assembleiaTableRetornada));

        assertThatExceptionOfType(AssembleiaEmAndamentoException.class)
                .isThrownBy(() -> this.adapter.obterResultado(assembleiaInput))
                .withMessage(MENSAGEM_ASSEMBLEIA_EM_ANDAMENTO);
    }

    private AssembleiaTable criarAssembleiaTableRetornada(LocalDateTime fimAssembleia) {
        var assembleiaTable = new AssembleiaTable();
        assembleiaTable.setId(ID_ASSEMBLEIA);
        assembleiaTable.setPauta(criarPautaTable());
        assembleiaTable.setVotos(criarListaVotoTable());
        assembleiaTable.setQtdVotosSim(VOTOS_SIM);
        assembleiaTable.setQtdVotosNao(VOTOS_NAO);
        assembleiaTable.setFimAssembleia(fimAssembleia);
        return assembleiaTable;
    }

    private List<VotoTable> criarListaVotoTable() {
        var votoTable = criarVotoTable();
        var listaVotoTable = new ArrayList<VotoTable>();
        listaVotoTable.add(votoTable);
        return listaVotoTable;
    }

    private VotoTable criarVotoTable() {
        var voto = new VotoTable();
        voto.setId(ID_VOTO);
        voto.setVotoEnum(VOTO_ENUM);
        voto.setIdAssociado(ID_ASSOCIADO);
        return voto;
    }

    private PautaTable criarPautaTable() {
        var pautaTable = new PautaTable();
        pautaTable.setId(ID_PAUTA);
        pautaTable.setNome(NOME_PAUTA);
        return pautaTable;
    }

    private ObterResultadoAssembleiaUseCaseInput criarAssembleiaInput() {
        return new ObterResultadoAssembleiaUseCaseInput(ID_ASSEMBLEIA, MOMENTO_REQUEST);
    }

    private AssembleiaTable criarAssembleiaTableAtualizada() {
        var assembleia = new AssembleiaTable();
        assembleia.setId(ID_ASSEMBLEIA);
        assembleia.setPauta(criarPautaTable());
        assembleia.setVotos(criarListaVotoTable());
        assembleia.setQtdVotosSim(VOTOS_SIM);
        assembleia.setQtdVotosNao(VOTOS_NAO);
        assembleia.setFimAssembleia(FIM_ASSEMBLEIA);
        assembleia.setVencedora(VENCEDORA);
        return assembleia;
    }

    private Assembleia criarAssembleiaEsperada() {
        var assembleia = new AssembleiaImpl();
        assembleia.setIdAssembleia(ID_ASSEMBLEIA);
        assembleia.setPauta(criarPauta());
        assembleia.setVotos(criarListaVotos());
        assembleia.setVencedora(VENCEDORA);
        assembleia.setQtdVotosSim(VOTOS_SIM);
        assembleia.setQtdVotosNao(VOTOS_NAO);
        return assembleia;
    }

    private List<Voto> criarListaVotos() {
        var voto = criarVoto();
        var listaVotos = new ArrayList<Voto>();
        listaVotos.add(voto);
        return listaVotos;
    }

    private Voto criarVoto() {
        var voto = new VotoImpl();
        voto.setIdVoto(ID_VOTO);
        voto.setVotoEnum(VOTO_ENUM);
        voto.setIdAssociado(ID_ASSOCIADO);
        return voto;
    }

    private Pauta criarPauta() {
        var pauta = new PautaImpl();
        pauta.setIdPauta(ID_PAUTA);
        pauta.setNome(NOME_PAUTA);
        return pauta;
    }
}