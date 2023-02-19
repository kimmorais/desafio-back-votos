package org.back_votos_core.entities;

import java.util.UUID;

public abstract class Associado {

    protected UUID idAssociado;
    protected String cpf;

    public abstract void setCpf(String cpf);
    public abstract String getCpf();
}
