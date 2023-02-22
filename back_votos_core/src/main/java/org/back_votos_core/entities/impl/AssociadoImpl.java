package org.back_votos_core.entities.impl;

import org.back_votos_core.entities.Associado;

import java.util.UUID;

public class AssociadoImpl extends Associado {

    @Override
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public UUID getIdAssociado() {
        return this.idAssociado;
    }

    @Override
    public void setIdAssociado(UUID id) {
        this.idAssociado = id;
    }
}
