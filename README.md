# Backend Sistema de Bolsas (API)

API REST desenvolvida em **Spring Boot 3**, projetada para o gerenciamento de **bolsas e incentivos institucionais** vinculados a pessoas e projetos.  
O sistema permite cadastrar **pessoas**, **projetos**, e **participações** (relação entre ambos), além de controlar dados bancários, endereços e perfis de acesso.

O projeto segue boas práticas de **arquitetura em camadas**, **segurança via API Key**, e suporte completo para **execução em ambiente Docker** com banco **PostgreSQL**.

---

## 🧠 Visão Geral da Arquitetura

```
bolsas-api/
├── src/
│   ├── main/java/org/isaci/bolsas_api/
│   │   ├── controller/       # Camada de controladores REST
│   │   ├── service/          # Camada de serviços (regras de negócio)
│   │   ├── repository/       # Interfaces JPA
│   │   ├── model/            # Entidades JPA
│   │   ├── dtos/             # Objetos de transferência de dados
│   │   ├── enums/            # Enumerações do domínio
│   │   ├── config/           # Configurações de segurança e beans
│   │   └── security/         # Implementação de filtro de API Key
│   └── resources/
│       ├── application.yml           # Configuração base
│       ├── application-dev.yml       # Ambiente local
│       ├── application-prod.yml      # Ambiente Docker
│       └── static/                   # (se houver assets)
├── Dockerfile
├── docker-compose.yml
├── .env
└── pom.xml
```

---

## 🚀 Tecnologias Utilizadas

| Categoria | Tecnologia |
|------------|-------------|
| Linguagem | Java 17 |
| Framework | Spring Boot 3 (Web, Data JPA, Validation, Actuator, Security) |
| Banco de Dados | PostgreSQL 15 |
| Segurança | API Key customizada (`X-API-KEY`) |
| Build | Maven |
| Containerização | Docker & Docker Compose |
| ORM | Hibernate |
| Validação | Jakarta Validation (Bean Validation) |

---

## 📦 Requisitos

Antes de começar, instale:

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)
- (opcional) [IntelliJ IDEA](https://www.jetbrains.com/idea/) ou outro IDE Java

---

## 🧰 Clonagem do Projeto

```bash
git clone https://github.com/hkuribayashi/bolsas-api.git
cd bolsas-api
```

---

## ⚙️ Configuração de Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto (se ainda não existir):

```env
# 🔐 Chave de segurança da API
SECURITY_API_KEY=minha-chave-secreta-123

# 💾 Banco de dados
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=isaciBolsasAPI
```

> ⚠️ Este arquivo **não deve ser versionado** — adicione-o ao `.gitignore`.

---

## 🐳 Execução via Docker

A maneira mais simples de executar a aplicação e o banco é com Docker Compose:

```bash
docker-compose up --build
```

Isso irá:

1. Construir o jar da aplicação via Maven (etapa de build multi-stage)
2. Subir o container da API (`bolsas-api`) na porta **8080**
3. Subir o container do PostgreSQL (`postgres`) na porta **5432**

Após o build, o sistema estará disponível em:

👉 **http://localhost:8080**

---

## 🔐 Testando a API com Segurança (X-API-KEY)

Todas as rotas **/admin/** exigem autenticação via **API Key** definida no `.env`.

### ✅ Exemplo de requisição (GET):

```bash
curl -X GET http://localhost:8080/admin/projects   -H "X-API-KEY: minha-chave-secreta-123"
```

### ❌ Exemplo sem chave:

```bash
curl -X GET http://localhost:8080/admin/projects
```

**Resposta esperada:**
```json
{
  "error": "Unauthorized",
  "message": "Missing or invalid API key"
}
```

---

## 💻 Execução Local (sem Docker)

Se quiser rodar apenas a aplicação localmente (por exemplo, via IntelliJ):

1. Crie um banco local PostgreSQL:
   ```bash
   createdb isaciBolsasAPI
   ```
2. Defina o perfil ativo `dev`:
    - No IntelliJ → **Run → Edit Configurations → VM Options**:
      ```
      -Dspring.profiles.active=dev
      ```
3. Execute:
   ```bash
   ./mvnw spring-boot:run
   ```

A aplicação usará o **application-dev.yml**, conectando-se ao PostgreSQL local (`localhost`).

---

## 🧩 Perfis de Aplicação

| Perfil | Arquivo | Banco | Uso |
|--------|----------|--------|-----|
| `dev` | `application-dev.yml` | `localhost` | Execução local no IntelliJ |
| `prod` | `application-prod.yml` | `postgres` (container Docker) | Execução via Docker Compose |

---

## 🧱 Estrutura de Segurança

A autenticação é feita via **filtro customizado** que valida a presença de uma chave API (`X-API-KEY`) no cabeçalho da requisição.

- Configuração da chave: `.env → SECURITY_API_KEY`
- Implementação: `ApiKeyAuthFilter.java`
- Configuração: `SecurityConfig.java`

---

## 🧪 Exemplos de Endpoints

| Método | Endpoint | Descrição |
|---------|-----------|-----------|
| `POST` | `/admin/projects` | Cria um novo projeto |
| `GET` | `/admin/projects` | Lista todos os projetos |
| `GET` | `/admin/projects/{id}` | Retorna um projeto pelo ID |
| `PUT` | `/admin/projects/{id}` | Atualiza um projeto |
| `DELETE` | `/admin/projects/{id}` | Remove um projeto |

> Todos exigem cabeçalho `X-API-KEY`.

---

## 🧹 Limpeza e reconstrução dos containers

```bash
docker-compose down -v
docker-compose up --build
```

---

> 💡 *Dica:* para ver os logs da aplicação em tempo real:
> ```bash
> docker logs -f bolsas-api
> ```