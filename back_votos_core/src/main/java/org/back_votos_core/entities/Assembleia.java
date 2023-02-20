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

    public abstract UUID getIdAssembleia();
    public abstract void setIdAssembleia(UUID idAssembleia);
    public abstract Pauta getPauta();
    public abstract void setPauta(Pauta pauta);
    public abstract Boolean getVencedora();
    public abstract void setVencedora(Boolean vencedora);
    public abstract Integer getQtdVotosSim();
    public abstract void setQtdVotosSim(Integer quantidade);
    public abstract Integer getQtdVotosNao();
    public abstract void setQtdVotosNao(Integer quantidade);
    public abstract List<Voto> getVotos();
    public abstract void setVotos(List<Voto> votos);
}
