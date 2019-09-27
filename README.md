# Sumario

REST Web API para compra e venda de ações.

## Requisitos

[`Spring-Boot - v2.1.8`](https://spring.io/projects/spring-boot)

[`Maven - v3.6.2`](https://maven.apache.org/download.cgi)

[`Java SDK - v12` ](https://www.oracle.com/technetwork/java/javase/downloads/jdk12-downloads-5295953.html)

[`PostgreSQL - v11.5`](https://www.postgresql.org/download/)

[`Git - v2.23.0`](https://git-scm.com/downloads)
______

## Configuração
Para que a aplicação funcione é necessário que o servidor `PostgreSQL` esteja configurado para a porta `5432`. 

Para a inicializar:

1. Crie um `Database` com o nome `corretor_inteligente`:
1. Clone o repositório:
    ```git
    git clone https://github.com/victoralvesp/CorretorInteligente.git
    ```
1. Navegue até o diretório do arquivo e monte o pacote:

    ```
    mvn package 
    ```
1. Execute a aplicação:
   ```java
   java -jar ./target/CorretorInteligente-0.0.1-SNAPSHOT.jar
   ```

Por padrão a aplicação espera requisições na porta **8080**. 

## Operações

Para interagir com o sistema, utilize as seguintes rotas

> Para que alguma negociação seja feita, execute os
> três exemplos a seguir na ordem em que aparecem.

### Contas
1. Inclusão de `Contas Pessoais`

    ```
    POST /contas/
    ```
    > Exemplo
    ```
        curl -X POST \
    http://localhost:8080/contas/ \
    -H 'cache-control: no-cache' \
    -H 'content-type: application/json' \
    -H 'postman-token: 844a630f-5233-2516-f3bc-841dfd4ffced' \
    -d '{
        "email":"corretorinteligente@gmail.com",
        "saldoDisponivel":"10000.00"
    }'
    ```

1. Listagem de `Contas Pessoais`

    ```
    GET /contas/
    ```
### Monitoramentos

1. Inclusão de `Monitoramentos`

    ```
    POST /contas/{idConta}/monitoramentos
    ```
    > Exemplo
    ```
    curl -X POST \
    http://localhost:8080/contas/1/monitoramentos/ \
    -H 'cache-control: no-cache' \
    -H 'content-type: application/json' \
    -H 'postman-token: e34c0cea-b9a2-4a9e-c727-056c1daa8e1f' \
    -d '{
        "empresa":"Intel",
        "precoCompra":"10.00",
        "precoVenda":"11.00"
    }'
    ```

1. Listagem de `Monitoramentos`

    ```
    GET /contas/{idConta}/monitoramentos
    ```
1. Pesquisa de `Monitoramento`

    ```
    GET /contas/{idConta}/monitoramentos/{empresa}
    ```
1. Exclusão de `Monitoramento`
    ```
    DELETE /contas/{idConta}/monitoramentos/{empresa}
    ```
1. Alteração de `Monitoramento`
    ```
    PUT /contas/{idConta}/monitoramentos/{empresa}
    ```


### Observações de Ação
1. Inclusão de `Observação de ação`
    ```
    POST /acoes/
    ```
    > Exemplo
    ```
    curl -X POST \
    http://localhost:8080/acoes/ \
    -H 'cache-control: no-cache' \
    -H 'content-type: application/json' \
    -H 'postman-token: 7716f867-fc67-937e-7a1f-4ec2ba6eddff' \
    -d '{
        "empresa":"Intel",
        "precoCompra":"9.35",
        "precoVenda":"12"
    }'
    ```

### Movimentações de conta
1. Listagem de `Movimentações`

    ```
    GET /contas/{idConta}/monitoramentos
    ```


## Modelagem


O usuário deseja comprar e vender ações sem necessidade de acompanhar o mercado de ações a todo instante. Para isso, define `Monitoramentos` com preços desejados para compra e venda de ações.
Quando a aplicação recebe uma `Acao observada`, que define os valores atuais de compra e venda de **ações de uma Empresa**, ela utiliza estes `Monitoramentos` para criar `Regras de Negociacao` que *movimentam* as `Contas` do usuário. Estas últimas guardam os saldos disponíveis para as negociações.

A `Conta pessoal` determina qual o saldo disponível para compra de ações e indica a posse das ações disponíveis nas `Contas de ação`.  

O modelo segue o seguinte
[Diagrama ER simplificado](https://drive.google.com/file/d/1Imlmnb0r-mvJX-7y6YWXRtGSDPwJV8JC/view?usp=sharing)

## Arquitetura

### Tecnologias utilizadas
O sistema utiliza o pacote `org.springframework.web` para simplificar o recebimento de chamadas HTTP no padrão REST e o pacote `org.springframework.beans` para a injetar dependências diretamente ligadas às configurações de hospedagem (como acesso ao banco de dados, por exemplo). Para a camada de persistência, a aplicação utiliza `org.springframework.data` para auto implementar acessos *CRUD* triviais

### Estruturação do modelo
O sistema utiliza uma camada de serviços para isolar as chamadas aos repositórios e manter as lógicas de domínio minimamente dependetes destes. Entidades de dados são utilizadas para isolar a estrutura de objetos de domínio das responsabilidades da camada de persistência.

