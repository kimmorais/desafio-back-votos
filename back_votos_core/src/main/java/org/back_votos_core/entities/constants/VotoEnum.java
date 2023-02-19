package org.back_votos_core.entities.constants;

public enum VotoEnum {

    SIM("SIM"),
    NAO("NAO");

    private final String valor;

    VotoEnum(String valor) { this.valor = valor; }

    public String getValor() { return this.valor; }
}
