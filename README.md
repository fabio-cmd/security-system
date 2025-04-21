
# FIAP Security System API

## Visão Geral

A FIAP Security System API fornece um conjunto de endpoints para gerenciar funcionários, lidar com autenticação de usuários e registrar incidentes de segurança. A API é destinada a sistemas de segurança onde é necessário registrar ocorrências, gerenciar usuários e aplicar controle de acesso baseado em funções.

## Funcionalidades

- **Gerenciamento de Funcionários**: Criar, atualizar e buscar registros de funcionários.
- **Autenticação de Usuários**: Registrar novos usuários e gerenciar sessões de login.
- **Gerenciamento de Ocorrências**: Criar, atualizar, deletar e buscar incidentes de segurança.

## API Endpoints

### 1. Gerenciamento de Funcionários

- **Criar Funcionário**: `POST /api/employees`
  - Adiciona um novo funcionário ao sistema.
- **Listar Todos os Funcionários**: `GET /api/employees`
  - Recupera todos os registros de funcionários.
- **Buscar Funcionário por ID**: `GET /api/employees/{id}`
  - Retorna os detalhes de um funcionário específico.
- **Buscar Funcionários por Função**: `GET /api/employees/role/{role}`
  - Lista funcionários com base no cargo ou função.

### 2. Autenticação

- **Registro**: `POST /api/auth/register`
  - Registra um novo usuário no sistema.
- **Login**: `POST /api/auth/login`
  - Autentica um usuário existente e retorna um token.

### 3. Gerenciamento de Ocorrências

- **Listar Ocorrências**: `GET /api/incidents`
  - Lista todas as ocorrências registradas.
- **Criar Ocorrência**: `POST /api/incidents`
  - Registra uma nova ocorrência.
- **Buscar Ocorrência por ID**: `GET /api/incidents/{id}`
  - Retorna os detalhes de uma ocorrência específica.
- **Buscar Ocorrências por Status**: `GET /api/incidents/status/{status}`
  - Lista ocorrências filtradas por status.
- **Atualizar Status da Ocorrência**: `PATCH /api/incidents/{id}/status`
  - Atualiza o status de uma ocorrência.
- **Deletar Ocorrência**: `DELETE /api/incidents/{id}`
  - Remove uma ocorrência do sistema.

## Getting Started

### Prerequisites

- **Java 21**: The backend is built using Java 21.
- **Docker**: Used for containerization and deployment.
- **kubectl**: For interacting with Kubernetes cluster (AKS).
- **Azure CLI** (opcional): Para gestão do cluster se necessário.

### Running the API (Staging)

1. Criar o namespace staging:
```bash
kubectl create namespace staging
```

2. Aplicar as secrets e serviços do banco Oracle:
```bash
kubectl apply -f k8s/oracle/secrets.yaml -n staging
kubectl apply -f k8s/oracle/service.yaml -n staging
kubectl apply -f k8s/oracle/statefulset.yaml -n staging
```

3. Aguardar até o pod `oracle-xe-0` ficar com STATUS `Running`

4. Aplicar os deployments da aplicacao:
```bash
kubectl apply -f k8s/staging/configmap.yaml -n staging
kubectl apply -f k8s/staging/service.yaml -n staging
kubectl apply -f k8s/staging/deployment.yaml -n staging
```

5. Obter o IP externo:
```bash
kubectl get svc -n staging
```

6. Testar a API com o Postman usando o IP externo

### Running the API (Production)

1. Criar o namespace production:
```bash
kubectl create namespace production
```

2. Aplicar a secret com credenciais do Oracle:
```bash
kubectl apply -f k8s/production/oracle-secret.yaml -n production
```

3. Aplicar o configmap da aplicacao:
```bash
kubectl apply -f k8s/production/configmap.yaml -n production
```

4. Aplicar os manifests:
```bash
kubectl apply -f k8s/production/service.yaml -n production
kubectl apply -f k8s/production/deployment.yaml -n production
```

5. Obter o IP externo para testar com o Postman:
```bash
kubectl get svc -n production
```

## CI/CD com GitHub Actions

Pipeline com 3 jobs:

### `build_test_and_push`
- Checkout do repo
- Configura JDK 21 com Temurin
- Compila projeto, roda testes
- Cria imagem Docker (`latest`, `staging`) e faz push pro Docker Hub

### `deploy_staging`
- Aplica kubeconfig do Secret
- Faz deploy no AKS namespace `staging`
- Aplica `deployment.yaml` e `service.yaml`

### `deploy_production`
- Aplica kubeconfig e faz deploy no namespace `production`

## Orquestração e Containerização

- Docker utilizado para empacotar a aplicacao.
- AKS gerencia os pods, services, secrets, configmaps e banco Oracle.
- Ambiente separado: `staging` para testes, `production` para uso real.

