# Projet - Restalo

Le meilleur logiciel de réservation en restauration!

## Requis

- Java 21
- Maven 3.x

## Commandes

### Compilation

```
mvn compile
```

### Exécution

```
mvn exec:java
```

### Exécution avec docker InMemory

Linux/Mac
```shell
$ sudo docker build -t restalo .

$ sudo docker run -p 8080:8080 -it restalo mvn exec:java"
```

Windows
```
docker build -t restalo .

docker run -p 8080:8080 -it restalo mvn exec:java"
```

### Exécution avec docker Mongo

Linux/Mac
```shell
$ sudo build -t restalo .

$ sudo docker compose up -d
$ sudo docker run --env MONGO_CLUSTER_URL=mongodb://equipe17:AVeryComplexPasswordThatIsNot123@mongo:27017 --env MONGO_DATABASE=local -p 8080:8080 -it restalo mvn exec:java -D persistence="mongo"
```

Windows
```
docker build -t restalo .

docker compose up -d
docker run --env MONGO_CLUSTER_URL=mongodb://equipe17:AVeryComplexPasswordThatIsNot123@mongo:27017 --env MONGO_DATABASE=local -p 8080:8080 -it restalo mvn exec:java -D persistence="mongo"
```
## Routes pour la feature des menus

### Créer un menu

```http request
POST /restaurants/{restaurantId}/menu
Owner: <OwnerId>
```
Body
```json
{
  "dishes": ["Spaghetti", "Poutine", "Poulet"]
}
```

response
`201 Created`

### Lister les menus d'un restaurant

```http request
GET /restaurants/{restaurantId}/menu
```

response
`200 OK`
```json
{
  "dishes": ["Spaghetti", "Poutine", "Poulet"]
}
```

### Supprimer un menu

```http request
DELETE /restaurants/{restaurantId}/menu
Owner: <OwnerId>
```

response
`204 No Content`
## Documentation du projet
- [Code de Conduite](CODE_OF_CONDUCT.md)
- [Licence](LICENSE.md)
- [Guide de Contribution](CONTRIBUTING.md)

## Badges CI
[![Docker Image CI](https://github.com/GLO2003-H24-eq17/Restalo-H24/actions/workflows/docker-image.yml/badge.svg)](https://github.com/GLO2003-H24-eq17/Restalo-H24/actions/workflows/docker-image.yml)
[![Java CI with Maven](https://github.com/GLO2003-H24-eq17/Restalo-H24/actions/workflows/maven.yml/badge.svg)](https://github.com/GLO2003-H24-eq17/Restalo-H24/actions/workflows/maven.yml)

Pour des informations supplémentaires ou des questions, veuillez consulter nos guidelines dans [CONTRIBUTING.md](CONTRIBUTING.md) ou ouvrir un issue.

Merci de contribuer à Restalo et de rendre la réservation en restauration plus facile et plus accessible pour tous!

