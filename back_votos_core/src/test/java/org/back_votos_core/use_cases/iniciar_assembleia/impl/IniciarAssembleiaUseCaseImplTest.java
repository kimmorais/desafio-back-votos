package org.back_votos_core.use_cases.iniciar_assembleia.impl;

import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.entities.Pauta;
import org.back_votos_core.entities.impl.AssembleiaImpl;
import org.back_votos_core.entities.impl.PautaImpl;
import org.back_votos_core.use_cases.iniciar_assembleia.impl.ports.IniciarAssembleiaPort;
import org.back_votos_core.use_cases.iniciar_assembleia.input.IniciarAssembleiaUseCaseInput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IniciarAssembleiaUseCaseImplTest {

    static final UUID ID_ASSEMBLEIA = UUID.fromString("e9543ff7-4957-4bef-b644-a898ac154a96");
    static final UUID ID_PAUTA = UUID.fromString("aafd9009-8864-4ec2-b66f-d9d245b44ad3");
    static final LocalDateTime FIM_ASSEMBLEIA = LocalDateTime.of(2023, 2, 19, 20, 0, 0, 0);
    static final String NOME_PAUTA = "Nova pauta";
    static final Integer VOTOS = 0;

    @Mock
    IniciarAssembleiaPort iniciarAssembleiaPort;

    @InjectMocks
    IniciarAssembleiaUseCaseImpl impl;

    @Test
    @DisplayName("Ao chamar o método, deve retornar uma Assembleia")
    void execute_chamadaValida_retornarAssembleia() {

        var assembleiaInput = criarAssembleiaInput();
        var assembleiaEsperada = criarAssembleiaEsperada();

        when(this.iniciarAssembleiaPort.iniciarAssembleia(assembleiaInput)).thenReturn(assembleiaEsperada);

        var retorno = this.impl.execute(assembleiaInput);

        assertEquals(assembleiaEsperada, retorno);
    }

    private Assembleia criarAssembleiaEsperada() {
        var assembleia = new AssembleiaImpl();
        assembleia.setIdAssembleia(ID_ASSEMBLEIA);
        assembleia.setPauta(criarPauta(ID_PAUTA));
        assembleia.setQtdVotosNao(VOTOS);
        assembleia.setQtdVotosSim(VOTOS);
        assembleia.setVotos(new ArrayList<>());
        return assembleia;
    }

    private IniciarAssembleiaUseCaseInput criarAssembleiaInput() {
        return new IniciarAssembleiaUseCaseInput(criarPauta(null), FIM_ASSEMBLEIA);
    }

    private Pauta criarPauta(UUID idPauta) {
        var pauta = new PautaImpl();
        pauta.setIdPauta(idPauta);
        pauta.setNome(NOME_PAUTA);
        return pauta;
    }
}