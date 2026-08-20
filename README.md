# Sistema de controle Financeiro

## Tecnologias

Esse projeto é um projeto Spring Boot Maven para a versão >= 21 do Java Runtime Environment, ele só funcionará com as dependências declaradas no pom.arquivo xml

This project is a Spring Boot Maven project for the Java Runtime Environment version >= 21, it will only work with de dependencies as declared in pom.xml.

Isso é para ser utilizado em combinação com um frontend, localizado aqui:

This is used in combination with a frontend, which is localized here:

https://github.com/Felipe-Victorino/controle-financeiro-front

## Dependências/Dependencies

* Maven
* Java Runtime Environment >= 21
* MariaDB

## Inicializar

Para incializar esse projeto, digite no seu terminal de comandos de preferência esse comando:

To run this project, make sure you're at the porject root and type this command on your terminal of choice:

```
./mvnw clean install
```

Após a instalação das dependências, diigte esse comando para iniciar o servidor na porta 8080.

After the installation of the dependencies, please run this command to start the server on port 8080

```
./mvnw spring-boot:run
```

## SwaggerUI

A página OpenAPI/Swagger está localizada em:

The OpenAPI/Swagger page is localized in:

```
localhost:8080/api/v3/swagget-ui.html
```