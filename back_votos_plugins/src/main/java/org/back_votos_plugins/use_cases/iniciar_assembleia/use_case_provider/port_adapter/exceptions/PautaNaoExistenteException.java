package org.back_votos_plugins.use_cases.iniciar_assembleia.use_case_provider.port_adapter.exceptions;

public class PautaNaoExistenteException extends RuntimeException {
    public PautaNaoExistenteException(String nome) {
        super("Não foi possível encontrar uma pauta com o nome " + nome);
    }
}
