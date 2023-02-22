package org.back_votos_plugins.use_cases.votar.use_case_provider.port_adapter;

import org.back_votos_core.entities.Voto;
import org.back_votos_core.entities.constants.VotoEnum;
import org.back_votos_core.entities.impl.VotoImpl;
import org.back_votos_core.use_cases.votar.input.VotarUseCaseInput;
import org.back_votos_plugins.dao.repositories.AssembleiaRepository;
import org.back_votos_plugins.dao.repositories.VotoRepository;
import org.back_votos_plugins.dao.tables.AssembleiaTable;
import org.back_votos_plugins.dao.tables.VotoTable;
import org.back_votos_plugins.dao.tables.mappers.VotoTableMapper;
import org.back_votos_plugins.use_cases.votar.use_case_provider.port_adapter.exceptions.AssembleiaFinalizadaException;
import org.back_votos_plugins.common.exceptions.AssembleiaNaoEncontradaException;
import org.back_votos_plugins.use_cases.votar.use_case_provider.port_adapter.exceptions.AssociadoNaoPodeVotarException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VotarPortAdapterTest {

    static final UUID ID_ASSEMBLEIA = UUID.fromString("27b62339-d656-4443-833d-2991db5ca442");
    static final UUID ID_ASSEMBLEIA_INVALIDO = UUID.fromString("cc180f85-d9aa-4970-9e9c-2deb3b08dfc1");
    static final UUID ID_VOTO = UUID.fromString("e9543ff7-4957-4bef-b644-a898ac154a96");
    static final UUID ID_ASSOCIADO = UUID.fromString("f3213a7f-95f1-473d-8ffc-7651154d5f41");
    static final VotoEnum VOTO = VotoEnum.SIM;
    static final LocalDateTime HORARIO_VOTO = LocalDateTime.of(2023, 2, 19, 15, 0, 0, 0);
    static final LocalDateTime FIM_ASSEMBLEIA_FUTURO = LocalDateTime.of(2023, 2, 19, 20, 0, 0, 0);
    static final LocalDateTime FIM_ASSEMBLEIA_HORARIO_VOTO = LocalDateTime.of(2023, 2, 19, 15, 0, 0, 0);
    static final LocalDateTime FIM_ASSEMBLEIA_PASSADO = LocalDateTime.of(2023, 2, 19, 14, 0, 0, 0);

    static final String ASSEMBLEIA_NAO_ENCONTRADA_MESSAGE = "Não foi possível encontrar uma assembleia com ID " + ID_ASSEMBLEIA_INVALIDO;
    static final String ASSEMBLEIA_FINALIZADA_MESSAGE = "Não foi possível votar nesta assembleia pois ela já foi encerrada." +
            "\nHorário final da assembleia: " + FIM_ASSEMBLEIA_PASSADO + "." +
            "\nHorário do voto: " + HORARIO_VOTO + ".";

    public static Stream<Arguments> criarEntradas() {
        return Stream.of(
                arguments(FIM_ASSEMBLEIA_HORARIO_VOTO),
                arguments(FIM_ASSEMBLEIA_FUTURO)
        );
    }

    @Mock
    VotoTableMapper votoTableMapper;

    @Mock
    VotoRepository votoRepository;

    @Mock
    AssembleiaRepository assembleiaRepository;

    @InjectMocks
    VotarPortAdapter adapter;

    @ParameterizedTest
    @MethodSource("criarEntradas")
    @DisplayName("Ao registrar um novo voto, deve fazer as validações e mappings necessários, salvar e retornar o Voto")
    void votar_votoInputValido_retornarVoto(LocalDateTime fimAssembleia) {

        var votoEsperado = criarVotoEsperado();
        var assembleiaValida = criarAssembleia(fimAssembleia);
        var votoInput = criarVotoInput();
        var votoTable = criarVotoTable(null);
        var votoTableComId = criarVotoTable(ID_VOTO);
        var assembleiaAtualizada = criarAssembleiaAtualizada(assembleiaValida, votoInput);

        when(this.assembleiaRepository.findByIdWithPauta(ID_ASSEMBLEIA)).thenReturn(Optional.of(assembleiaValida));
        when(this.votoTableMapper.converterVotoInputParaVotoTable(votoInput)).thenReturn(votoTable);
        when(this.votoRepository.save(votoTable)).thenReturn(votoTableComId);
        when(this.assembleiaRepository.save(any(AssembleiaTable.class))).thenReturn(assembleiaAtualizada);
        when(this.votoTableMapper.converterVotoTableParaVoto(votoTableComId)).thenReturn(votoEsperado);

        var retorno = this.adapter.votar(votoInput);

        assertThat(retorno).usingRecursiveComparison().isEqualTo(votoEsperado);
    }

    @Test
    @DisplayName("Ao buscar pelo ID da assembleia informada, deve lançar AssembleiaNaoEncontradaException")
    void votar_assembleiaNaoEncontrada_lancarAssembleiaNaoEncontradaException() {

        var votoInput = new VotarUseCaseInput(ID_ASSEMBLEIA_INVALIDO, null, null, null);

        when(this.assembleiaRepository.findByIdWithPauta(ID_ASSEMBLEIA_INVALIDO)).thenReturn(Optional.empty());

        assertThatExceptionOfType(AssembleiaNaoEncontradaException.class)
                .isThrownBy(() -> this.adapter.votar(votoInput))
                .withMessage(ASSEMBLEIA_NAO_ENCONTRADA_MESSAGE);
    }

    @Test
    @DisplayName("Ao verificar a data/hora que a assembleia finalizou, deve lançar AssembleiaFinalizadaException")
    void votar_assembleiaJaFinalizada_lancarAssembleiaFinalizadaException() {

        var votoInput = criarVotoInput();
        var assembleiaFinalizada = criarAssembleia(FIM_ASSEMBLEIA_PASSADO);

        when(this.assembleiaRepository.findByIdWithPauta(ID_ASSEMBLEIA)).thenReturn(Optional.of(assembleiaFinalizada));

        assertThatExceptionOfType(AssembleiaFinalizadaException.class)
                .isThrownBy(() -> this.adapter.votar(votoInput))
                .withMessage(ASSEMBLEIA_FINALIZADA_MESSAGE);
    }

    @Test
    void votar_associadoJaVotou_deveLancarAssociadoNaoPodeVotarException() {

        var votoInput = criarVotoInput();
        var assembleiaComVotoDoAssociado = criarAssembleiaComVotoDoAssociadoJaExistente();
        var mensagem = "Não é possível votar mais de uma vez em uma mesma assembleia!\n"
                + criarListaVotos(ID_ASSOCIADO, VOTO).get(0).toString();

        when(this.assembleiaRepository.findByIdWithPauta(ID_ASSEMBLEIA)).thenReturn(Optional.of(assembleiaComVotoDoAssociado));

        assertThatExceptionOfType(AssociadoNaoPodeVotarException.class)
                .isThrownBy(() -> this.adapter.votar(votoInput))
                .withMessage(mensagem);
    }

    private AssembleiaTable criarAssembleiaComVotoDoAssociadoJaExistente() {
        var assembleia = criarAssembleia(FIM_ASSEMBLEIA_FUTURO);
        var listaVotos = criarListaVotos(ID_ASSOCIADO, VOTO);
        assembleia.setVotos(listaVotos);

        return assembleia;
    }

    private AssembleiaTable criarAssembleiaAtualizada(AssembleiaTable assembleiaValida, VotarUseCaseInput votoInput) {
        var assembleiaAtualizada = new AssembleiaTable();
        assembleiaAtualizada.setId(assembleiaValida.getId());
        assembleiaAtualizada.setFimAssembleia(assembleiaValida.getFimAssembleia());
        assembleiaAtualizada.setQtdVotosSim(1);
        assembleiaAtualizada.setVotos(criarListaVotos(votoInput.idAssociado(), votoInput.votoEnum()));

        return assembleiaAtualizada;

    }

    private AssembleiaTable criarAssembleia(LocalDateTime fimAssembleiaFuturo) {
        var assembleia = new AssembleiaTable();
        assembleia.setId(ID_ASSEMBLEIA);
        assembleia.setFimAssembleia(fimAssembleiaFuturo);

        return assembleia;
    }

    private List<VotoTable> criarListaVotos(UUID idAssociado, VotoEnum voto) {
        var listaVotos = new ArrayList<VotoTable>();
        var votoTable = new VotoTable();
        votoTable.setId(ID_VOTO);
        votoTable.setVotoEnum(voto);
        votoTable.setIdAssociado(idAssociado);
        listaVotos.add(votoTable);

        return listaVotos;
    }

    private VotoTable criarVotoTable(UUID idVoto) {
        var voto = new VotoTable();
        voto.setId(idVoto);
        voto.setVotoEnum(VOTO);
        voto.setIdAssociado(ID_ASSOCIADO);

        return voto;
    }

    private VotarUseCaseInput criarVotoInput() {
        return new VotarUseCaseInput(ID_ASSEMBLEIA, ID_ASSOCIADO, VOTO, HORARIO_VOTO);
    }

    private Voto criarVotoEsperado() {
        var voto = new VotoImpl();
        voto.setIdVoto(ID_VOTO);
        voto.setIdAssociado(ID_ASSOCIADO);
        voto.setVotoEnum(VOTO);

        return voto;
    }

}