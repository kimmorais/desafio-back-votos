package org.back_votos_plugins.dao.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class AssembleiaTable {

    @Id
    @Column(name = "id")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "id_pauta")
    private PautaTable pauta;

    @Column(name = "vencedora")
    private Boolean vencedora;

    @Column(name = "quantidade_votos_sim")
    private Integer qtdVotosSim;

    @Column(name = "quantidade_votos_nao")
    private Integer qtdVotosNao;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "votos")
    private List<VotoTable> votos = new ArrayList<>();

    @Column(name = "fim_assembleia")
    private LocalDateTime fimAssembleia;
}
