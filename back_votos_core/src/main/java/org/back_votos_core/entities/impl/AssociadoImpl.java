package org.back_votos_core.entities.impl;

import org.back_votos_core.entities.Associado;

public class AssociadoImpl extends Associado {

    @Override
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String getCpf() {
        return this.cpf;
    }
}
