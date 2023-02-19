package org.back_votos_core.contratos;

public interface FunctionUseCase<I, O> {

    O execute(I input);
}
