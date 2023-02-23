package org.back_votos_plugins.use_cases.obter_resultado_assembleia.rest_endpoint;

import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.entities.Pauta;
import org.back_votos_core.entities.Voto;
import org.back_votos_core.entities.constants.VotoEnum;
import org.back_votos_core.entities.impl.AssembleiaImpl;
import org.back_votos_core.entities.impl.PautaImpl;
import org.back_votos_core.entities.impl.VotoImpl;
import org.back_votos_core.use_cases.obter_resultado_assembleia.ObterResultadoAssembleiaUseCase;
import org.back_votos_core.use_cases.obter_resultado_assembleia.input.ObterResultadoAssembleiaUseCaseInput;
import org.back_votos_plugins.common.exceptions.AssembleiaNaoEncontradaException;
import org.back_votos_plugins.use_cases.obter_resultado_assembleia.use_case_provider.port_adapter.exceptions.AssembleiaEmAndamentoException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ObterResultadoAssembleiaRestEndpointTest {

    static final LocalDateTime MOMENTO_REQUEST = LocalDateTime.of(2023, 2, 19, 15, 0, 0, 0);
    static final LocalDateTime FIM_ASSEMBLEIA = LocalDateTime.of(2023, 2, 19, 20, 0, 0, 0);
    static final Clock FIXED_CLOCK = Clock.fixed(MOMENTO_REQUEST.toInstant(ZoneOffset.UTC), ZoneId.of("UTC-03:00"));
    static final UUID ID_ASSEMBLEIA = UUID.fromString("16d14880-90f6-4a6f-b20c-2dacfee8e11d");
    static final UUID ID_PAUTA = UUID.fromString("f3213a7f-95f1-473d-8ffc-7651154d5f41");
    static final UUID ID_VOTO = UUID.fromString("261903ea-d50a-4225-8a30-a0da4ab1f1c9");
    static final UUID ID_ASSOCIADO = UUID.fromString("dc495b7b-061c-4767-b1e7-bc87a33701dd");
    static final String NOME_PAUTA = "Teste";
    static final VotoEnum VOTO_ENUM = VotoEnum.SIM;
    static final Boolean VENCEDORA = true;
    static final Integer VOTOS_SIM = 23;
    static final Integer VOTOS_NAO = 19;

    @Mock
    ObterResultadoAssembleiaUseCase obterResultadoAssembleiaUseCase;

    ObterResultadoAssembleiaRestEndpoint endpoint;

    @BeforeEach
    void setUp() {

        this.endpoint = new ObterResultadoAssembleiaRestEndpoint(FIXED_CLOCK, this.obterResultadoAssembleiaUseCase);
    }

    @Test
    @DisplayName("Request valida, deve retornar a Assembleia")
    void obterResultadoAssembleia_requestValida_retornarAssembleia() {

        var assembleiaEsperada = criarAssembleia();
        var assembleiaInput = new ObterResultadoAssembleiaUseCaseInput(ID_ASSEMBLEIA, LocalDateTime.now(FIXED_CLOCK));

        when(this.obterResultadoAssembleiaUseCase.execute(assembleiaInput)).thenReturn(assembleiaEsperada);

        var retorno = this.endpoint.obterResultadoAssembleia(ID_ASSEMBLEIA.toString());

        assertEquals(ResponseEntity.ok(assembleiaEsperada), retorno);
    }

    @Test
    @DisplayName("Ao tentar obter o resultado de uma assembleia que não existe, deve retornar NOT_FOUND")
    void handleException_assembleiaNaoEncontrada_retornarNotFound() {

        var exception = criarAssembleiaNaoEncontradaException();
        var retornoEsperado = new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);

        var retorno = this.endpoint.handleException(exception);

        assertEquals(retornoEsperado, retorno);
    }

    @Test
    @DisplayName("Ao tentar obter o resultado de uma assembleia que ainda está em andamento, deve retornar CONFLICT")
    void testHandleException_assembleiaEmAndamento_retornarConflict() {

        var exception = criarAssembleiaEmAndamentoException();
        var retornoEsperado = new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);

        var retorno = this.endpoint.handleException(exception);

        assertEquals(retornoEsperado, retorno);
    }

    private AssembleiaEmAndamentoException criarAssembleiaEmAndamentoException() {
        return new AssembleiaEmAndamentoException(FIM_ASSEMBLEIA, MOMENTO_REQUEST);
    }

    private Assembleia criarAssembleia() {
        var assembleia = new AssembleiaImpl();
        assembleia.setIdAssembleia(ID_ASSEMBLEIA);
        assembleia.setVotos(criarListaVotos());
        assembleia.setPauta(criarPauta());
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

    private AssembleiaNaoEncontradaException criarAssembleiaNaoEncontradaException() {
        return new AssembleiaNaoEncontradaException(ID_ASSEMBLEIA);
    }
}