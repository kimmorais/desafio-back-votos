package org.back_votos_plugins.dao.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "pauta_table")
public class PautaTable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID id;

    @Column(name = "nome")
    private String nome;
}


