# 65DSD-Sockets

## Descrição do Projeto

Este projeto é um estudo sobre Sockets desenvolvido para a disciplina de Sistemas Paralelos e Distribuídos (65DSD). O sistema é um gerenciador de equipes e membros que oferece operações CRUD (Criar, Ler, Atualizar e Excluir) tanto para pessoas quanto para equipes. Utilizando sockets, a comunicação entre um servidor e clientes é facilitada, permitindo o gerenciamento dos dados diretamente através do terminal.

## Estrutura do Sistema

### Classes Modelo

- **Pessoa**: Classe base que representa uma pessoa no sistema. Possui atributos como CPF, nome e endereço.
- **Administrador**: Herda de `Pessoa` e representa um administrador. Inclui um atributo adicional para o setor responsável.
- **Membro**: Herda de `Pessoa` e adiciona informações sobre a data e a hora de entrada na equipe.
- **Equipe**: Representa uma equipe com um identificador único, nome, um administrador associado e uma lista de membros.

### Cliente

O cliente é uma aplicação que se conecta ao servidor e permite ao usuário enviar comandos para realizar operações sobre pessoas e equipes. Ele é executado a partir do terminal e se comunica com o servidor através de sockets. As mensagens enviadas pelo cliente são processadas pelo servidor para realizar as operações solicitadas.

### Servidor

O servidor é a aplicação central que gerencia os dados das pessoas e equipes. Ele escuta as conexões dos clientes na porta 12345 e processa os comandos recebidos. O servidor realiza operações como inserção, atualização, listagem e exclusão de pessoas e equipes, além de adicionar e remover membros das equipes.

## Funcionalidades

### Gerenciamento de Pessoas

- **Inserir**: Adiciona novos administradores ou membros.
- **Atualizar**: Modifica os dados de uma pessoa existente.
- **Listar**: Exibe todas as pessoas cadastradas.
- **Excluir**: Remove uma pessoa do sistema.

### Gerenciamento de Equipes

- **Inserir**: Cria novas equipes e associa um administrador a elas.
- **Atualizar**: Modifica os dados de uma equipe existente.
- **Listar**: Exibe todas as equipes cadastradas.
- **Excluir**: Remove uma equipe do sistema.
- **Adicionar Membro**: Adiciona um membro a uma equipe existente.
- **Remover Membro**: Remove um membro de uma equipe existente.

## Como Utilizar

**Atenção!** Este projeto foi desenvolvido e testado pela IDE VSCode, portanto, sugiro que a utilize.

1. **Executar o Servidor**:
   - Entre na classe `Servidor` e clique no botão para executar . O servidor começará a escutar na porta 12345.

2. **Executar o Cliente**:
   - Entre na classe `Cliente` e clique no botão para executar. O cliente se conectará ao servidor e permitirá que você envie comandos através do terminal.

3. **Enviar Mensagens**:
   - Utilize o terminal para enviar mensagens de comando ao servidor via cliente. Os comandos devem seguir o formato especificado para realizar operações como inserir, atualizar, listar e excluir pessoas e equipes.

## Exemplos de Mensagens

- **Inserir Administrador**:
  INSERT;ADMIN;12345678900;João Silva;Rua A, 123;Departamento X

- **Inserir Membro**:
INSERT;MEMBRO;09876543211;Maria Oliveira;Rua B, 456;2024-08-18;08:30:00

- **Inserir Equipe**:
INSERT;EQUIPE;1;Equipe Alpha;12345678900

- **Listar Pessoas**:
LIST

- **Listar Equipes**:
LIST_EQUIPE

- **Atualizar Pessoa**:
UPDATE;12345678900;João da Silva;Rua A, 321
  
- **Excluir Pessoa**:
DELETE;09876543211

- **Atualizar Equipe**:
UPDATE_EQUIPE;1;Equipe Beta;12345678900


- **Adicionar Membro à Equipe**:
INSERT_MEMBRO_EQUIPE;1;09876543211


- **Remover Membro da Equipe**:
DELETE_MEMBRO_EQUIPE;1;09876543211


### Observações

- Certifique-se de que o servidor esteja em execução antes de iniciar o cliente.
- As mensagens devem seguir o formato exato para que o servidor as reconheça e processe corretamente.
- Caso encontre problemas com caracteres especiais, verifique a codificação utilizada no terminal.
# 65DSD-Sockets

