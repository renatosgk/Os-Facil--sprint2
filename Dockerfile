# ----------------------------------------------------------------------
# STAGE 1: BUILD (Etapa de Compilação)
# Esta etapa usa uma imagem completa com JDK e Maven para compilar o código.
# ----------------------------------------------------------------------
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos de configuração do Maven e o código-fonte
COPY pom.xml .
COPY src /app/src

# Comando de Build: limpa, compila e empacota o JAR, pulando os testes
RUN mvn clean package -DskipTests

# ----------------------------------------------------------------------
# STAGE 2: RUN (Etapa de Execução)
# Esta etapa usa uma imagem muito mais leve (somente JRE) para rodar a aplicação.
# ----------------------------------------------------------------------
FROM eclipse-temurin:17-jre-alpine

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR gerado na primeira etapa (build) para a pasta de execução
# LEMBRE-SE: 'OSfacil-0.0.1-SNAPSHOT.jar' é o nome do seu JAR baseado no seu pom.xml
COPY --from=build /app/target/OSfacil-0.0.1-SNAPSHOT.jar app.jar

# Define a porta que o Spring Boot usa (Render a mapeará)
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]