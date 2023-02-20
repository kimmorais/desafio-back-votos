package org.back_votos_core.use_cases.votar.factories;

import org.back_votos_core.use_cases.votar.impl.VotarUseCaseImpl;
import org.back_votos_core.use_cases.votar.impl.ports.VotarPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class VotarUseCaseFactoryTest {

    @Mock
    VotarPort votarPort;

    @InjectMocks
    VotarUseCaseFactory factory;

    @Test
    @DisplayName("Ao chamar o método, deve retornar uma nova instância de VotarUseCaseImpl")
    void makeInstance_chamadaValida_retornarInstanciaVotarUseCaseImpl() {

        var retornoEsperado = new VotarUseCaseImpl(this.votarPort);

        var retorno = this.factory.makeInstance();

        assertThat(retorno).usingRecursiveComparison().isEqualTo(retornoEsperado);
    }
}