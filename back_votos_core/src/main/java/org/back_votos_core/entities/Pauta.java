package org.back_votos_core.entities;

import java.util.UUID;

public abstract class Pauta {

    protected UUID idPauta;
    protected String nome;

    public abstract void setIdPauta(UUID id);
    public abstract UUID getIdPauta();
    public abstract void setNome(String nome);
    public abstract String getNome();
}
