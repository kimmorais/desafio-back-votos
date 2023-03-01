# <h1 align="center"> desafio-back-votos </h1>
### Sistema de votação

Esta é uma API Rest de um sistema de votação em assembleias sobre determinadas pautas.<br/>
A documentação dos endpoints da API pode ser encontrada neste [link](http://desafiobackvotos-env-2.eba-tsvwepdi.us-east-1.elasticbeanstalk.com/swagger-ui/index.html#/).<br/>
Esta API foi desenvolvida utilizando Java 17 e o framework Spring Boot.<br/>
Os testes unitários foram feitos utilizando JUnit 5 e Mockito.<br/>
A API está hosteada na AWS utilizando o AWS Elastic Beanstalk, AWS RDS para o banco de dados PostgreSQL e o AWS SQS para o serviço de mensageria.

### Comandos

Você pode:

* Cadastrar uma nova pauta.
* Cadastrar um novo associado com base no seu CPF.
* Iniciar uma assembleia para uma pauta. Podem existir várias assembleias para uma mesma pauta.
* Votar em uma assembleia. Para isso, será necessário passar o CPF do associado e o seu voto (SIM/NAO).
* Obter o resultado de uma determinada assembleia.

### Como a API funciona

* Ao cadastrar uma nova pauta, será verificado se já existe uma pauta com o nome informado, antes do cadastro ser realizado.<br/>
* Ao iniciar uma nova assembleia para uma pauta, é validado se o tempo informado para a assembleia acabar é em um momento futuro, pois não é possível iniciar uma assembleia com um final no passado ou no presente momento. Também é validada se a pauta para a qual esta assembleia está sendo cadastrada realmente existe.<br/>
* Ao votar, é verificado se o associado já está cadastrado, se a assembleia passada para votar realmente existe, e também se é possível votar nessa assembleia, com base no tempo final da assembleia, horario do voto,
  e se o associado já votou nesta assembleia, pois não é possível que um associado vote mais de uma vez em uma mesma assembleia.<br/>
* Ao obter o resultado de uma assembleia, é verificado se a assembleia informada realmente existe e também se ela já foi finalizada, pois não é possível obter o resultado de uma assembleia que ainda está em andamento.<br/>
* A cada 30 minutos é executado um Cron para verificar se há alguma assembleia finalizada e enviar uma mensagem para a fila SQS através do Publisher, que pode ser consumida pelo Consumer e disponibilizada para o restante da aplicação.

### Executando a solução

Para executar localmente:
1. Verifique se está com o ambiente configurado para o Maven e Java 17;
2. Clone o repositório na sua máquina;
3. Navegue até o diretório **back_votos_core** e execute o comando mvn clean, mvn install. Faça o mesmo no diretório **back_votos_plugins**;
4. No diretório **back_votos_plugins** avegue até a pasta _/target/_ e execute o comando **java -jar back_votos_plugins-1.0-SNAPSHOT.jar**.

Após realizar estes passos, a aplicação estará rodando e será possível testá-la localmente utilizando sua plataforma de envio de requisição para API desejada.

Para testar a API na nuvem, siga os passos:
1. Abra sua plataforma de envio de requisição para API desejada; (Postman, Insomnia etc...)
2. Crie uma nova requisição com um dos métodos disponíveis no link mencionado acima, na url **desafiobackvotos-env-2.eba-tsvwepdi.us-east-1.elasticbeanstalk.com**, com o path adequado;
3. No mesmo link é possível ver os parâmetros para serem passados no body ou path;
4. Ao ser feita a requisição, será retornada a response adequada.

### Considerações

A AWS fornece uma gama enorme de serviços para serem utilizados nos mais diferentes cenários. Tendo em vista o nível de complexidade da API, optei pelo uso do AWS Elastic Beanstalk para hostear a API, por ser mais direto, prático e rápido para subir. Pelo lado do banco de dados, com os relacionamentos entre as entidades,
um banco PostgreSQL no AWS RDS foi de bom uso.<br/> Para "fechar a conta" no uso da AWS, o SQS foi usado para implementar a parde de mensageria, com as mensagens sendo enviadas pelo Publisher e consumidas pelo Consumer, para que ficassem disponíveis para o restante da aplicação.<br/>
Para a mensageria ser implementada, da maneira que foi pedida, acreditei que faria sentido criar uma tarefa que seria executada periodicamente, executando algumas verificações, criando e enviando a mensagem para a fila SQS. Para isso fiz uso do Scheduler, do Spring, juntamente com uma expressão Cron para indicar a
periodicidade da execução.
