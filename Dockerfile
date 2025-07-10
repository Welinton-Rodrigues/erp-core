# --- FASE 1: BUILD ---
# Usa uma imagem JDK completa para compilar o código
FROM openjdk:17-jdk-slim AS builder

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia os arquivos de configuração do Maven e o pom.xml
# Isso permite que o Maven baixe as dependências primeiro, aproveitando o cache do Docker
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Garante que as dependências sejam baixadas para que o build seja mais rápido em execuções futuras
# se apenas o código fonte mudar
RUN ./mvnw dependency:go-offline

# Copia o código fonte da aplicação
COPY src/ ./src

# Compila o projeto e empacota em um JAR
# -DskipTests para pular testes no build do Docker (rode eles separadamente no CI/CD)
# -Dmaven.wagon.http.ssl.insecure=true se tiver problemas com repositórios Maven inseguros, mas evite
RUN ./mvnw clean install -DskipTests

# --- FASE 2: EXECUÇÃO ---
# Usa uma imagem JRE (Java Runtime Environment) menor para rodar a aplicação
# JRE é suficiente pois o código já foi compilado na fase anterior
FROM eclipse-temurin:17-jre-jammy 

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR compilado da fase 'builder' para esta fase de execução
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta que sua aplicação Spring Boot escutará (padrão é 8080)
EXPOSE 8080

# Define o usuário não-root para rodar a aplicação por questões de segurança
# Cria um grupo 'spring' e um usuário 'spring'
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring

# Comando que será executado quando o contêiner for iniciado
# 'java -jar app.jar' executa a aplicação Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]