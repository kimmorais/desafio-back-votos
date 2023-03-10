package org.back_votos_plugins.use_cases.iniciar_assembleia.rest_endpoint;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.entities.Pauta;
import org.back_votos_core.use_cases.iniciar_assembleia.IniciarAssembleiaUseCase;
import org.back_votos_core.use_cases.iniciar_assembleia.impl.exceptions.AssembleiaDeveFinalizarNoFuturoException;
import org.back_votos_core.use_cases.iniciar_assembleia.input.IniciarAssembleiaUseCaseInput;
import org.back_votos_plugins.factories.EntityFactories;
import org.back_votos_plugins.use_cases.iniciar_assembleia.rest_endpoint.request_model.IniciarAssembleiaRequestModel;
import org.back_votos_plugins.use_cases.iniciar_assembleia.use_case_provider.port_adapter.exceptions.PautaNaoExistenteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
public class IniciarAssembleiaUseCaseRestEndpoint {

    private final Clock clock;
    private final EntityFactories entityFactories;
    private final IniciarAssembleiaUseCase iniciarAssembleiaUseCase;

    @PostMapping("/assembleia")
    public ResponseEntity<Assembleia> iniciarAssembleia(@RequestHeader(required = false) String tempoAssembleia,
                                                        @RequestBody IniciarAssembleiaRequestModel assembleiaRequest) {
        var assembleiaInput = criarAssembleiaInput(tempoAssembleia, assembleiaRequest.getNomePauta());

        return ResponseEntity.ok(this.iniciarAssembleiaUseCase.execute(assembleiaInput));
    }

    @ExceptionHandler(value = AssembleiaDeveFinalizarNoFuturoException.class)
    public ResponseEntity<String> handleException(AssembleiaDeveFinalizarNoFuturoException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PautaNaoExistenteException.class)
    public ResponseEntity<String> handleException(PautaNaoExistenteException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    private IniciarAssembleiaUseCaseInput criarAssembleiaInput(String tempoAssembleia, String pauta) {
        var momentoRequest = LocalDateTime.now(this.clock);
        return new IniciarAssembleiaUseCaseInput(criarPauta(pauta), momentoRequest, validarTempoAssembleia(momentoRequest, tempoAssembleia));
    }

    private Pauta criarPauta(String nomePauta) {
        var pauta = this.entityFactories.pautaFactory().makeNewInstance();
        pauta.setNome(nomePauta);
        return pauta;
    }

    private LocalDateTime validarTempoAssembleia(LocalDateTime momentoRequest, String tempoAssembleia) {
        return tempoAssembleia == null ?
                momentoRequest.plusMinutes(1) :
                LocalDateTime.parse(tempoAssembleia, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
