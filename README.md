
# RealConsult - Backend

Backend da plataforma RealConsult, responsável pelas APIs de gestão financeira, economia e monitoramento operacional.

## 🛠️ Tecnologias Utilizadas

- **Java**
- **Spring Boot**
- **MySQL**
- **H2 Database** (ambiente de desenvolvimento/teste)
- **JWT**
- **Bcrypt**

## ✅ Pré-requisitos

- **Java JDK** instalado
- **Maven**
- **Git**

## 🚀 Instruções de Instalação e Execução

### Passo 1: Clonar o repositório

```bash
git clone https://github.com/ICEI-PUC-Minas-PMGES-TI/pmg-es-2025-2-ti3-9577100-realconsult.git
cd pmg-es-2025-2-ti3-9577100-realconsult
```

### Passo 3: Configurar o Backend

1. Navegue até o diretório `backend`:

```bash
cd backend
```

2. Configure o arquivo `backend/src/main/resources/application.properties`:

- Se estiver em **ambiente de desenvolvimento**, mantenha as configurações padrões.
- Em outros ambientes, ajuste:
  - URL do banco de dados
  - Usuário e senha
  - Demais propriedades necessárias

3. Se necessário, configure variáveis adicionais em um arquivo `.env` do backend.

4. Execute o backend com Spring Boot:

```bash
mvn spring-boot:run
```

### Passo 4: Configurar Banco de Dados H2 (Ambiente de Desenvolvimento)

Em ambiente de desenvolvimento e teste, o sistema utiliza **H2**:

1. Acesse o H2 Console:

```text
http://localhost:8080/h2-console
```

2. Use as credenciais definidas em `application.properties`, por exemplo:

- **JDBC URL**: `jdbc:h2:file:~/vtreal;DB_CLOSE_ON_EXIT=FALSE`
- **Username**: `sa`
- **Password**: `password`

3. Para popular o banco com dados mockados, execute no H2 Console o SQL do arquivo:

```text
Codigo/mock vtreal.sql
```

### Passo 5: Primeiro Acesso

Caso não exista um usuário com o cargo de **Administrador**, o sistema cria automaticamente um usuário padrão:

- **Email:** `adm@vtreal.com.br`  
- **Senha:** `123456`

### Passo 6: Endpoints da API

Com o backend em execução, a API estará disponível em:

```text
http://localhost:8080
```

O frontend padrão acessa essa API via `NEXT_PUBLIC_API_URL` configurada no `.env` do frontend.
