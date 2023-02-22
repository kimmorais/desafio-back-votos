package org.back_votos_plugins.use_cases.cadastrar_pauta.use_case_provider.port_adapter.exceptions;

import java.util.UUID;

public class PautaJaCadastradaException extends RuntimeException {
    public PautaJaCadastradaException(String nomePauta, UUID idPauta) {
        super("Já existe uma pauta cadastrada com o nome \"" + nomePauta + "\".\n" +
                "Se deseja iniciar uma nova assembleia para essa Pauta, ID: " + idPauta);
    }
}
