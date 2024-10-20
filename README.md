# Authorizer Caju
[![en](https://img.shields.io/badge/lang-en-red.svg)](README.en.md)

# Requirements
* Linux
* Make
* JDK >= 17
* Kotlin
* Gradle >= 8.5
* Docker >= 27.2.1
* Docker Compose >= 1.29.2

# Bootstrap

## Run

```bash
# run db and application
$ make up  
```

## Migration

```bash
# shutdown db and application
$ make migration
```

## Testing : L1, L2 and L3

```bash

# Request FOOD
curl --location --request POST 'localhost:8080/api/v1/transactions/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "account": "123",
    "totalAmount": 10,
    "merchant": "gemini systems",
    "mcc": "5411"
}'

# Request MEAL
curl --location --request POST 'localhost:8080/api/v1/transactions/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "account": "123",
    "totalAmount": 10,
    "merchant": "gemini systems",
    "mcc": "5811"
}'

# Request CASH
curl --location --request POST 'localhost:8080/api/v1/transactions/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "account": "123",
    "totalAmount": 10,
    "merchant": "gemini systems",
    "mcc": "581"
}'

# Request return code 51 (REJECTED)
curl --location --request POST 'localhost:8080/api/v1/transactions/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "account": "123",
    "totalAmount": 1000,
    "merchant": "gemini systems",
    "mcc": "5411"
}'

# Request return code 07 (ANY OTHER ERROR)
curl --location --request POST 'localhost:8080/api/v1/transactions/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "account": "123",
    "totalAmount": 10,
    "merchant": "systems",
    "mcc": ""
}'


```

## Shut down

```bash
# shutdown db and application 
$ make down
```


## L4. Questão aberta

PROBLEMA :

### Duas transações utilizando o mesmo de cartão de crédito chegam ao mesmo tempo ####

SOLVE :

1 - O Micro serviço receptor da transação recebe essas transações com
número de cartões iguais e as registra no cache único(REDIS) no formato
CARTÃO, TIMESTAMP e FLAG "READY_TO_PROCESS". Para fazer isso, a cada transação recebida,
ele irá consultar o CACHE ÚNICO. Caso uma entrada com esse número de cartão já exista,
a nova entrada NO CACHE, com esse mesmo número de cartão, será o TIMESTAMP da transação 
encontrada no CACHE anterior, cujo o qual, não esteja com o flag PROCESSED, acrescido de 1.

Obs.1: Todas as entradas/transações recebidas, também devem gravar em um tópico KAFKA que será
lido por um outro Micro serviço de forma assincrona(modelo PUB/SUB) e que deverá registrar
essas transações em uma base de dados física para uso em procedimentos de OBSERVABILIDADE
ou qualquer outras analises.

Obs.2: As entradas no CACHE são configuradas sempre com um TIMEOUT em miliseconds para seu processamento
e descarte. Caso algo saia errado e a mesma não consiga ser processada, esse registro sairá do CACHE ÚNICO.

2 - Essas transações gravadas no CACHE ÚNICO são consumidas/processadas em outro método no mesmo Micro serviço
no formato FIFO(Fisrt IN First OUT) dentro do fluxo de chegada da transação e
sinalização de existência de novas transação a serem processadas.

Obs.1:  As mesmas, quando descem do CACHE ÚNICO, são ordenadas com base  no TIMESTAMP(value do cache)

Obs.2: Só devem ser processadas as transações que estiverem com flag "READY_TO_PROCESS".

3 - Após o processamento dessa transação, a mesma recebe um update(flag "PROCESSED" colocado no value)
no seu registro no CACHE.

4 - Em um horário programado específico, após expediente normal, esse cache do dia será FLUSHED

