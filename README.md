## :rocket: Tecnologias utilizadas
* Java 17
* Spring Boot
* JOSE+JWT

## Geracao do certificado
1. Gerar chave privada
```
openssl genrsa -out app.key 2048
```
2. Gere chave publica
```
openssl rsa -in app.key -pubout -out app.pub
```

## Caminho do novo certificado
Para executar com novo certificado gerado necessario colocar na pasta resources do projeto

## Links Referencias
https://www.youtube.com/watch?v=nDst-CRKt_k&t=667s
https://cryptotools.net/rsagen
https://github.com/buildrun-tech/buildrun-spring-security-jwt-example