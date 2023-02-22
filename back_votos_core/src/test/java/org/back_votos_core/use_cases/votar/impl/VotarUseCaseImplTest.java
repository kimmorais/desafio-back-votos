package org.back_votos_core.use_cases.votar.impl;

import org.back_votos_core.entities.Voto;
import org.back_votos_core.entities.constants.VotoEnum;
import org.back_votos_core.entities.impl.VotoImpl;
import org.back_votos_core.use_cases.votar.impl.ports.VotarPort;
import org.back_votos_core.use_cases.votar.input.VotarUseCaseInput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VotarUseCaseImplTest {

    static final UUID ID_ASSEMBLEIA = UUID.fromString("e9543ff7-4957-4bef-b644-a898ac154a96");
    static final UUID ID_ASSOCIADO = UUID.fromString("f3213a7f-95f1-473d-8ffc-7651154d5f41");
    static final VotoEnum VOTO = VotoEnum.SIM;
    static final LocalDateTime HORARIO_VOTO = LocalDateTime.of(2023, 2, 19, 15, 0, 0, 0);

    @Mock
    VotarPort votarPort;

    @InjectMocks
    VotarUseCaseImpl impl;

    @Test
    @DisplayName("Ao chamar o método, deve retornar Voto")
    void execute_chamadaValida_retornarVoto() {

        var votoInput = criarVotoInput();
        var votoEsperado = criarVotoEsperado();

        when(this.votarPort.votar(votoInput)).thenReturn(votoEsperado);

        var retorno = this.impl.execute(votoInput);

        assertEquals(votoEsperado, retorno);
    }

    private Voto criarVotoEsperado() {

        var voto = new VotoImpl();
        voto.setIdAssociado(ID_ASSOCIADO);
        voto.setVotoEnum(VOTO);

        return voto;
    }

    private VotarUseCaseInput criarVotoInput() {
        return new VotarUseCaseInput(ID_ASSEMBLEIA, ID_ASSOCIADO, VOTO, HORARIO_VOTO);
    }
}