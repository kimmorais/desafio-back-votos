package org.back_votos_plugins.use_cases.votar.rest_endpoint;

import org.back_votos_core.entities.Voto;
import org.back_votos_core.entities.constants.VotoEnum;
import org.back_votos_core.entities.impl.VotoImpl;
import org.back_votos_core.use_cases.votar.VotarUseCase;
import org.back_votos_core.use_cases.votar.input.VotarUseCaseInput;
import org.back_votos_plugins.use_cases.votar.rest_endpoint.request_model.VotoRequestModel;
import org.back_votos_plugins.use_cases.votar.use_case_provider.port_adapter.exceptions.AssembleiaFinalizadaException;
import org.back_votos_plugins.common.exceptions.AssembleiaNaoEncontradaException;
import org.back_votos_plugins.use_cases.votar.use_case_provider.port_adapter.exceptions.AssociadoNaoPodeVotarException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VotarUseCaseRestEndpointTest {

    static final LocalDateTime DATA_VOTO = LocalDateTime.of(2023, 2, 19, 15, 0, 0, 0);
    static final Clock FIXED_CLOCK = Clock.fixed(DATA_VOTO.toInstant(ZoneOffset.UTC), ZoneId.systemDefault());

    static final UUID ID_VOTO = UUID.fromString("0c1d4965-6b32-4c85-8eb3-ca09a196e1c2");
    static final UUID ID_ASSOCIADO = UUID.fromString("b6d14737-a387-48dc-90ca-fc5508ea2405");
    static final String ID_ASSEMBLEIA_PATH_VARIABLE = "16d14880-90f6-4a6f-b20c-2dacfee8e11d";
    static final VotoEnum VOTO = VotoEnum.SIM;

    @Mock
    VotarUseCase votarUseCase;

    VotarUseCaseRestEndpoint endpoint;

    @BeforeEach
    void setUp() {

        this.endpoint = new VotarUseCaseRestEndpoint(FIXED_CLOCK, this.votarUseCase);
    }

    @Test
    @DisplayName("Dada uma request válida, deve retornar o Voto computado")
    void votar_requestValida_retornarVoto() {

        var votoEsperado = criarVoto();
        var votoRequest = criarvotoRequest();
        var votoInput = criarVotoInput();

        when(this.votarUseCase.execute(votoInput)).thenReturn(votoEsperado);

        var retorno = this.endpoint.votar(ID_ASSEMBLEIA_PATH_VARIABLE, votoRequest);

        assertEquals(ResponseEntity.ok(votoEsperado), retorno);
    }

    @Test
    @DisplayName("Ao ser lançada AssembleiaNaoEncontradaException, deve executar o handler e retornar NOT_FOUND")
    void handleException_assembleiaNaoEncontradaException_retornarNotFound() {

        var exception = criarAssembleiaNaoEncontradaException();
        var retornoEsperado = new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);

        var retorno = this.endpoint.handleException(exception);

        assertEquals(retornoEsperado, retorno);
    }

    @Test
    @DisplayName("Ao ser lançada AssembleiaFinalizadaException, deve executar o handler e retornar BAD_REQUEST")
    void testHandleException_assembleiaFinalizadaException_retornarBadRequest() {

        var exception = criarAssembleiaFinalizadaException();
        var retornoEsperado = new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

        var retorno = this.endpoint.handleException(exception);

        assertEquals(retornoEsperado, retorno);
    }

    @Test
    @DisplayName("Ao ser lançada AssociadoNaoPodeVotarException, deve executar o handler e retornar UNPROCESSABLE_ENTITY")
    void testHandleException_associadoNaoPodeVotarException_retornarUnprocessableEntity() {

        var exception = criarAssociadoNaoPodeVotarException();
        var retornoEsperado = new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);

        var retorno = this.endpoint.handleException(exception);

        assertEquals(retornoEsperado, retorno);
    }

    private AssociadoNaoPodeVotarException criarAssociadoNaoPodeVotarException() {
        return new AssociadoNaoPodeVotarException(VOTO.toString());
    }

    private VotarUseCaseInput criarVotoInput() {
        return new VotarUseCaseInput(UUID.fromString(ID_ASSEMBLEIA_PATH_VARIABLE), ID_ASSOCIADO, VOTO, LocalDateTime.now(FIXED_CLOCK));
    }

    private VotoRequestModel criarvotoRequest() {
        var request = new VotoRequestModel();
        request.setIdAssociado(ID_ASSOCIADO);
        request.setVoto(VOTO);

        return request;
    }

    private Voto criarVoto() {

        var voto = new VotoImpl();
        voto.setIdVoto(ID_VOTO);
        voto.setIdAssociado(ID_ASSOCIADO);
        voto.setVotoEnum(VOTO);

        return voto;
    }

    private AssembleiaNaoEncontradaException criarAssembleiaNaoEncontradaException() {
        return new AssembleiaNaoEncontradaException(UUID.fromString(ID_ASSEMBLEIA_PATH_VARIABLE));
    }

    private AssembleiaFinalizadaException criarAssembleiaFinalizadaException() {
        return new AssembleiaFinalizadaException(LocalDateTime.now(FIXED_CLOCK), DATA_VOTO);
    }
}