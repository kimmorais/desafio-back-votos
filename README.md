# <h1 align="center"> desafio-back-votos </h1>
### Sistema de votação

Esta é uma API Rest de um sistema de votação em assembleias sobre determinadas pautas.<br/>
A documentação dos endpoints da API pode ser encontrada neste [link](http://desafiobackvotos-env.eba-tsvwepdi.us-east-1.elasticbeanstalk.com/swagger-ui/index.html#/>).<br/>
Esta API foi desenvolvida utilizando Java 17 e o framework Spring Boot. Os testes unitários foram feitos utilizando JUnit 5 e Mockito.

### Funcionamento

Você pode:
* Cadastrar uma nova pauta.
* Cadastrar um novo associado com base no seu CPF.
* Iniciar uma assembleia para uma pauta. Podem existir várias assembleias para uma mesma pauta.
* Votar em uma assembleia. Para isso, será necessário passar o CPF do associado e o seu voto (SIM/NAO).
* Obter o resultado de uma determinada assembleia.

### Executando a solução

Para executar/testar a API, siga os passos:
* Abra sua plataforma de envio de requisição para API desejada. (Postman, Insomnia etc...)
* Crie uma nova requisição com um dos métodos disponíveis no link mencionado acima, na url desafiobackvotos-env.eba-tsvwepdi.us-east-1.elasticbeanstalk.com, com o path adequado.
* No mesmo link é possível ver os parâmetros para serem passados no body ou path.
* Ao ser feita a requisição, será retornada a response adequada.

Ao cadastrar uma nova pauta, será verificado se já existe uma pauta com o nome informado, antes do cadastro ser realizado.<br/>
Ao iniciar uma nova assembleia para uma pauta, é validado se o tempo informado para a assembleia acabar é em um momento futuro, pois não é possível iniciar uma assembleia com um final no passado ou no presente momento.
Também é validada se a pauta para a qual esta assembleia está sendo cadastrada realmente existe.<br/>
Ao votar, é verificado se o associado já está cadastrado, se a assembleia passada para votar realmente existe, e também se é possível votar nessa assembleia, com base no tempo final da assembleia, horario do voto,
e se o associado já votou nesta assembleia, pois não é possível que um associado vote mais de uma vez em uma mesma assembleia.<br/>
Ao obter o resultado de uma assembleia, é verificado se a assembleia informada realmente existe e também se ela já foi finalizada, pois não é possível obter o resultado de uma assembleia que ainda está em andamento.<br/>
