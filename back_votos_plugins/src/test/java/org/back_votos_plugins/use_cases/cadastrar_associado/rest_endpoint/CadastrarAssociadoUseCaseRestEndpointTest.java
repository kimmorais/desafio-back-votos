package org.back_votos_plugins.use_cases.cadastrar_associado.rest_endpoint;

import org.back_votos_core.entities.Associado;
import org.back_votos_core.entities.impl.AssociadoImpl;
import org.back_votos_core.use_cases.cadastrar_associado.CadastrarAssociadoUseCase;
import org.back_votos_core.use_cases.cadastrar_associado.input.CadastrarAssociadoUseCaseInput;
import org.back_votos_plugins.use_cases.cadastrar_associado.rest_endpoint.request_model.AssociadoRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastrarAssociadoUseCaseRestEndpointTest {

    static final UUID ID_ASSOCIADO = UUID.fromString("aafd9009-8864-4ec2-b66f-d9d245b44ad3");
    static final String CPF_ASSOCIADO = "392.856.204-81";
    static final String CPF_ASSOCIADO_FORMATADO = "39285620481";

    @Mock
    CadastrarAssociadoUseCase cadastrarAssociadoUseCase;

    @InjectMocks
    CadastrarAssociadoUseCaseRestEndpoint endpoint;

    @Test
    @DisplayName("Dada uma request válida, deve retornar o Associado cadastrado")
    void cadastrarAssociado_requestValida_retornarAssociadoCadastrado() {

        var associadoEsperado = criarAssociado();
        var associadoInput = criarAssociadoInput();
        var associadoRequest = criarAssociadoRequest();

        when(this.cadastrarAssociadoUseCase.execute(associadoInput)).thenReturn(associadoEsperado);

        var retorno = this.endpoint.cadastrarAssociado(associadoRequest);

        assertAll(() -> {
            verify(this.cadastrarAssociadoUseCase).execute(associadoInput);
            assertEquals(ResponseEntity.ok(associadoEsperado), retorno);
        });
    }

    private Associado criarAssociado() {

        var associado = new AssociadoImpl();
        associado.setIdAssociado(ID_ASSOCIADO);
        associado.setCpf(CPF_ASSOCIADO_FORMATADO);

        return associado;
    }

    private CadastrarAssociadoUseCaseInput criarAssociadoInput() {
        return new CadastrarAssociadoUseCaseInput(CPF_ASSOCIADO_FORMATADO);
    }

    private AssociadoRequestModel criarAssociadoRequest() {

        var request = new AssociadoRequestModel();
        request.setCpf(CPF_ASSOCIADO);

        return request;
    }
}