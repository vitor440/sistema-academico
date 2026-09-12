# sistema-academico
Sistema web full-stack de gerenciamento acadêmico, desenvolvido com
Java/Spring Boot e React. A aplicação possui dashboards específicos
para administradores, docentes e alunos, autenticação OAuth2 com
Authorization Code + PKCE, controle de acesso baseado em roles e
regras de negócio para matrículas, disciplinas, avaliações,
frequência e horários.

## Visão Geral
Sistema Acadêmico é uma aplicação web full-stack que centraliza as operações de uma instituição acadêmica:

* Admin Dashboard: Gestão de usuários, departamentos, cursos, disciplinas e matrículas
* Docente Dashboard: Lançamento de notas, controle de frequência, agendamento de exames
* Aluno Dashboard: Visualização de tarefas, exames, notas, frequência e solicitação de matrículas



## Tecnologias Utilizadas

### Backend
- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security
- Spring Authorization Server
- OAuth2
- PostgreSQL
- Flyway
- MapStruct
- Bean Validation
- Query by Example / Specification
- OpenAPI / Swagger (Documentação)
  
### Frontend
- React 19
- Vite
- Material UI
- MUI X Charts
- React Router DOM
- Axios
- React Toastify

## Controle de acesso

A aplicação utiliza controle de acesso baseado em roles (`ADMIN`,
`DOCENTE` e `ALUNO`).

Além da autorização por role, a API aplica regras de autorização
em nível de recurso.

Por exemplo, no endpoint `/resultados`:

- `ADMIN`: possui acesso aos resultados conforme suas permissões.
- `DOCENTE`: recebe apenas resultados relacionados às disciplinas
  que leciona.
- `ALUNO`: recebe apenas resultados das disciplinas em que está
  matriculado.

Dessa forma, o backend garante que um usuário não consiga acessar
dados acadêmicos pertencentes a outros usuários ou disciplinas.

## 🚀 Como Executar

### Pré-requisitos
- Docker e Docker Compose instalados
- **OU** Node.js 18+ e Java 21 (para desenvolvimento local)

### Opção 1: Com Docker Compose (Recomendado)

```bash
# Clone o repositório
git clone https://github.com/vitor440/sistema-academico.git
cd sistema-academico
```
#### Inicie todos os serviços (DB, Backend, Frontend)
```bash
docker-compose up --build
```
Acesse a aplicação:
* Frontend: http://localhost:5173
* Backend API: http://localhost:8080/swagger-ui.html

### Opção 2: Desenvolvimento Local
```bash
cd server

# Build e execute
./mvnw clean package
./mvnw spring-boot:run

# Servidor rodará em http://localhost:8080
# Documentação da API: http://localhost:8080/swagger-ui.html

```

## Demonstração


### Admin Dashboard

https://github.com/user-attachments/assets/6c8213e9-6917-4ec3-b9eb-6e647eb4c499




### Docente Dashboard

https://github.com/user-attachments/assets/9fb2a80b-384d-484d-a6da-abe3fa5309ba


### Aluno Dashboard


https://github.com/user-attachments/assets/fccabe46-a1c0-4327-b23b-9338ee3a3654


## Fluxo de Segurança
A Aplicação usa o protocolo de segurança OAuth2 com fluxo Authorization Code + PKCE. O Frontend fica responsável por gerar os valores code_challenge e code_verifier que são mandados para o authorization server
usando a url de authenticação. Após isso, O Frontend obtém o token através da URL "oauth2/token" mandando o code_verifier junto. Depois de obter o token, O Frontend armazena no localstorage e usa para todas as requisições ao backend

## 🔐 Usuários de Teste

A aplicação possui usuários pré-configurados para facilitar os testes das diferentes funcionalidades e níveis de acesso.

### 👥 Roles

|  ID | Role      |
| :-: | :-------- |
|  1  | `ADMIN`   |
|  2  | `DOCENTE` |
|  3  | `ALUNO`   |

### 🔑 Senhas

As senhas dos usuários de teste são definidas de acordo com a role:

| Role      | Senha        |
| :-------- | :----------- |
| `ADMIN`   | `admin123`   |
| `DOCENTE` | `docente123` |
| `ALUNO`   | `aluno123`   |

### 👤 Usuários disponíveis

| Usuário     | E-mail                                            | Role      | Senha        |
| :---------- | :------------------------------------------------ | :-------- | :----------- |
| `admin`     | [admin@email.com](mailto:admin@email.com)         | `ADMIN`   | `admin123`   |
| `Marcos`    | [marcos@email.com](mailto:marcos@email.com)       | `DOCENTE` | `docente123` |
| `Gabriel`   | [gabriel@email.com](mailto:gabriel@email.com)     | `ALUNO`   | `aluno123`   |
| `Felipe`    | [felipe@email.com](mailto:felipe@email.com)       | `ALUNO`   | `aluno123`   |
| `Paulo`     | [paulo@email.com](mailto:paulo@email.com)         | `DOCENTE` | `docente123` |
| `admin2`    | [admin2@email.com](mailto:admin2@email.com)       | `ADMIN`   | `admin123`   |
| `Daniel`    | [daniel@email.com](mailto:daniel@email.com)       | `ALUNO`   | `aluno123`   |
| `Pedro`     | [pedro@email.com](mailto:pedro@email.com)         | `ALUNO`   | `aluno123`   |
| `Juliana`   | [juliana@email.com](mailto:juliana@email.com)     | `ALUNO`   | `aluno123`   |
| `Mikaele`   | [mikaele@email.com](mailto:mikaele@email.com)     | `ALUNO`   | `aluno123`   |
| `Fernando`  | [fernando@email.com](mailto:fernando@email.com)   | `ALUNO`   | `aluno123`   |
| `Priscilia` | [priscilia@email.com](mailto:priscilia@email.com) | `ALUNO`   | `aluno123`   |
| `Jonas`     | [jonas@email.com](mailto:jonas@email.com)         | `ALUNO`   | `aluno123`   |
| `Fernanda`  | [fernanda@email.com](mailto:fernanda@email.com)   | `ALUNO`   | `aluno123`   |
| `Bernardo`  | [bernardo@email.com](mailto:bernardo@email.com)   | `ALUNO`   | `aluno123`   |
| `Viviane`   | [viviane@email.com](mailto:viviane@email.com)     | `ALUNO`   | `aluno123`   |
| `Nelson`    | [nelson@email.com](mailto:nelson@email.com)       | `ALUNO`   | `aluno123`   |
| `Wilson`    | [wilson@email.com](mailto:wilson@email.com)       | `ALUNO`   | `aluno123`   |
| `Miguel`    | [miguel@email.com](mailto:miguel@email.com)       | `ALUNO`   | `aluno123`   |
| `Cristiano` | [cristiano@email.com](mailto:cristiano@email.com) | `ALUNO`   | `aluno123`   |
| `Carlos`    | [carlos@email.com](mailto:carlos@email.com)       | `ALUNO`   | `aluno123`   |
| `Victor`    | [victor@email.com](mailto:victor@email.com)       | `ALUNO`   | `aluno123`   |
| `Douglas`   | [douglas@email.com](mailto:douglas@email.com)     | `ALUNO`   | `aluno123`   |
| `Samantha`  | [samantha@email.com](mailto:samantha@email.com)   | `ALUNO`   | `aluno123`   |
| `David`     | [david@email.com](mailto:david@email.com)         | `ALUNO`   | `aluno123`   |
| `Luiz`      | [luiz@email.com](mailto:luiz@email.com)           | `ALUNO`   | `aluno123`   |





