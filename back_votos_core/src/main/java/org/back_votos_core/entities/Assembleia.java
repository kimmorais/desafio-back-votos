package org.back_votos_core.entities;

import java.util.List;
import java.util.UUID;

public abstract class Assembleia {

    protected UUID idAssembleia;
    protected Pauta pauta;
    protected Boolean vencedora;
    protected Integer qtdVotosSim;
    protected Integer qtdVotosNao;
    protected List<Voto> votos;
}
