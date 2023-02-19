package org.back_votos_plugins.use_cases.votar.use_case_provider.port_adapter.exceptions;

public class AssociadoNaoPodeVotarException extends RuntimeException {
    public AssociadoNaoPodeVotarException(String voto) {
        super("Não é possível votar mais de uma vez em uma mesma assembleia! \nVoto computado: " + voto);
    }
}
