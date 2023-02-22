package org.back_votos_plugins.use_cases.votar.rest_endpoint;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Voto;
import org.back_votos_core.use_cases.votar.VotarUseCase;
import org.back_votos_core.use_cases.votar.input.VotarUseCaseInput;
import org.back_votos_plugins.use_cases.votar.rest_endpoint.request_model.VotoRequestModel;
import org.back_votos_plugins.use_cases.votar.use_case_provider.port_adapter.exceptions.AssembleiaFinalizadaException;
import org.back_votos_plugins.common.exceptions.AssembleiaNaoEncontradaException;
import org.back_votos_plugins.use_cases.votar.use_case_provider.port_adapter.exceptions.AssociadoNaoCadastradoException;
import org.back_votos_plugins.use_cases.votar.use_case_provider.port_adapter.exceptions.AssociadoNaoPodeVotarException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class VotarUseCaseRestEndpoint {

    private final Clock clock;
    private final VotarUseCase votarUseCase;

    @PutMapping("/assembleia/{idAssembleia}/voto")
    public ResponseEntity<Voto> votar(@PathVariable String idAssembleia, @RequestBody VotoRequestModel votoRequest) {
        var horarioVoto = LocalDateTime.now(this.clock);
        var votoInput = new VotarUseCaseInput(UUID.fromString(idAssembleia), votoRequest.getIdAssociado(), votoRequest.getVoto(), horarioVoto);

        return ResponseEntity.ok(this.votarUseCase.execute(votoInput));
    }

    @ExceptionHandler(value = AssociadoNaoCadastradoException.class)
    public ResponseEntity<String> handleException(AssociadoNaoCadastradoException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AssembleiaNaoEncontradaException.class)
    public ResponseEntity<String> handleException(AssembleiaNaoEncontradaException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AssembleiaFinalizadaException.class)
    public ResponseEntity<String> handleException(AssembleiaFinalizadaException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AssociadoNaoPodeVotarException.class)
    public ResponseEntity<String> handleException(AssociadoNaoPodeVotarException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
