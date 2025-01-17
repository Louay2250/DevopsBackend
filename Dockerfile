# Utiliser une image de base Java 17
FROM eclipse-temurin:17-jdk-alpine

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier jar généré dans le conteneur
COPY target/*.jar app.jar

# Exposer le port utilisé par l'application (par défaut 8080 pour Spring Boot)
EXPOSE 8080

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