## Descrição do Projeto

Este projeto é um estudo sobre Sockets desenvolvido para a disciplina de Sistemas Paralelos e Distribuídos (65DSD). O sistema é um gerenciador de equipes e membros que oferece operações CRUD (Criar, Ler, Atualizar e Excluir) tanto para pessoas quanto para equipes. Utilizando sockets, a comunicação entre um servidor e clientes é facilitada, permitindo o gerenciamento dos dados diretamente através do terminal.

## Estrutura do Sistema

### Classes Modelo

- **Pessoa**: Classe base que representa uma pessoa no sistema. Possui atributos como CPF, nome e endereço.
- **Administrador**: Herda de `Pessoa` e representa um administrador. Inclui um atributo adicional para o setor responsável.
- **Membro**: Herda de `Pessoa` e adiciona informações sobre a data e a hora de entrada na equipe.
- **Equipe**: Representa uma equipe com um identificador único, nome, um administrador associado e uma lista de membros.

### Cliente

O cliente é uma aplicação que se conecta ao servidor e permite ao usuário enviar comandos para realizar operações sobre pessoas e equipes. Ele é executado a partir do terminal e se comunica com o servidor através de sockets. As mensagens enviadas pelo cliente são processadas pelo servidor para realizar as operações solicitadas.

### Servidor

O servidor é a aplicação central que gerencia os dados das pessoas e equipes. Ele escuta as conexões dos clientes na porta 12345 e processa os comandos recebidos. O servidor realiza operações como inserção, atualização, listagem e exclusão de pessoas e equipes, além de adicionar e remover membros das equipes.

## Funcionalidades

### Gerenciamento de Pessoas

- **Inserir**: Adiciona novos administradores ou membros.
- **Atualizar**: Modifica os dados de uma pessoa existente.
- **Listar**: Exibe todas as pessoas cadastradas.
- **Excluir**: Remove uma pessoa do sistema.

### Gerenciamento de Equipes

- **Inserir**: Cria novas equipes e associa um administrador a elas.
- **Atualizar**: Modifica os dados de uma equipe existente.
- **Listar**: Exibe todas as equipes cadastradas.
- **Excluir**: Remove uma equipe do sistema.
- **Adicionar Membro**: Adiciona um membro a uma equipe existente.
- **Remover Membro**: Remove um membro de uma equipe existente.

## Como Utilizar

**Atenção!** Este projeto foi desenvolvido e testado pela IDE VSCode, portanto, sugiro que a utilize.

1. **Executar o Servidor**:
   - Entre na classe `Servidor` e clique no botão para executar . O servidor começará a escutar na porta 12345.

2. **Executar o Cliente**:
   - Entre na classe `Cliente` e clique no botão para executar. O cliente se conectará ao servidor e permitirá que você envie comandos através do terminal.

3. **Enviar Mensagens**:
   - Utilize o terminal para enviar mensagens de comando ao servidor via cliente. Os comandos devem seguir o formato especificado para realizar operações como inserir, atualizar, listar e excluir pessoas e equipes.

## Exemplos de Mensagens

- **Inserir Administrador**:
  INSERT;ADMIN;12345678900;João Silva;Rua A, 123;Departamento X

- **Inserir Membro**:
INSERT;MEMBRO;09876543211;Maria Oliveira;Rua B, 456;2024-08-18;08:30:00

- **Inserir Equipe**:
INSERT;EQUIPE;1;Equipe Alpha;12345678900

- **Listar Pessoas**:
LIST

- **Listar Equipes**:
LIST_EQUIPE

- **Atualizar Pessoa**:
UPDATE;12345678900;João da Silva;Rua A, 321
  
- **Excluir Pessoa**:
DELETE;09876543211

- **Atualizar Equipe**:
UPDATE_EQUIPE;1;Equipe Beta;12345678900


- **Adicionar Membro à Equipe**:
INSERT_MEMBRO_EQUIPE;1;09876543211


- **Remover Membro da Equipe**:
DELETE_MEMBRO_EQUIPE;1;09876543211


### Observações

- Certifique-se de que o servidor esteja em execução antes de iniciar o cliente.
- As mensagens devem seguir o formato exato para que o servidor as reconheça e processe corretamente.
- Caso encontre problemas com caracteres especiais, verifique a codificação utilizada no terminal.
