# Usa una imagen base de Java
FROM openjdk:21-ea-24-oracle

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR construido al contenedor
COPY target/microservicio1-1.0-SNAPSHOT.jar app.jar

COPY Wallet_dbFullstack3 /app/oracle_wallet
# Expone el puerto en el que la aplicación escuchará
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]