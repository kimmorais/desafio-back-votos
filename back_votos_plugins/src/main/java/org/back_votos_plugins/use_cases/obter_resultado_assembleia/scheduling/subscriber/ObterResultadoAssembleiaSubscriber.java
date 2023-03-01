package org.back_votos_plugins.use_cases.obter_resultado_assembleia.scheduling.subscriber;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import lombok.RequiredArgsConstructor;
import org.back_votos_plugins.use_cases.obter_resultado_assembleia.scheduling.credenciais.CredenciaisAws;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ObterResultadoAssembleiaSubscriber {

    private final CredenciaisAws credenciaisAws;

    @Scheduled(cron = "0 */30 * ? * *")
    public void run() {
        var credenciais = obterAwsCredentials();
        var sqs = sqsClientBuilder(credenciais);
        var fila = criarFila(sqs);
        var receiveMessageRequest = new ReceiveMessageRequest(fila)
                .withWaitTimeSeconds(10)
                .withMaxNumberOfMessages(10);
        var mensagens = sqs.receiveMessage(receiveMessageRequest).getMessages();
    }

    private String criarFila(AmazonSQS sqs) {

        var atributosFila = new HashMap<String, String>();
        atributosFila.put("FifoQueue", "true");
        atributosFila.put("ContentBasedDeduplication", "true");


        var requestFilaFifo = new CreateQueueRequest(this.credenciaisAws.getQueueName()).withAttributes(atributosFila);
        return sqs.createQueue(requestFilaFifo).getQueueUrl();
    }

    private AWSCredentials obterAwsCredentials() {
        return new BasicAWSCredentials(this.credenciaisAws.getAccessKey(), this.credenciaisAws.getSecretKey());
    }

    private AmazonSQS sqsClientBuilder(AWSCredentials credenciais) {
        return AmazonSQSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credenciais))
                .withRegion(Regions.US_EAST_1)
                .build();
    }
}
