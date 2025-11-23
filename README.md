# Trabalho Coti

Este é o trabalho realizado como Projeto Final
do curso de Arquiteto Java da Coti.

## Iniciando e Parando o Projeto

Este projeto utiliza o Docker.

Para iniciar o projeto, deve-se utilizar o
comando 

```
docker compose up -d --build
```

Para parar o projeto, deve-se utilizar o
comando

```
docker compose down
```

## Containers

Este projeto inicializa sete containers do 
Docker:

1. Container da API `clientes-api`, que 
implementa a parte principal do projeto, a
API de cadastro de clientes.

   - A API deste container pode ser acessada
em 
[http://localhost:8080/api/clientes](http://localhost:8080/api/clientes)
   - A interface web do `Swagger` para esta 
API pode ser acessada em 
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

2. Container do `Postgres`, banco de dados
utilizado pela API para a persistência dos
dados dos clientes.

3. Container do `Adminer`, interface web
para visualização dos dados armazenados no
banco de dados do `Postgres`.

    - A interface do `Adminer` pode ser
acessada em 
[http://localhost:8081/](http://localhost:8081/),
com os dados:
      - Sistema: `PostgreSQL`
      - Servidor: `postgres`
      - Usuário: `postgres`
      - Senha: `postgres`
      - Base de dados: `clientes`

4. Container do componente `mail-sender`, 
parte do projeto que implementa o envio de 
e-mails quando novos clientes são cadastrados.

5. Container do `RabbitMQ`, `message broker`
que realiza a intermediação de mensagens entre
o componente `clientes-api` e o componente
`mail-sender`.

    - A interface web do `RabbitMQ` pode ser
acessada em [http://localhost:15672/](http://localhost:15672/), 
com os dados:
      - Username: `rabbit`
      - Password: `rabbit`

6. Container do `MailHog`, servidor de e-mails
utilizado pelo componente `mail-sender` para
gerenciar o envio dos e-mails

    - A interface web do `MailHog` pode ser
acessada em 
[http://localhost:8025/](http://localhost:8025/)

7. Container do componente `frontend`, parte
do projeto que implementa uma interface web
para acesso à API do componente 
`api-clientes`.

    - O componente `frontend` foi 
desenvolvido com `Angular` e `Bootstrap`
    - O `frontend` pode ser acessado em 
[http://localhost:4200/](http://localhost:4200/)