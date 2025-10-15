# ===============================
# 🌱 Etapa 1 - Build da aplicação
# ===============================
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copia todos os arquivos do projeto
COPY . .

# Dá permissão de execução ao Maven Wrapper (importante!)
RUN chmod +x mvnw

# Executa o build da aplicação (sem testes)
RUN ./mvnw clean package -DskipTests

# ===============================
# 🚀 Etapa 2 - Imagem final
# ===============================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia o JAR gerado da etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java","-jar","app.jar"]
