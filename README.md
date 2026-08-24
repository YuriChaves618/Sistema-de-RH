# Projeto Final — Sistema de Funcionários e Dependentes 

> **Aplicação desktop desenvolvida em Java para gerenciamento de funcionários e seus respectivos dependentes, utilizando Java Swing e MySQL através de JDBC** 

## Funcionalidades

 * **Cadastro de funcionários**
 * **Edição de funcionários**
 * **Exclusão de funcionários**
 * **Listagem de funcionários**
 * **Cadastro de dependentes**
 * **Edição de dependentes**
 * **Exclusão de dependentes**
 * **Listagem de dependentes**
 * **Persistência dos dados em MySQL**
 * **Tecnologias**
 
## Tecnologias	
* **Java**	Linguagem principal
* **Java Swing**	Interface gráfica
* **MySQL**	Banco de dados
* **JDBC**	Comunicação com o banco
* **MySQL** Connector/J	Driver JDBC

## Executando o Projeto

###  1. Pré-requisitos

* **JDK 25 ou compatível**
* **Apache NetBeans**
*  **MySQL Server**
* **MySQL Workbench ou outro cliente MySQL**
*  **MySQL Connector/J**

  
### 2. Configuração do banco de dados

Crie o banco:

CREATE DATABASE projeto_final;
USE projeto_final;

Crie a tabela de funcionários:

CREATE TABLE Funcionario (
    cpf VARCHAR(14) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    salario DECIMAL(10,2) NOT NULL,
    cargo VARCHAR(100) NOT NULL
);

Crie a tabela de dependentes:

CREATE TABLE Dependente (
    cpf_funcionario VARCHAR(14) NOT NULL,
    cpf VARCHAR(14) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    FOREIGN KEY (cpf_funcionario) REFERENCES Funcionario(cpf)
);
🔌 Conexão

A conexão está centralizada em:

src/projetofinal/ConexaoBanco.java

O projeto utiliza uma conexão JDBC com o banco projeto_final.

Antes de executar, confira o usuário, senha e porta do MySQL no arquivo ConexaoBanco.java.

 Não coloque senhas reais diretamente em um repositório público.


