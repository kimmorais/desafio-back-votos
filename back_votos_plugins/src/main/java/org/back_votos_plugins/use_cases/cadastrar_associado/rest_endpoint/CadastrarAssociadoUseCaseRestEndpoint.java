package org.back_votos_plugins.use_cases.cadastrar_associado.rest_endpoint;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Associado;
import org.back_votos_core.use_cases.cadastrar_associado.CadastrarAssociadoUseCase;
import org.back_votos_core.use_cases.cadastrar_associado.input.CadastrarAssociadoUseCaseInput;
import org.back_votos_plugins.use_cases.cadastrar_associado.rest_endpoint.request_model.AssociadoRequestModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CadastrarAssociadoUseCaseRestEndpoint {

    private final CadastrarAssociadoUseCase cadastrarAssociadoUseCase;

    @PostMapping("/cadastro/associado")
    public ResponseEntity<Associado> cadastrarAssociado(@RequestBody AssociadoRequestModel associadoRequest) {
        var associadoInput = new CadastrarAssociadoUseCaseInput(formatarCpfAssociado(associadoRequest.getCpf()));

        return ResponseEntity.ok(this.cadastrarAssociadoUseCase.execute(associadoInput));
    }

    private static String formatarCpfAssociado(String cpf) {
        return cpf.replace(".", "").replace("-", "");
    }
}
