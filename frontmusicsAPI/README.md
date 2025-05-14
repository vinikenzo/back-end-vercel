# 📚 API REST - FrontMusics 🎸

Este projeto foi desenvolvido no contexto da disciplina _Front-End Design Engineering_, pertencente ao curso de Tecnologia em Análise e Desenvolvimento de Sistemas da **FIAP** (Faculdade de Informática e Administração Paulista). O principal objetivo foi aplicar conceitos práticos de desenvolvimento front-end em conjunto com integrações de back-end e banco de dados.

Cabe destacar que os módulos referentes à API (desenvolvida em Java) e ao banco de dados (Oracle) ainda podem ser otimizados. Essas áreas, apesar de funcionais, não constituem meu principal campo de especialização, razão pela qual algumas implementações podem carecer de refinamento técnico.

Aqui está uma visão geral das funcionalidades da API.

---

&nbsp;

## 🧩 Tecnologias Utilizadas

🌐 **FRONTEND**

- **NEXTJS** — Framework React para SSR e geração estática.
  - TypeScript — Tipagem estática para JavaScript, melhorando a manutenção do código.
    - React Hooks (useState, useEffect, useParams) — Para gerenciar estado e ciclo de vida dos componentes.
    - Fetch API — Comunicação com a API Java.
    - Next Image — Otimização de imagens no Next.js.

⚙️ **BACKEND**

- **JAVA** (JAX-RS - Jakarta RESTful Web Services) — Para criação de endpoints RESTful.
  - DAO Pattern (Data Access Object) — Separação da lógica de acesso a dados.
  - JDBC (Java Database Connectivity) — Comunicação com banco de dados Oracle.

🛢️ **BANCO DE DADOS**

- **Oracle Database** — Armazenamento relacional das entidades (banda, álbum, menu).
  - SQL — Consultas customizadas com JOIN, ORDER BY, DBMS_RANDOM, etc.

🛠️ **Ferramentas de Desenvolvimento**

- **Eclipse IDE ou IntelliJ** — Ambiente de desenvolvimento Java.
- **Postman ou Insomnia** — Testes de endpoints da API.
- **Git/GitHub** — Controle de versão e hospedagem do projeto.

---

&nbsp;

## 🚀 Como criar a base de dados e utilizar a API

1. Configure o banco de dados e o servidor (Tomcat ou similar).
2. Acesse os endpoints via `http://localhost:8080/frontmusics/api`.
3. Importe o projeto em uma IDE Java (como Eclipse ou IntelliJ).

&nbsp;

### 🛢️Banco de Dados ![Database](https://img.shields.io/badge/Database-Oracle-blue?logo=oracle)

  📁 **Tabelas**

  - `banda`: Informações das bandas
  - `album`: Álbuns relacionados aos álbuns
  - `estilo`: Categorias musicais

  O SQL da estrutura do banco de dados pode ser encontrada no endereço: https://github.com/profmilanez-fiap/discosDB

Faça o _download_ das tabelas do banco e execute o script.

## 🧩 Estrutura da Tabela: `album`

| Campo       | Tipo      | Descrição                                         |
|-------------|-----------|---------------------------------------------------|
| `id`        | INTEGER   | Identificador único do álbum                      |
| `album`     | VARCHAR2  | Nome do álbum                                     |
| `imagem`    | VARCHAR2  | Caminho da imagem da capa                         |
| `categoria` | NUMBER    | Quantidade de faixas                              |
| `links`     | VARCHAR2  | Quantidade de faixas                              |
| `lancamento`| DATE      | Data de lançamento                                |
| `exibir`    | NUMBER    | Indica se deve ser exibido (0 ou 1)               |
| `faixas`    | INTEGER   | Quantidade de faixas                              |
| `descricao` | TEXT      | Descrição completa do álbum                       |
| `idbanda`   | INTEGER   | Chave estrangeira da banda                        |
| `slug`      | VARCHAR2  | URL amigável para o álbum                         |

---

&nbsp;

## 🧩 Estrutura da Tabela: `banda`

| Campo        | Tipo       | Descrição                           |
|--------------|------------|-------------------------------------|
| `id`         | NUMBER     | Identificador único da banda        |
| `banda`      | VARCHAR2   | Nome da banda                       |
| `integrantes`| VARCHAR2   | Integrantes da banda                |
| `descricao`  | VARCHAR2   | Descrição detalhada da banda        |
| `categoria`  | NUMBER     | ID da categoria                     |
| `links`      | VARCHAR2   | Link da categoria                   |
| `slug`       | VARCHAR2   | URL amigável da banda               |
| `imagem`     | VARCHAR2   | Imagem de capa da banda             |
| `exibir`     | NUMBER     | Indica se deve ser exibido (0 ou 1) |

