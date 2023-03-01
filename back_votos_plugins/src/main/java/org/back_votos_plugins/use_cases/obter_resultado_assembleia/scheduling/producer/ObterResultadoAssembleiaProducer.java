package org.back_votos_plugins.use_cases.obter_resultado_assembleia.scheduling.producer;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import lombok.RequiredArgsConstructor;
import org.back_votos_plugins.dao.repositories.AssembleiaRepository;
import org.back_votos_plugins.dao.tables.AssembleiaTable;
import org.back_votos_plugins.use_cases.obter_resultado_assembleia.scheduling.credenciais.CredenciaisAws;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ObterResultadoAssembleiaProducer {

    private static final String MENSAGEM_ASSEMBLEIA_FINALIZADA = "Assembleia ID %s e Pauta %s finalizada. Resultado: %s";

    private final Clock clock;
    private final CredenciaisAws credenciaisAws;
    private final AssembleiaRepository assembleiaRepository;

    @Scheduled(cron = "0 */30 * ? * *")
    public void run() {
        var assembleias = obterAssembleias();
        var horaAtual = LocalDateTime.now(this.clock);

        var assembleiasFinalizadas = assembleias.stream()
                .filter(assembleiaTable -> verificarSeAssembleiaEstaFinalizada(assembleiaTable, horaAtual))
                .filter(this::verificarResultadoNaoProcessado)
                .map(this::verificarAssembleiaVencedora)
                .toList();

        var credenciais = obterAwsCredentials();
        var sqs = sqsClientBuilder(credenciais);
        var fila = criarFila(sqs);
        var mensagens = new ArrayList<SendMessageBatchRequestEntry>();

        assembleiasFinalizadas.forEach(assembleia -> {
            var resultado = Boolean.TRUE.equals(assembleia.getVencedora()) ? "VITORIA" : "DERROTA";
            mensagens.add(new SendMessageBatchRequestEntry()
                    .withId(UUID.randomUUID().toString())
                    .withMessageBody(String.format(MENSAGEM_ASSEMBLEIA_FINALIZADA, assembleia.getId(), assembleia.getPauta().getNome(), resultado))
                    .withMessageGroupId("group-id-1"));
        });

        if (!mensagens.isEmpty()) {
            var requestMessageBatch = new SendMessageBatchRequest(fila, mensagens);
            sqs.sendMessageBatch(requestMessageBatch);
        }
    }

    private boolean verificarResultadoNaoProcessado(AssembleiaTable assembleiaTable) {
        return assembleiaTable.getVencedora() == null;
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

    private boolean verificarSeAssembleiaEstaFinalizada(AssembleiaTable assembleiaTable, LocalDateTime horaAtual) {
        return assembleiaTable.getFimAssembleia().isBefore(horaAtual) ||
                assembleiaTable.getFimAssembleia().isEqual(horaAtual);
    }

    private AssembleiaTable verificarAssembleiaVencedora(AssembleiaTable assembleiaTable) {
        var qtdVotosSim = assembleiaTable.getQtdVotosSim();
        var qtdVotosNao = assembleiaTable.getQtdVotosNao();
        assembleiaTable.setVencedora(qtdVotosSim > qtdVotosNao);

        return this.assembleiaRepository.save(assembleiaTable);
    }

    private List<AssembleiaTable> obterAssembleias() {
        return this.assembleiaRepository.findAll();
    }
}
