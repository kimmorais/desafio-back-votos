package org.back_votos_plugins.use_cases.iniciar_assembleia.rest_endpoint;

import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.entities.Pauta;
import org.back_votos_core.entities.factories.PautaFactory;
import org.back_votos_core.entities.impl.AssembleiaImpl;
import org.back_votos_core.entities.impl.PautaImpl;
import org.back_votos_core.use_cases.iniciar_assembleia.IniciarAssembleiaUseCase;
import org.back_votos_core.use_cases.iniciar_assembleia.input.IniciarAssembleiaUseCaseInput;
import org.back_votos_plugins.factories.EntityFactories;
import org.back_votos_plugins.use_cases.iniciar_assembleia.rest_endpoint.exceptions.AssembleiaDeveFinalizarNoFuturoException;
import org.back_votos_plugins.use_cases.iniciar_assembleia.rest_endpoint.request_model.IniciarAssembleiaRequestModel;
import org.back_votos_plugins.use_cases.iniciar_assembleia.use_case_provider.port_adapter.exceptions.PautaNaoExistenteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IniciarAssembleiaUseCaseRestEndpointTest {

    static final LocalDateTime MOMENTO_REQUEST = LocalDateTime.of(2023, 2, 19, 15, 0, 0, 0);
    static final Clock FIXED_CLOCK = Clock.fixed(MOMENTO_REQUEST.toInstant(ZoneOffset.UTC), ZoneId.of("UTC-03:00"));
    static final String MOMENTO_FUTURO_STRING = "2023-02-19T19:00:00.00";
    static final String MOMENTO_PASSADO_STRING = "2023-02-19T14:59:00.00";
    static final String MESMO_MOMENTO_STRING = "2023-02-19T15:00:00.00";
    static final UUID ID_ASSEMBLEIA = UUID.fromString("16d14880-90f6-4a6f-b20c-2dacfee8e11d");
    static final UUID ID_PAUTA = UUID.fromString("846af96e-e305-4c8a-b372-55ae5b3c579b");
    static final String NOME_PAUTA = "TESTE";

    static Stream<Arguments> criarEntradasValidas() {
        return Stream.of(
                arguments((Object) null),
                arguments(MOMENTO_FUTURO_STRING)
        );
    }

    static Stream<Arguments> criarEntradasInvalidas() {
        return Stream.of(
                arguments(MOMENTO_PASSADO_STRING),
                arguments(MESMO_MOMENTO_STRING)
        );
    }


    @Mock
    EntityFactories entityFactoriesMock;

    @Mock
    PautaFactory pautaFactory;

    @Mock
    IniciarAssembleiaUseCase iniciarAssembleiaUseCase;

    IniciarAssembleiaUseCaseRestEndpoint endpoint;

    @BeforeEach
    void setUp() {

        this.endpoint = new IniciarAssembleiaUseCaseRestEndpoint(FIXED_CLOCK, this.entityFactoriesMock, this.iniciarAssembleiaUseCase);
    }

    @ParameterizedTest
    @MethodSource("criarEntradasValidas")
    @DisplayName("Dada uma request válida, deve iniciar uma nova Assembleia e retorná-la")
    void iniciarAssembleia_requestValida_retornarNovaAssembleia(String tempoAssembleia) {

        var assembleiaEsperada = criarAssembleia();
        var assembleiaRequest = criarAssembleiaRequest();

        when(this.entityFactoriesMock.pautaFactory()).thenReturn(this.pautaFactory);
        when(this.pautaFactory.makeNewInstance()).thenReturn(new PautaImpl());
        when(this.iniciarAssembleiaUseCase.execute(any(IniciarAssembleiaUseCaseInput.class))).thenReturn(assembleiaEsperada);

        var retorno = this.endpoint.iniciarAssembleia(tempoAssembleia, assembleiaRequest);

        assertEquals(ResponseEntity.ok(assembleiaEsperada), retorno);
    }

    @ParameterizedTest
    @MethodSource("criarEntradasInvalidas")
    @DisplayName("Dado um tempo de assembleia no passado ou presente (mesmo momento da request), deve retornar BAD_REQUEST")
    void handleException_tempoAssembleiaAtualOuPassado_retornarBadRequest(String tempoAssembleia) {

        var assembleiaRequest = criarAssembleiaRequest();
        var tempoAtual = LocalDateTime.now(FIXED_CLOCK);
        var tempoAssembleiaFormatado = LocalDateTime.parse(tempoAssembleia, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        var exception = criarException(tempoAtual, tempoAssembleiaFormatado);
        var retornoEsperado = new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

        when(this.entityFactoriesMock.pautaFactory()).thenReturn(this.pautaFactory);
        when(this.pautaFactory.makeNewInstance()).thenReturn(new PautaImpl());

        this.endpoint.iniciarAssembleia(tempoAssembleia, assembleiaRequest);
        var retorno = this.endpoint.handleException(exception);

        assertEquals(retornoEsperado, retorno);
    }

    @Test
    @DisplayName("Ao tentar iniciar uma Assembleia para uma Pauta que não existe, deve retornar NOT_FOUND")
    void handleException_pautaNaoExistente_retornarNotFound() {

        var exception = new PautaNaoExistenteException(NOME_PAUTA);
        var retornoEsperado = new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);

        var retorno = this.endpoint.handleException(exception);

        assertEquals(retornoEsperado, retorno);
    }

    private AssembleiaDeveFinalizarNoFuturoException criarException(LocalDateTime tempoAtual, LocalDateTime tempoAssembleiaFormatado) {
        return new AssembleiaDeveFinalizarNoFuturoException(tempoAtual, tempoAssembleiaFormatado);
    }

    private IniciarAssembleiaRequestModel criarAssembleiaRequest() {
        var request = new IniciarAssembleiaRequestModel();
        request.setNomePauta(NOME_PAUTA);
        return request;
    }

    private Assembleia criarAssembleia() {
        var assembleia = new AssembleiaImpl();
        assembleia.setIdAssembleia(ID_ASSEMBLEIA);
        assembleia.setPauta(criarPauta());
        assembleia.setVotos(new ArrayList<>());
        return assembleia;
    }

    private Pauta criarPauta() {
        var pauta = new PautaImpl();
        pauta.setIdPauta(ID_PAUTA);
        pauta.setNome(NOME_PAUTA);
        return pauta;
    }
}