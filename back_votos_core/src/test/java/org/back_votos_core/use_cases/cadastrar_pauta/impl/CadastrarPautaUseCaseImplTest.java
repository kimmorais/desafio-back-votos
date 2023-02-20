package org.back_votos_core.use_cases.cadastrar_pauta.impl;

import org.back_votos_core.entities.Pauta;
import org.back_votos_core.entities.impl.PautaImpl;
import org.back_votos_core.use_cases.cadastrar_pauta.impl.ports.CadastrarPautaPort;
import org.back_votos_core.use_cases.cadastrar_pauta.input.CadastrarPautaUseCaseInput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastrarPautaUseCaseImplTest {

    static final String NOME_PAUTA = "Nova pauta";

    @Mock
    CadastrarPautaPort cadastrarPautaPort;

    @InjectMocks
    CadastrarPautaUseCaseImpl impl;

    @Test
    @DisplayName("Ao chamar o método, deve retornar uma nova instância de CadastrarPautaUseCaseImpl")
    void execute_chamadaValida_retornarInstanciaCadastrarPautaUseCaseImpl() {

        var pautaInput = criarPautaInput();
        var pautaEsperada = criarPautaEsperada();

        when(this.cadastrarPautaPort.cadastrarPauta(pautaInput)).thenReturn(pautaEsperada);

        var retorno = this.impl.execute(pautaInput);

        assertEquals(pautaEsperada, retorno);
    }

    private CadastrarPautaUseCaseInput criarPautaInput() {

        return new CadastrarPautaUseCaseInput(NOME_PAUTA);
    }

    private Pauta criarPautaEsperada() {

        var pauta = new PautaImpl();
        pauta.setNome(NOME_PAUTA);

        return pauta;
    }
}