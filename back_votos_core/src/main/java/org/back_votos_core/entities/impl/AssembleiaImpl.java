package org.back_votos_core.entities.impl;

import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.entities.Pauta;
import org.back_votos_core.entities.Voto;

import java.util.List;
import java.util.UUID;

public class AssembleiaImpl extends Assembleia {

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
    public void setVencedora(Boolean vencedora) {
        this.vencedora = vencedora;
    }

    @Override
    public void setQtdVotosSim(Integer quantidade) {
        this.qtdVotosSim = quantidade;
    }

    @Override
    public void setQtdVotosNao(Integer quantidade) {
        this.qtdVotosNao = quantidade;
    }

    @Override
    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }
}
