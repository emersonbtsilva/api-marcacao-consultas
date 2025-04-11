# 🏥 API de Marcação de Consultas

Esta é uma aplicação desenvolvida com **Spring Boot** que permite o gerenciamento de consultas médicas. Ela oferece funcionalidades como cadastro de usuários (médicos e pacientes), gerenciamento de especialidades médicas e agendamento de consultas.

---

## 📐 Arquitetura

A aplicação segue uma arquitetura em camadas típica de projetos Spring:

- **Controllers**: Responsáveis por receber requisições HTTP e encaminhá-las para os serviços apropriados.
- **Services**: Contêm a lógica de negócio da aplicação.
- **Repositories**: Responsáveis pela persistência de dados.
- **Models/Entities**: Representam as entidades de negócio.
- **Security**: Implementa autenticação e autorização via JWT.

---

## 🧍 Entidades Principais

### 1. Usuário
- Representa tanto **pacientes** quanto **médicos**.
- Atributos: `id`, `nome`, `email`, `senha`, `tipo` (`MEDICO` ou `PACIENTE`), `especialidades` (para médicos).
- **Regras**:
  - Emails devem ser únicos.
  - Senhas são criptografadas com **BCrypt**.
  - Médicos podem estar associados a uma ou mais especialidades.
  - Médicos podem ser filtrados por especialidade.

### 2. Especialidade
- Representa as especialidades médicas disponíveis.
- Atributos: `id`, `nome`.

### 3. Consulta
- Representa o agendamento de uma consulta médica.
- Atributos: `id`, `paciente`, `médico`, `data`, `hora`, `status` (`agendada`, `realizada`, `cancelada`).
- **Regras**:
  - Uma consulta deve conter médico e paciente associados.
  - Deve ter data e hora definidas.
  - Consultas podem ser buscadas por ID ou canceladas/excluídas.

---

## 🔐 Segurança

A API utiliza autenticação baseada em **JWT (JSON Web Token)**.

- **Endpoints públicos**:
  - `POST /usuarios`: Cadastro de usuários.
  - `POST /usuarios/login`: Autenticação de usuários.
  - Console H2 (apenas em ambiente de desenvolvimento).

- **Demais endpoints** requerem autenticação via token JWT.
- O token deve ser enviado no cabeçalho `Authorization`:
