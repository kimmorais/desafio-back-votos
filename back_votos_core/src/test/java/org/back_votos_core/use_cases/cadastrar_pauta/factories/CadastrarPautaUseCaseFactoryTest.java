package org.back_votos_core.use_cases.cadastrar_pauta.factories;

import org.back_votos_core.use_cases.cadastrar_pauta.impl.CadastrarPautaUseCaseImpl;
import org.back_votos_core.use_cases.cadastrar_pauta.impl.ports.CadastrarPautaPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CadastrarPautaUseCaseFactoryTest {

    @Mock
    CadastrarPautaPort cadastrarPautaPort;

    @InjectMocks
    CadastrarPautaUseCaseFactory factory;

    @Test
    @DisplayName("Ao chamar o método, deve retornar uma nova instância de CadastrarPautaUseCaseImpl")
    void makeInstance_chamadaValida_retornarInstanciaCadastrarPautaUseCaseImpl() {

        var retornoEsperado = new CadastrarPautaUseCaseImpl(this.cadastrarPautaPort);

        var retorno = this.factory.makeInstance();

        assertThat(retorno).usingRecursiveComparison().isEqualTo(retornoEsperado);
    }
}