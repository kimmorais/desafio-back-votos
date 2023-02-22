package org.back_votos_core.entities.impl;

import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.entities.Pauta;
import org.back_votos_core.entities.Voto;

import java.util.List;
import java.util.UUID;

public class AssembleiaImpl extends Assembleia {

    @Override
    public UUID getIdAssembleia() {
        return this.idAssembleia;
    }

    @Override
    public void setIdAssembleia(UUID idAssembleia) {
        this.idAssembleia = idAssembleia;
    }

    @Override
    public Pauta getPauta() {
        return this.pauta;
    }

    @Override
    public void setPauta(Pauta pauta) {
        this.pauta = pauta;
    }

    @Override
    public Boolean getVencedora() {
        return this.vencedora;
    }

    @Override
    public void setVencedora(Boolean vencedora) {
        this.vencedora = vencedora;
    }

    @Override
    public Integer getQtdVotosSim() {
        return this.qtdVotosSim;
    }

    @Override
    public void setQtdVotosSim(Integer quantidade) {
        this.qtdVotosSim = quantidade;
    }

    @Override
    public Integer getQtdVotosNao() {
        return this.qtdVotosNao;
    }

    @Override
    public void setQtdVotosNao(Integer quantidade) {
        this.qtdVotosNao = quantidade;
    }

    @Override
    public List<Voto> getVotos() {
        return this.votos;
    }

    @Override
    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }
}
