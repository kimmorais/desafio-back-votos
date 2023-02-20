package org.back_votos_plugins.use_cases.cadastrar_pauta.rest_endpoint;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Pauta;
import org.back_votos_core.use_cases.cadastrar_pauta.CadastrarPautaUseCase;
import org.back_votos_core.use_cases.cadastrar_pauta.input.CadastrarPautaUseCaseInput;
import org.back_votos_plugins.use_cases.cadastrar_pauta.rest_endpoint.request_model.PautaRequestModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CadastrarPautaUseCaseRestEndpoint {

    private final CadastrarPautaUseCase cadastrarPautaUseCase;

    @PostMapping("/cadastro/pauta")
    public ResponseEntity<Pauta> cadastrarPauta(@RequestBody PautaRequestModel pautaRequest) {
        var pautaInput = new CadastrarPautaUseCaseInput(pautaRequest.getNome());

        return ResponseEntity.ok(this.cadastrarPautaUseCase.execute(pautaInput));
    }
}
