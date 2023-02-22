package org.back_votos_core.entities;

import java.util.UUID;

public abstract class Associado {

    protected UUID idAssociado;
    protected String cpf;

    public abstract UUID getIdAssociado();
    public abstract void setIdAssociado(UUID id);
    public abstract String getCpf();
    public abstract void setCpf(String cpf);
}
