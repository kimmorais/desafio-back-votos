package org.back_votos_core.entities.factories;

import org.back_votos_core.contratos.EntityFactory;
import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.entities.impl.AssembleiaImpl;

public class AssembleiaFactory implements EntityFactory<Assembleia> {
    @Override
    public Assembleia makeNewInstance() {
        return new AssembleiaImpl();
    }
}
