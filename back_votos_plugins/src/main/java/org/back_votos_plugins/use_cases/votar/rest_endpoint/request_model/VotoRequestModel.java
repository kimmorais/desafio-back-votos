package org.back_votos_plugins.use_cases.votar.rest_endpoint.request_model;

import lombok.Getter;
import lombok.Setter;
import org.back_votos_core.entities.constants.VotoEnum;

import java.util.UUID;

@Getter
@Setter
public class VotoRequestModel {

    private UUID idAssociado;
    private VotoEnum voto;
}
