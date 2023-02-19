package org.back_votos_core.entities.factories;

import org.back_votos_core.contratos.EntityFactory;
import org.back_votos_core.entities.Associado;
import org.back_votos_core.entities.impl.AssociadoImpl;

public class AssociadoFactory implements EntityFactory<Associado> {
    @Override
    public Associado makeNewInstance() {
        return new AssociadoImpl();
    }
}
