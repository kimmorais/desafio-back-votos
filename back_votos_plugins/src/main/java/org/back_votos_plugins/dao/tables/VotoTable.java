package org.back_votos_plugins.dao.tables;

import lombok.Getter;
import lombok.Setter;
import org.back_votos_core.entities.constants.VotoEnum;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
public class VotoTable {

    @Id
    @Column(name = "Ed")
    @GeneratedValue
    private UUID id;

    @Column(name = "id_associado")
    private UUID idAssociado;

    @Enumerated(EnumType.STRING)
    @Column(name = "votoEnum")
    private VotoEnum votoEnum;
}
