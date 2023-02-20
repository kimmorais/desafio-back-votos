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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IniciarAssembleiaUseCaseRestEndpointTest {

    static final LocalDateTime MOMENTO_REQUEST = LocalDateTime.of(2023, 2, 19, 15, 0, 0, 0);
    static final Clock FIXED_CLOCK = Clock.fixed(MOMENTO_REQUEST.toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
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
    EntityFactories entityFactories;

    @Mock
    PautaFactory pautaFactory;

    @Mock
    IniciarAssembleiaUseCase iniciarAssembleiaUseCase;

    IniciarAssembleiaUseCaseRestEndpoint endpoint;

    @BeforeEach
    void setUp() {

        this.endpoint = new IniciarAssembleiaUseCaseRestEndpoint(FIXED_CLOCK, this.entityFactories, this.iniciarAssembleiaUseCase);

        when(this.entityFactories.pautaFactory()).thenReturn(this.pautaFactory);
        when(this.pautaFactory.makeNewInstance()).thenReturn(new PautaImpl());
    }

    @ParameterizedTest
    @MethodSource("criarEntradasValidas")
    @DisplayName("Dada uma request válida, deve iniciar uma nova Assembleia e retorná-la")
    void iniciarAssembleia_requestValida_retornarNovaAssembleia(String tempoAssembleia) {

        var assembleiaEsperada = criarAssembleia();
        var assembleiaInput = criarAssembleiaInput(tempoAssembleia);
        var assembleiaRequest = criarAssembleiaRequest();

        when(this.iniciarAssembleiaUseCase.execute(assembleiaInput)).thenReturn(assembleiaEsperada);

        var retorno = this.endpoint.iniciarAssembleia(tempoAssembleia, assembleiaRequest);

        assertAll(() -> {
            verify(this.iniciarAssembleiaUseCase).execute(assembleiaInput);
            assertEquals(ResponseEntity.ok(assembleiaEsperada), retorno);
        });
    }

    @ParameterizedTest
    @MethodSource("criarEntradasInvalidas")
    @DisplayName("Dado um tempo de assembleia no passado ou presente (mesmo momento da request), deve lançar AssembleiaDeveFinalizarNoFuturoException")
    void handleException_tempoAssembleiaAtualOuPassado_lancarAssembleiaDeveFinalizarNoFuturoException(String tempoAssembleia) {

        var assembleiaRequest = criarAssembleiaRequest();
        var tempoAtual = LocalDateTime.now(FIXED_CLOCK);
        var tempoAssembleiaFormatado = LocalDateTime.parse(tempoAssembleia, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        var exception = criarException(tempoAtual, tempoAssembleiaFormatado);
        var retornoEsperado = new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

        this.endpoint.iniciarAssembleia(tempoAssembleia, assembleiaRequest);
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

    private IniciarAssembleiaUseCaseInput criarAssembleiaInput(String tempoAssembleia) {
        return new IniciarAssembleiaUseCaseInput(criarPauta(null), validarTempoAssembleia(tempoAssembleia));
    }

    private LocalDateTime validarTempoAssembleia(String tempoAssembleia) {
        return tempoAssembleia == null ?
                LocalDateTime.now(FIXED_CLOCK).plusMinutes(1) :
                LocalDateTime.parse(tempoAssembleia, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    private Assembleia criarAssembleia() {
        var assembleia = new AssembleiaImpl();
        assembleia.setIdAssembleia(ID_ASSEMBLEIA);
        assembleia.setPauta(criarPauta(ID_PAUTA));
        assembleia.setVotos(new ArrayList<>());
        return assembleia;
    }

    private Pauta criarPauta(UUID idPauta) {
        var pauta = this.entityFactories.pautaFactory().makeNewInstance();
        pauta.setIdPauta(idPauta);
        pauta.setNome(NOME_PAUTA);
        return pauta;
    }
}