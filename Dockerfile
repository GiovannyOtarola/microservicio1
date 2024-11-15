FROM openjdk:21-ea-24-oracle

WORKDIR /app
COPY target/microservicio1-1.0-SNAPSHOT.jar app.jar
COPY Wallet_dbFullstack3 /app/oracle_wallet
EXPOSE 8080

CMD [ "java", "-jar", "app.jar" ]