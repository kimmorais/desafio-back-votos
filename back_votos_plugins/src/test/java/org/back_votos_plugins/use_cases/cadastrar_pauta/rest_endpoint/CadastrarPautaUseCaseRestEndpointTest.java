package org.back_votos_plugins.use_cases.cadastrar_pauta.rest_endpoint;

import org.back_votos_core.entities.Pauta;
import org.back_votos_core.entities.impl.PautaImpl;
import org.back_votos_core.use_cases.cadastrar_pauta.CadastrarPautaUseCase;
import org.back_votos_core.use_cases.cadastrar_pauta.input.CadastrarPautaUseCaseInput;
import org.back_votos_plugins.use_cases.cadastrar_pauta.rest_endpoint.request_model.PautaRequestModel;
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
class CadastrarPautaUseCaseRestEndpointTest {

    static final UUID ID_PAUTA = UUID.fromString("aafd9009-8864-4ec2-b66f-d9d245b44ad3");
    static final String NOME_PAUTA = "Pauta teste";

    @Mock
    CadastrarPautaUseCase cadastrarPautaUseCase;

    @InjectMocks
    CadastrarPautaUseCaseRestEndpoint endpoint;

    @Test
    @DisplayName("Dada uma request válida, deve retornar a Pauta cadastrada")
    void cadastrarPauta_requestValida_retornarPautaCadastrada() {

        var pautaEsperada = criarPauta();
        var pautaInput = criarPautaInput();
        var pautaRequest = criarPautaRequest();

        when(this.cadastrarPautaUseCase.execute(pautaInput)).thenReturn(pautaEsperada);

        var retorno = this.endpoint.cadastrarPauta(pautaRequest);

        assertAll(() -> {
            verify(this.cadastrarPautaUseCase).execute(pautaInput);
            assertEquals(ResponseEntity.ok(pautaEsperada), retorno);
        });
    }

    private PautaRequestModel criarPautaRequest() {
        var request = new PautaRequestModel();
        request.setNome(NOME_PAUTA);

        return request;
    }

    private CadastrarPautaUseCaseInput criarPautaInput() {
        return new CadastrarPautaUseCaseInput(NOME_PAUTA);
    }

    private Pauta criarPauta() {
        var pauta = new PautaImpl();
        pauta.setIdPauta(ID_PAUTA);
        pauta.setNome(NOME_PAUTA);

        return pauta;
    }
}