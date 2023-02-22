package org.back_votos_core.entities.impl;

import org.back_votos_core.entities.Voto;
import org.back_votos_core.entities.constants.VotoEnum;

import java.util.UUID;

public class VotoImpl extends Voto {

    @Override
    public void setIdVoto(UUID idVoto) {
        this.idVoto = idVoto;
    }

    @Override
    public UUID getIdAssociado() {
        return this.idAssociado;
    }

    @Override
    public void setIdAssociado(UUID idAssociado) {
        this.idAssociado = idAssociado;
    }

    @Override
    public void setVotoEnum(VotoEnum votoEnum) {
        this.votoEnum = votoEnum;
    }
}
