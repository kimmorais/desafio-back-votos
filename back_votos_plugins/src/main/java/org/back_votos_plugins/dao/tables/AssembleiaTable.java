package org.back_votos_plugins.dao.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class AssembleiaTable {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "pauta")
    private PautaTable pauta;

    @Column(name = "vencedora")
    private Boolean vencedora;

    @Column(name = "quantidade_votos_sim")
    private Integer qtdVotosSim;

    @Column(name = "quantidade_votos_nao")
    private Integer qtdVotosNao;

    @Column(name = "votos")
    private List<VotoTable> votos;

    @Column(name = "fim_assembleia")
    private OffsetDateTime fimAssembleia;
}
