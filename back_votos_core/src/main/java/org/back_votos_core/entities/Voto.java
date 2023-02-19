package org.back_votos_core.entities;

import org.back_votos_core.entities.constants.VotoEnum;

import java.util.UUID;

public abstract class Voto {

    protected UUID idVoto;
    protected Associado associado;
    protected VotoEnum votoEnum;
}
