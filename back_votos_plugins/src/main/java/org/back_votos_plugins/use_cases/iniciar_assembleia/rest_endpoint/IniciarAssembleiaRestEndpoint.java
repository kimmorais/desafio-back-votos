package org.back_votos_plugins.use_cases.iniciar_assembleia.rest_endpoint;

import lombok.RequiredArgsConstructor;
import org.back_votos_core.entities.Assembleia;
import org.back_votos_core.entities.Pauta;
import org.back_votos_core.use_cases.iniciar_assembleia.impl.ports.IniciarAssembleiaPort;
import org.back_votos_core.use_cases.iniciar_assembleia.input.IniciarAssembleiaUseCaseInput;
import org.back_votos_plugins.factories.EntityFactories;
import org.back_votos_plugins.use_cases.iniciar_assembleia.rest_endpoint.request_model.IniciarAssembleiaRequestModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
public class IniciarAssembleiaRestEndpoint {

    private final Clock clock;
    private final EntityFactories entityFactories;
    private final IniciarAssembleiaPort iniciarAssembleiaPort;

    @PostMapping("/assembleia")
    public ResponseEntity<Assembleia> iniciarAssembleia(@RequestHeader(required = false) String tempoAssembleia,
                                                        @RequestBody IniciarAssembleiaRequestModel assembleiaRequest) {
        var assembleiaInput = criarAssembleiaInput(tempoAssembleia, assembleiaRequest.getNomePauta());

        return ResponseEntity.ok(this.iniciarAssembleiaPort.iniciarAssembleia(assembleiaInput));
    }

    private IniciarAssembleiaUseCaseInput criarAssembleiaInput(String tempoAssembleia, String pauta) {
        return new IniciarAssembleiaUseCaseInput(criarPauta(pauta), validarTempoAssembleia(tempoAssembleia));
    }

    private Pauta criarPauta(String nomePauta) {
        var pauta = this.entityFactories.pautaFactory().makeNewInstance();
        pauta.setNome(nomePauta);
        return pauta;
    }

    private LocalDateTime validarTempoAssembleia(String tempoAssembleia) {
        return tempoAssembleia == null ?
                LocalDateTime.now(this.clock).plusMinutes(1) :
                LocalDateTime.parse(tempoAssembleia, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
