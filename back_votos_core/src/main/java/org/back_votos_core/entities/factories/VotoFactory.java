package org.back_votos_core.entities.factories;

import org.back_votos_core.contratos.EntityFactory;
import org.back_votos_core.entities.Voto;
import org.back_votos_core.entities.impl.VotoImpl;

public class VotoFactory implements EntityFactory<Voto> {
    @Override
    public Voto makeNewInstance() {
        return new VotoImpl();
    }
}
