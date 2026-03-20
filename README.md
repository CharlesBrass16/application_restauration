# Projet - Restalo

Projet académique réalisé dans le cadre du cours GLO-2003

Ce projet consistait au développement d'une application REST permettant la gestion d'un restaurant.
Les différentes fonctionnalités offertes comportent notamment, sans y être limités:

- Création et suppression d'un restaurant de la part d'un propriétaire
- Création d'un menu avec ajout ou suppression de repas par le propriétaire
- Création d'un horaire d'ouverture et modification de celui-ci par le propriétaire
- Création de réservation par des clients

Coéquipiers/contributeurs :

- Élodie Langevin (github : Feltear)
- Charles-Antoine Gaudreault (github : CharlesAntoineUL)
- Dinar Sougoudji (github : SADIN4)
- Carl Boulianne-Boivin (github : vigenere23)

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

