# ===============================
# 🌱 Etapa 1 - Build da aplicação
# ===============================
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copia o projeto
COPY . .

# Dá permissão de execução ao Maven Wrapper
RUN chmod +x mvnw

# Compila o projeto sem rodar testes
RUN ./mvnw clean package -DskipTests

# ===============================
# 🚀 Etapa 2 - Imagem final
# ===============================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia o JAR gerado
COPY --from=build /app/target/*.jar app.jar

# Define a porta padrão
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java","-jar","app.jar"]
