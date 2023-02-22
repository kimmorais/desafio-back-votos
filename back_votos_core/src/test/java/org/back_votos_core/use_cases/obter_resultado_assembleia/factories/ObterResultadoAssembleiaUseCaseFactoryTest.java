package org.back_votos_core.use_cases.obter_resultado_assembleia.factories;

import org.back_votos_core.use_cases.obter_resultado_assembleia.impl.ObterResultadoAssembleiaUseCaseImpl;
import org.back_votos_core.use_cases.obter_resultado_assembleia.impl.ports.ObterResultadoAssembleiaPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ObterResultadoAssembleiaUseCaseFactoryTest {

    @Mock
    ObterResultadoAssembleiaPort obterResultadoAssembleiaPort;

    @InjectMocks
    ObterResultadoAssembleiaUseCaseFactory factory;

    @Test
    @DisplayName("Ao chamar o método, deve retornar uma nova instância de ObterResultadoAssembleiaUseCaseImpl")
    void makeInstance_chamadaValida_retornarInstanciaObterResultadoAssembleiaUseCaseImpl() {

        var retornoEsperado = new ObterResultadoAssembleiaUseCaseImpl(this.obterResultadoAssembleiaPort);

        var retorno = this.factory.makeInstance();

        assertThat(retorno).usingRecursiveComparison().isEqualTo(retornoEsperado);
    }
}