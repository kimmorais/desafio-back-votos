package org.back_votos_core.use_cases.obter_resultado_assembleia.impl;

import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.entities.Pauta;
import org.back_votos_core.entities.Voto;
import org.back_votos_core.entities.constants.VotoEnum;
import org.back_votos_core.entities.impl.AssembleiaImpl;
import org.back_votos_core.entities.impl.PautaImpl;
import org.back_votos_core.entities.impl.VotoImpl;
import org.back_votos_core.use_cases.obter_resultado_assembleia.impl.ports.ObterResultadoAssembleiaPort;
import org.back_votos_core.use_cases.obter_resultado_assembleia.input.ObterResultadoAssembleiaUseCaseInput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ObterResultadoAssembleiaUseCaseImplTest {

    static final UUID ID_ASSEMBLEIA = UUID.fromString("e9543ff7-4957-4bef-b644-a898ac154a96");
    static final UUID ID_PAUTA = UUID.fromString("f3213a7f-95f1-473d-8ffc-7651154d5f41");
    static final UUID ID_VOTO = UUID.fromString("261903ea-d50a-4225-8a30-a0da4ab1f1c9");
    static final UUID ID_ASSOCIADO = UUID.fromString("dc495b7b-061c-4767-b1e7-bc87a33701dd");
    static final String NOME_PAUTA = "Teste";
    static final VotoEnum VOTO_ENUM = VotoEnum.SIM;
    static final Boolean VENCEDORA = true;
    static final Integer VOTOS_SIM = 23;
    static final Integer VOTOS_NAO = 19;
    static final LocalDateTime MOMENTO_REQUEST = LocalDateTime.of(2023, 2, 19, 15, 0, 0, 0);

    @Mock
    ObterResultadoAssembleiaPort obterResultadoAssembleiaPort;

    @InjectMocks
    ObterResultadoAssembleiaUseCaseImpl impl;

    @Test
    @DisplayName("Ao chamar o método, deve retornar uma nova instância de CadastrarPautaUseCaseImpl")
    void execute_chamadaValida_retornarAssembleia() {

        var assembleiaInput = criarAssembleiaInput();
        var assembleiaEsperada = criarAssembleiaEsperada();

        when(this.obterResultadoAssembleiaPort.obterResultado(assembleiaInput)).thenReturn(assembleiaEsperada);

        var retorno = this.impl.execute(assembleiaInput);

        assertEquals(assembleiaEsperada, retorno);
    }

    private Assembleia criarAssembleiaEsperada() {
        var assembleia = new AssembleiaImpl();
        assembleia.setIdAssembleia(ID_ASSEMBLEIA);
        assembleia.setPauta(criarPauta());
        assembleia.setVotos(criarListaVotos());
        assembleia.setVencedora(VENCEDORA);
        assembleia.setQtdVotosSim(VOTOS_SIM);
        assembleia.setQtdVotosNao(VOTOS_NAO);
        return assembleia;
    }

    private List<Voto> criarListaVotos() {
        var voto = criarVoto();
        var listaVotos = new ArrayList<Voto>();
        listaVotos.add(voto);
        return listaVotos;
    }

    private Voto criarVoto() {
        var voto = new VotoImpl();
        voto.setIdVoto(ID_VOTO);
        voto.setVotoEnum(VOTO_ENUM);
        voto.setIdAssociado(ID_ASSOCIADO);
        return voto;
    }

    private Pauta criarPauta() {
        var pauta = new PautaImpl();
        pauta.setIdPauta(ID_PAUTA);
        pauta.setNome(NOME_PAUTA);
        return pauta;
    }

    private ObterResultadoAssembleiaUseCaseInput criarAssembleiaInput() {
        return new ObterResultadoAssembleiaUseCaseInput(ID_ASSEMBLEIA, MOMENTO_REQUEST);
    }
}