package org.back_votos_core.use_cases.iniciar_assembleia.factories;

import org.back_votos_core.use_cases.iniciar_assembleia.impl.IniciarAssembleiaUseCaseImpl;
import org.back_votos_core.use_cases.iniciar_assembleia.impl.ports.IniciarAssembleiaPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class IniciarAssembleiaUseCaseFactoryTest {

    @Mock
    IniciarAssembleiaPort iniciarAssembleiaPort;

    @InjectMocks
    IniciarAssembleiaUseCaseFactory factory;

    @Test
    @DisplayName("Ao chamar o método, deve retornar uma nova instância de IniciarAssembleiaUseCaseImpl")
    void makeInstance_chamadaValida_retornarInstanciaIniciarAssembleiaUseCase() {

        var retornoEsperado = new IniciarAssembleiaUseCaseImpl(this.iniciarAssembleiaPort);

        var retorno = this.factory.makeInstance();

        assertThat(retorno).usingRecursiveComparison().isEqualTo(retornoEsperado);
    }
}