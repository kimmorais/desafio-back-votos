package org.back_votos_plugins.use_cases.obter_resultado_assembleia.rest_endpoint;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.use_cases.obter_resultado_assembleia.ObterResultadoAssembleiaUseCase;
import org.back_votos_core.use_cases.obter_resultado_assembleia.input.ObterResultadoAssembleiaUseCaseInput;
import org.back_votos_plugins.common.exceptions.AssembleiaNaoEncontradaException;
import org.back_votos_plugins.use_cases.obter_resultado_assembleia.use_case_provider.port_adapter.exceptions.AssembleiaEmAndamentoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ObterResultadoAssembleiaRestEndpoint {

    private final Clock clock;
    private final ObterResultadoAssembleiaUseCase obterResultadoAssembleiaUseCase;

    @GetMapping("/assembleia/{idAssembleia}")
    public ResponseEntity<Assembleia> obterResultadoAssembleia(@PathVariable String idAssembleia) {
        var momentoRequest = LocalDateTime.now(this.clock);
        var assembleiaInput = new ObterResultadoAssembleiaUseCaseInput(UUID.fromString(idAssembleia), momentoRequest);

        return ResponseEntity.ok(this.obterResultadoAssembleiaUseCase.execute(assembleiaInput));
    }

    @ExceptionHandler(value = AssembleiaNaoEncontradaException.class)
    public ResponseEntity<String> handleException(AssembleiaNaoEncontradaException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AssembleiaEmAndamentoException.class)
    public ResponseEntity<String> handleException(AssembleiaEmAndamentoException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }
}
