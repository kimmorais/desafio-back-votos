package org.back_votos_core.entities.impl;

import org.back_votos_core.entities.Voto;
import org.back_votos_core.entities.constants.VotoEnum;

import java.util.UUID;

public class VotoImpl extends Voto {

    @Override
    public UUID getIdVoto() {
        return this.idVoto;
    }

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
    public VotoEnum getVotoEnum() {
        return this.votoEnum;
    }

    @Override
    public void setVotoEnum(VotoEnum votoEnum) {
        this.votoEnum = votoEnum;
    }
}
