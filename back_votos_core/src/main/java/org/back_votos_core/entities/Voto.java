package org.back_votos_core.entities;

import org.back_votos_core.entities.constants.VotoEnum;

import java.util.UUID;

public abstract class Voto {

    protected UUID idVoto;
    protected UUID idAssociado;
    protected VotoEnum votoEnum;

    public abstract void setIdVoto(UUID idVoto);
    public abstract UUID getIdAssociado();
    public abstract void setIdAssociado(UUID associado);
    public abstract void setVotoEnum(VotoEnum votoEnum);
}
