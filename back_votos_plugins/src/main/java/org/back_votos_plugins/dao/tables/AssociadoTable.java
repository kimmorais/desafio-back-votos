package org.back_votos_plugins.dao.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "associado_table")
public class AssociadoTable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID id;

    @Column(name = "cpf")
    private String cpf;
}
