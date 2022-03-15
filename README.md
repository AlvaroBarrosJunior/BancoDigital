# Banco Digital

## Descrição:
O sistema Banco Digital é uma API REST desenvolvida com o objetivo de aprimorar conhecimentos em Spring Boot, Testes Unitários, TDD e Swagger. Se propõe a ser um sistema bancário básico onde se é possível cadastrar bancos, agencias, clientes e contas, assim como realizar operações básicas em contas.

## Tecnologias:

<div display=flex>
  <img src="https://user-images.githubusercontent.com/8556553/158421720-d21cfd64-1e9c-4428-a0ce-1c9d773d3b11.png" width="250" height="250">
  <img src="https://user-images.githubusercontent.com/8556553/158421546-f89f5ca3-c0b5-48c4-98ac-61d54f5b3604.png" width="250" height="250">
  <img src="https://user-images.githubusercontent.com/8556553/158424150-7e1e8524-c377-492d-80df-da2678dd11d4.png" height="250">
  <img src="https://user-images.githubusercontent.com/8556553/158424467-e4117052-b040-46d0-bcf3-6296637c2dd0.png" width="250" height="250">
</div>

Para desenvolver o sistema do Banco Digital foram utilizadas as técnologias: Spring Boot, JUnit, PostgreSql e Swagger.
  
## Funcionalidades:
As funcionalidades foram desenvolvidas levando em consideração que o sistema seria apenas um MVP, uma vez que o objetivo era o aprimoramento e aprendizado das ferramentas e tecnologias utilizadas, também é interessante frizar que o sistema foi deployado no heroku, então caso queira fique a vontade para testar e dar o seu feedback.

Para comunicar com os endpoints utiliza a url https://api-rest-banco-digital.herokuapp.com.

É possível acessar a documentação e testar as funcionalidades do sistema acessando o swagger-ui pelo link https://api-rest-banco-digital.herokuapp.com/swagger-ui.html

### Banco:
- Buscar bancos: esta funcionalidade retorna todos os bancos que estão cadastrados no sistema, endpoint: [GET] /banco .
- Cadastrar banco: esta funcionalidade permite ao usuario cadastrar um novo banco no sistema, não é possível adicionar um banco com mesmo nome de outro banco já cadastrado, endpoint: [POST] /banco/novo .

### Agência:
- Buscar agências: essa funcionalidade retorna todas as agências que estão cadastradas no sistema, endpoint: [GET] /agencia .
- Buscar agẽncias do banco: essa funcionalidade retorna todas as agências de um banco informado que estão cadastradas no sistema, endpoint: [GET] /agencia/{idBanco} .
- Cadastrar agência: essa funcionalidade permite ao usuario cadastrar uma nova agencia no sistema, não é possível adicionar uma agência com mesmo nome de outra agencia cadastrada para o mesmo banco informado, é permitido adicionar duas agências com mesmo nome desde que o banco seja distinto, endpoint: [POST] /agencia/novo .

### Cliente:
- Buscar clientes: essa funcionalidade retorna todos os clientes cadastrados no sistema, no entanto retornará apenas os nomes dos clientes, endpoint: [GET] /cliente .
- Cadastar cliente: essa funcionalidade permite cadastrar um novo cliente ao sistema, endpoint: [POST] /cliente/novo .
- Login: essa funcionalidade retorna um token que será necessário para as funcionalides de Conta e Detalhes do Cliente, endpoint: [POST] /cliente/login .
- Detalhes do cliente: essa funcionalidade retorna os detalhes do cliente logado, para isso é necessário informar o token gerado no login, endpoint: [GET] /cliente/logado .

### Conta:
- Buscar contas: essa funcionalidade retorna todas as contas que tem como titular o usuario logado e que estão cadastradas no sistema, endpoint: [GET] /conta .
- Buscar conta: essa funcionalidade retorna uma conta específica se ela tiver como titular o usuario logado e que estão cadastradas no sistema, endpoint: [GET] /conta .
- Cadastrar conta: essa funcionalidade permite cadastrar uma nova conta ao sistema, o tipo da conta pode ser Conta Corrente: 1 ou Conta Poupança: 2, para isso o cliente deve estar logado, endpoint: [POST] /conta/novo .
- Depósito: essa funcionalidade permite que o usuário realize um depósito em uma conta em que é titular, endpoint: [PUT] /conta/deposito .
- Saque: essa funcionalidade permite que o usuário realize um saque em uma conta em que é titular, sendo necessário ter saldo superior ou igual ao valor pretendido do saque, endpoint: [PUT] /conta/saque .
- Transferência: essa funcionalidade permite que o usuário realize uma transferência de uma conta em que é titular para qualquer conta cadastrada no sistema, sendo necessário ter saldo superior ou igual ao valor pretendido da transferência, endpoint: [PUT] /conta/transferencia .

