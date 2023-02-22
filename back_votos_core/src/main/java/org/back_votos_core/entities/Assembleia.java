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

    public abstract void setIdAssembleia(UUID idAssembleia);
    public abstract Pauta getPauta();
    public abstract void setPauta(Pauta pauta);
    public abstract void setVencedora(Boolean vencedora);
    public abstract void setQtdVotosSim(Integer quantidade);
    public abstract void setQtdVotosNao(Integer quantidade);
    public abstract void setVotos(List<Voto> votos);
}
