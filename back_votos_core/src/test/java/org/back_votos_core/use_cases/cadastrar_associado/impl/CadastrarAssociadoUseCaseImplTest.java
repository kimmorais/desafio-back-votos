package org.back_votos_core.use_cases.cadastrar_associado.impl;

import org.back_votos_core.entities.Associado;
import org.back_votos_core.entities.impl.AssociadoImpl;
import org.back_votos_core.use_cases.cadastrar_associado.impl.ports.CadastrarAssociadoPort;
import org.back_votos_core.use_cases.cadastrar_associado.input.CadastrarAssociadoUseCaseInput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastrarAssociadoUseCaseImplTest {

    static final String CPF_ASSOCIADO = "29372649208";

    @Mock
    CadastrarAssociadoPort cadastrarAssociadoPort;

    @InjectMocks
    CadastrarAssociadoUseCaseImpl impl;

    @Test
    @DisplayName("Ao chamar o método, deve retornar Associado")
    void execute_chamadaValida_retornarAssociado() {

        var associadoInput = criarAssociadoInput();
        var associadoEsperado = criarAssociadoEsperado();

        when(this.cadastrarAssociadoPort.cadastrarAssociado(associadoInput)).thenReturn(associadoEsperado);

        var retorno = this.impl.execute(associadoInput);

        assertEquals(associadoEsperado, retorno);
    }

    private CadastrarAssociadoUseCaseInput criarAssociadoInput() {
        return new CadastrarAssociadoUseCaseInput(CPF_ASSOCIADO);
    }

    private Associado criarAssociadoEsperado() {
        var associado = new AssociadoImpl();
        associado.setCpf(CPF_ASSOCIADO);

        return associado;
    }
}