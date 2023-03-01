package org.back_votos_plugins.use_cases.obter_resultado_assembleia.scheduling.credenciais;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class CredenciaisAws {

    @Value("${aws.credentials.access-key}")
    private String accessKey;

    @Value("${aws.credentials.secret-key}")
    private String secretKey;

    @Value("${aws.queue.name}")
    private String queueName;
}
