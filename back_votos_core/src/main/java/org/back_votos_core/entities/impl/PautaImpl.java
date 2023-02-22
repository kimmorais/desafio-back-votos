package org.back_votos_core.entities.impl;

import org.back_votos_core.entities.Pauta;

import java.util.UUID;

public class PautaImpl extends Pauta {
    @Override
    public void setIdPauta(UUID id) {
        this.idPauta = id;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getNome() {
        return this.nome;
    }
}
