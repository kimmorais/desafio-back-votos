package org.back_votos_plugins.use_cases.cadastrar_pauta.use_case_provider.port_adapter;

import org.back_votos_core.entities.Pauta;
import org.back_votos_core.entities.impl.PautaImpl;
import org.back_votos_core.use_cases.cadastrar_pauta.input.CadastrarPautaUseCaseInput;
import org.back_votos_plugins.dao.repositories.PautaRepository;
import org.back_votos_plugins.dao.tables.PautaTable;
import org.back_votos_plugins.dao.tables.mappers.PautaTableMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastrarPautaPortAdapterTest {

    static final UUID ID_PAUTA = UUID.fromString("2e6e74ef-5c6b-4e89-a3cd-f53132dee552");
    static final String NOME_PAUTA = "Pauta teste";

    @Mock
    PautaTableMapper pautaTableMapper;

    @Mock
    PautaRepository pautaRepository;

    @InjectMocks
    CadastrarPautaPortAdapter adapter;

    @Test
    @DisplayName("Ao cadastrar uma pauta, deve fazer os mappings necessários, salvar e retornar a Pauta")
    void cadastrarPauta_pautaInputValida_retornarPauta() {

        var pautaEsperada = criarPautaEsperada();
        var pautaInput = criarPautaInput();
        var pautaTable = criarPautaTable(null);
        var pautaTableComId = criarPautaTable(ID_PAUTA);

        when(this.pautaTableMapper.converterPautaInputParaPautaTable(pautaInput)).thenReturn(pautaTable);
        when(this.pautaRepository.save(pautaTable)).thenReturn(pautaTableComId);
        when(this.pautaTableMapper.converterPautaTableParaPauta(pautaTableComId)).thenReturn(pautaEsperada);

        var retorno = this.adapter.cadastrarPauta(pautaInput);

        assertEquals(pautaEsperada, retorno);
    }

    private CadastrarPautaUseCaseInput criarPautaInput() {
        return new CadastrarPautaUseCaseInput(NOME_PAUTA);
    }

    private PautaTable criarPautaTable(UUID idPauta) {
        var pautaTable = new PautaTable();
        pautaTable.setId(idPauta);
        pautaTable.setNome(NOME_PAUTA);

        return pautaTable;
    }

    private Pauta criarPautaEsperada() {
        var pauta = new PautaImpl();
        pauta.setIdPauta(ID_PAUTA);
        pauta.setNome(NOME_PAUTA);

        return pauta;
    }
}