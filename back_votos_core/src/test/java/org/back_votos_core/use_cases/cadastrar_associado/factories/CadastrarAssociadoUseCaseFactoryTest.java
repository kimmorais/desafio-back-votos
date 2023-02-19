package org.back_votos_core.use_cases.cadastrar_associado.factories;

import org.back_votos_core.use_cases.cadastrar_associado.impl.CadastrarAssociadoUseCaseImpl;
import org.back_votos_core.use_cases.cadastrar_associado.impl.ports.CadastrarAssociadoPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CadastrarAssociadoUseCaseFactoryTest {

    @Mock
    CadastrarAssociadoPort cadastrarAssociadoPort;

    @InjectMocks
    CadastrarAssociadoUseCaseFactory factory;

    @Test
    @DisplayName("Ao chamar o método, deve retornar uma nova instância de CadastrarAssociadoUseCaseImpl")
    void makeInstance_chamadaValida_retornarInstanciaCadastrarAssociadoUseCaseImpl() {

        var retornoEsperado = new CadastrarAssociadoUseCaseImpl(this.cadastrarAssociadoPort);

        var retorno = this.factory.makeInstance();

        assertThat(retorno).usingRecursiveComparison().isEqualTo(retornoEsperado);
    }
}