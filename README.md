# 🎵 SocialMusic

![Java](https://img.shields.io/badge/Java-21-red?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.6-green?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?logo=postgresql)
![Koyeb](https://img.shields.io/badge/Deploy-Koyeb-orange?logo=koyeb)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

**SocialMusic** é uma rede social voltada para apaixonados por música!  
Os usuários podem cadastrar, avaliar e comentar músicas, álbuns e playlists — além de criar suas próprias coleções e interagir com o conteúdo de outros usuários.

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.6**
    - Spring Web
    - Spring Data JPA
    - Spring Security (JWT)
    - Validation (Jakarta)
- **PostgreSQL** (Banco de Dados)
- **ModelMapper** (conversão de DTOs)
- **Lombok**
- **Docker / Koyeb** (deploy na nuvem)

---

## 🧩 Funcionalidades

✅ **Autenticação e Registro de Usuários**
- Login e cadastro com validação
- Geração de token JWT
- Proteção de endpoints autenticados

🎵 **Músicas**
- CRUD completo de músicas
- Apenas o dono pode editar ou excluir suas músicas
- Avaliação de 0 a 5 estrelas (média atualiza automaticamente)

💿 **Álbuns**
- Cadastro e listagem de álbuns com paginação e filtros

🎧 **Playlists**
- Usuário pode criar e gerenciar suas playlists
- Pode adicionar/remover músicas
- Visualizar playlists de outros usuários

💬 **Comentários e Reações**
- Comentar em músicas
- Curtir / descurtir comentários
- Excluir seus próprios comentários

⭐ **Avaliações**
- Avaliar músicas individualmente
- Média atualizada em tempo real

---

## 🗄️ Estrutura do Projeto

```
com.progweb.socialmusic
├── album
│ ├── api / domain / service
├── comment
│ ├── api / domain / service
├── music
│ ├── api / domain / service
├── playlist
│ ├── api / domain / service
├── rating
│ ├── api / domain / service
├── user
│ ├── api / domain / service
├── config
│ ├── segurança, mapeamento e beans globais
└── exceptions
```
---

## ⚙️ Configuração do Projeto

### 🧠 Pré-requisitos

- **JDK 21**
- **Maven 3.9+**
- **PostgreSQL** (ou banco remoto como Railway / Neon)

---

### 🔧 Passos para rodar localmente

1️⃣ Clone o repositório:
```bash
git clone https://github.com/<seu-usuario>/socialmusic.git
cd socialmusic
```

2️⃣ Configure o arquivo src/main/resources/application.properties:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/socialmusic
spring.datasource.username=postgres
spring.datasource.password=senha

jwt.secret=sua_chave_jwt_aqui
jwt.expiration=86400000
```
3️⃣ Execute o projeto:

```
./mvnw spring-boot:run

Ou

Apenas rode a classe main da SocialmusicApplication
```
4️⃣ Acesse:

```
http://localhost:8080/{de acordo com os controllers}
```

5️⃣ Pegue o arquivo SocialMusic.json na raíz do projeto e importe ele no Insomnia. Nele já tem todos os edpoints necessários

🧑‍💻 Autores

- Levy Martins Nascimento
- Clawê Max Vasconcelos de Queiros
- Gustavo Gadelha Silva