---

&nbsp;

## 🧩 Estrutura da Tabela: `estilo`

| Campo        | Tipo       | Descrição                           |
|--------------|------------|-------------------------------------|
| `id`         | NUMBER     | Identificador único do estilo       |
| `estilo`     | VARCHAR2   | Nome do estilo musical              |
| `exibir`     | NUMBER     | Indica se deve ser exibido (0 ou 1) |
| `links`      | VARCHAR2   | URL amigável do estilo              |

---

## 🚀 Acesso aos endpoints ![REST API](https://img.shields.io/badge/API-RESTful-green?style=flat&logo=api)

### 🎵 Endpoint @Path("/estilo")

| Método | Caminho                  | Descrição                                                          | Saída  |
|--------|--------------------------| -------------------------------------------------------------------|---------------------------------------|
| GET    | /estilo/Editar           | Lista TODOS os estilos.                                            | [{ "id": 1, "estilo": "Grunge", "exibir": 1, "links": "grunge" }] |
| GET    | /estilo                  | Lista os estilos que devem ser exibidos para o usuário.            | [{ "id": 1, "estilo": "Grunge", "exibir": 1, "links": "grunge" }] |
| GET    | /estilo/{links}          | Busca os dados de um estilo específico pelo campo `links`.         | [{ "id": 1, "estilo": "Grunge", "exibir": 1, "links": "grunge" }] |
| POST   | /estilo                  | Cadastra um novo estilo.                                           | HTTP/1.1 201 Created |
| PUT    | /estilo/{links}          | Atualiza um estilo existente.                                      | HTTP/1.1 200 OK
| PUT    | /estilo/excluir/{id}     | Neste _endpoint_ não fazemos a exclusão _física_ do registro, apenas a exclusão 'lógica'. Atualizamos um _flag_, para `exibir` ou não um determinado estilo.| HTTP/1.1 200 OK  |
---
*Observação:* Para saber o significado dos códigos HTTP/1.1, visualiza a tabela explicativa no final desta documentação.
&nbsp;

### 🎸 Endpoint @Path("/bandas")

| Método | Caminho                    | Descrição                                                                 | Saída |
|--------|----------------------------| --------------------------------------------------------------------------|-------|
| GET    | /bandas                    | Lista todas as bandas disponíveis (públicas).                             | [{ "id": 1, "banda": "Queen", "links": "queen", "slug": "queen" }]|
| GET    | /bandas/{slug}             | Busca bandas por `slug` (geralmente para visualização pública).           | [{ "id": 1, "banda": "Nirvana", "descricao": "Banda de rock alternativo formada em 1987", "integrantes": "Kurt Cobain, Krist Novoselic, Dave Grohl", "links": "nirvana", "slug": "nirvana", "imagem": "nirvana.jpg", "categoria": "Rock Alternativo" }]|
| GET    | /bandas/editar/{slug}      | Busca banda específica pelo `slug` para edição.                           | [{ "id": 1, "banda": "Foo Fighters", "descricao": "Banda de rock liderada por Dave Grohl", "integrantes": "Dave Grohl, Nate Mendel, Pat Smear", "links": "foo-fighters", "slug": "foo-fighters", "imagem": "foo.jpg", "exibir": 1, "categoria": "2" }]|
| GET    | /bandas/editar             | Lista todas as bandas cadastradas (admin/edição).                         | [{ "id": 1, "banda": "Foo Fighters", "links": "foo-fighters", "exibir": 1, "slug": "foo-fighters" }]|
| POST   | /bandas/cadastrar          | Cadastra uma nova banda.                                                  | HTTP/1.1 201 Created |
| PUT    | /bandas/atualizar/{id}     | Atualiza os dados de uma banda pelo `id`.                                 | HTTP/1.1 200 OK |
| PUT    | /bandas/excluir/{id}       | Neste _endpoint_ não fazemos a exclusão _física_ do registro, apenas a exclusão 'lógica'. Atualizamos um _flag_, para `exibir` ou não um determinado estilo.  | HTTP/1.1 204 No Content |
---

&nbsp;

### 📀 Endpoint @Path("/album")

