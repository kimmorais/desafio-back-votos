package org.back_votos_plugins.use_cases.cadastrar_associado.use_case_provider.port_adapter;

import org.back_votos_core.entities.Associado;
import org.back_votos_core.entities.impl.AssociadoImpl;
import org.back_votos_core.use_cases.cadastrar_associado.input.CadastrarAssociadoUseCaseInput;
import org.back_votos_plugins.dao.repositories.AssociadoRepository;
import org.back_votos_plugins.dao.tables.AssociadoTable;
import org.back_votos_plugins.dao.tables.mappers.AssociadoTableMapper;
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
class CadastrarAssociadoPortAdapterTest {

    static final UUID ID_ASSOCIADO = UUID.fromString("2e6e74ef-5c6b-4e89-a3cd-f53132dee552");
    static final String CPF_ASSOCIADO = "29375623801";

    @Mock
    AssociadoTableMapper associadoTableMapper;

    @Mock
    AssociadoRepository associadoRepository;

    @InjectMocks
    CadastrarAssociadoPortAdapter adapter;

    @Test
    @DisplayName("Ao cadastrar um associado, deve fazer os mappings necessários, salvar e retornar o Associado")
    void cadastrarAssociado_associadoInputValido_retornarAssociado() {

        var associadoEsperado = criarAssociadoEsperado();
        var associadoInput = criarAssociadoInput();
        var associadoTable = criarAssociadoTable(null);
        var associadoTableComId = criarAssociadoTable(ID_ASSOCIADO);

        when(this.associadoTableMapper.converterAssociadoInputParaAssociadoTable(associadoInput)).thenReturn(associadoTable);
        when(this.associadoRepository.save(associadoTable)).thenReturn(associadoTableComId);
        when(this.associadoTableMapper.converterAssociadoTableParaAssociado(associadoTableComId)).thenReturn(associadoEsperado);

        var retorno = this.adapter.cadastrarAssociado(associadoInput);

        assertEquals(associadoEsperado, retorno);
    }

    private Associado criarAssociadoEsperado() {

        var associado = new AssociadoImpl();
        associado.setIdAssociado(ID_ASSOCIADO);
        associado.setCpf(CPF_ASSOCIADO);

        return associado;
    }

    private CadastrarAssociadoUseCaseInput criarAssociadoInput() {
        return new CadastrarAssociadoUseCaseInput(CPF_ASSOCIADO);
    }

    private AssociadoTable criarAssociadoTable(UUID idAssociado) {

        var associadoTable = new AssociadoTable();
        associadoTable.setId(idAssociado);
        associadoTable.setCpf(CPF_ASSOCIADO);

        return associadoTable;
    }
}