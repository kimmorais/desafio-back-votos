package org.back_votos_plugins.dao.tables;

import lombok.Getter;
import lombok.Setter;
import org.back_votos_core.entities.constants.VotoEnum;

import javax.persistence.*;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VotoTable votoTable = (VotoTable) o;
        return id.equals(votoTable.id) && idAssociado.equals(votoTable.idAssociado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idAssociado);
    }

    @Override
    public String toString() {
        return "Voto: {" +
                " idAssociado: " + idAssociado +
                ", voto: " + votoEnum +
                " }";
    }
}
