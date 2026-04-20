Order System API

API REST desenvolvida para praticar conceitos de backend utilizados em aplicações reais.
A ideia foi criar uma API simples de pedidos, mas usando algumas ferramentas que são comuns em sistemas maiores.

Tecnologias

- Java + Spring Boot
- Oracle Database
- Docker
- RabbitMQ

O que a aplicação faz:

- Cria pedidos via API
- Salva no banco de dados
- Envia o pedido para uma fila
- Processa esse pedido de forma assíncrona
- Atualiza o status depois do processamento

Como funciona o fluxo:

1. O pedido chega pela API
2. É salvo no banco
3. Um evento é enviado para o RabbitMQ
4. Um consumer pega esse evento
5. O pedido é processado e atualizado

O que aprendi:

- Integração com banco de dados Oracle
- Uso de Docker para subir serviços
- Comunicação assíncrona com RabbitMQ
- Estruturação de API com Controller, Service e Repository


⚙️ Como rodar

1. Subir containers no Docker

```bash
docker-compose up -d
```
2. Rode a aplicação com:
```bash
mvn spring-boot:run
```
(ou pela IDE)

4. A API estará disponível na porta:
```
http://localhost:8084