| Método | Caminho                     | Descrição                                                                  | Saída  |
|--------|-----------------------------|----------------------------------------------------------------------------|--------|
| GET    | /aleatorios                 | Retorna uma lista de 12 álbuns aleatórios para exibição na página inicial. | [{ "id": 3, "album": "Hybrid Theory", "nomeBanda": "Linkin Park","imagem": "hybrid.jpg","slug": "hybrid-theory" },...] |
| GET    | /buscarporcategoria/{links} | Busca álbuns com base no link da categoria.                                | [{ "id": 18, "album": "Fear of the Dark", "banda": 4, "categoria": 2, "descricao": "Fear of the Dark é o...", "exibir": 1, "faixas": "Be Quick or Be Dead; From...", "imagem": "fear-of-the-dark.jpg", "integrantes":"Steve Harris; Dave...", "lancamento":1992, "links": "heavy-metal", "nomeBanda": "Iron Maiden", "nomeEstilo":"Heavy Metal", "slug": "fear-of-the-dark" }] |
| GET    | /buscarTODOSalbuns          | Retorna todos os álbuns, inclusive os que não devem ser exibidos.          | [{ "id": 1, "album": "The Wall", "nomeBanda": "Pink Floyd", "slug": "the-wall", "slugBanda": "pink-floyd", "exibir": 1 }]  |
| GET    | /buscaralbuns               | Retorna apenas os álbuns marcados para exibição (exibir = 1).              | [{ "id": 1, "album": "Nevermind", "nomeBanda": "Nirvana", "slug": "nevermind", "slugBanda": "nirvana", "exibir": 1 }]  |
| GET    | /buscaalbum/{slug}          | Busca os detalhes de um álbum específico pelo `slug`.                      | [{ "id": 10, "album": "Nevermind", "banda": 7, "categoria": 3, "imagem": "nevermind.jpg", "lancamento": 1991, "faixas": "Smells Like Teen Spirit, Come as You Are, Lithium...", "descricao": "Álbum clássico da banda Nirvana.", "slug": "nevermind", "links": "grunge", "exibir": 1, "nomeEstilo": "Grunge", "nomeBanda": "Nirvana", "slugBanda": "nirvana" }]|
| GET    | /buscar/{slug}              | Busca o álbum pelo `slug` para preencher os campos de edição.              | [{ "id": 7, "album": "Hybrid Theory", "banda": 3, "categoria": 2, "imagem": "hybrid-theory.jpg", "lancamento": 2000, "faixas": "Papercut, In The End...", "descricao": "Álbum de estreia da banda Linkin Park...", "slug": "hybrid-theory", "links": "rock", "exibir": 1, "nomeEstilo": "Rock", "nomeBanda": "Linkin Park", "slugBanda": "linkin-park" }]  |
| POST   | /cadastrar                  | Cadastra um novo álbum no banco de dados.                                  | HTTP/1.1 201 Created  |
| PUT    | /atualizar/{id}             | Atualiza os dados de um álbum existente com base no `id`.                  | HTTP/1.1 200 OK |
| PUT    | /excluir/{id}               | Neste _endpoint_ não fazemos a exclusão _física_ do registro, apenas a exclusão 'lógica'. Atualizamos um _flag_, para `exibir` ou não um determinado estilo.                    | HTTP/1.1 204 No Content |
---

### 🔁 HTTP - STATUS

---

| Código | Status HTTP                      | Descrição                                                           |
|--------|----------------------------------|---------------------------------------------------------------------|
| 200    | OK                               | A requisição foi bem-sucedida e o servidor retornou a resposta.     |
| 201    | Created                          | A requisição foi bem-sucedida e um novo recurso foi criado.         |
| 202    | Accepted                         | A requisição foi aceita, mas ainda não foi processada.              |
| 204    | No Content                       | A requisição foi bem-sucedida, mas não há conteúdo a ser retornado. |
| 301    | Moved Permanently                | O recurso foi movido permanentemente para outra URL.                |
| 302    | Found                            | O recurso foi temporariamente movido para outra URL.                |
| 400    | Bad Request                      | A requisição não pode ser processada devido a erros de sintaxe.     |
| 401    | Unauthorized                     | A requisição requer autenticação do usuário.                        |
| 403    | Forbidden                        | O servidor entendeu a requisição, mas se recusa a autorizá-la.      |
| 404    | Not Found                        | O recurso solicitado não foi encontrado no servidor.                |
| 405    | Method Not Allowed               | O método HTTP usado não é permitido para o recurso solicitado.      |
| 500    | Internal Server Error            | Ocorreu um erro inesperado no servidor.                             |
| 502    | Bad Gateway                      | O servidor agiu como um gateway e recebeu uma resposta inválida.    |
| 503    | Service Unavailable              | O servidor está temporariamente indisponível.                       |
| 504    | Gateway Timeout                  | O servidor não recebeu uma resposta a tempo de outro servidor.      |

---

## 🧑‍💻 Desenvolvedor

    Prof. Adriano Milanez
    profadriano.milanez@fiap.com.br
    Disciplina: Front-End Design Engineering
    Tecnologia em Análise e Desenvolvimento de Sistemas
    Faculdade de Informática e Administração Paulista - FIAP
