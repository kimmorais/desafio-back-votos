package org.back_votos_core.entities.factories;

import org.back_votos_core.contratos.EntityFactory;
import org.back_votos_core.entities.Pauta;
import org.back_votos_core.entities.impl.PautaImpl;

public class PautaFactory implements EntityFactory<Pauta> {
    @Override
    public Pauta makeNewInstance() {
        return new PautaImpl();
    }
}
