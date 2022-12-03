# Projet: Automatisation d’envois de mails contenant des blagues

### Par Thomas Germano et Guillaume Courbat

___
## Description

Ce projet a été développé dans le contexte d'un cours, nommé **DAI** (**D**éveloppement d'**A**pplication **I**nternet) donné à la HEIG-VD durant le 3ème semestre.

Ce projet est une application cliente permettant d'effectuer une campagne de farce de mail forgé en fournissant une liste d'adresse e-mail victime et une liste de messages électroniques. 

Plus concrètement, voici ce que l'utilisateur peut faire avec l'application :

- L'utilisateur peut définir une liste de victimes
- L'utilisateur peut définir combien de groupes de victimes doivent être formés dans une campagne donnée. Dans chaque groupe de victimes, il y a 1 expéditeur et au moins 2 destinataires (c'est-à-dire que la taille minimale d'un groupe est de 3).
- L'utilisateur peut définir une liste de messages électroniques. Lorsqu'une farce est jouée sur un groupe de victimes, l'un de ces messages est sélectionné. Le message est envoyé à tous les destinataires du groupe, à partir de l'adresse de l'expéditeur du groupe. En d'autres termes, les victimes destinataires sont amenées à croire que la victime expéditrice les a envoyés.

## Qu'est-ce qu'un serveur MockMock ?

MockMock est un serveur SMTP multiplateforme construit en Java. 

Il vous permet de tester si des courriels sortants sont envoyés (sans les envoyer réellement) et de voir à quoi ils ressemblent. 

Il fournit une interface web qui affiche les courriels qui ont été envoyés et vous montre le contenu de ces courriels. 

Si vous utilisez MockMock, vous pouvez être sûr que vos e-mails sortants n'atteindront pas vos clients ou utilisateurs par accident. 

Il s'agit simplement d'un serveur SMTP fictif qui n'a aucune fonctionnalité d'envoi d'e-mails.

## Mise en place du serveur SMTP

### Pré-requis

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)

#### Marche à suivre

1. Pour commencer, créez le dossier `mockmock` et allez dedans à l'aide la commande suivante :

   ```bash
   mkdir mockmock && cd mockmock
   ```

2. Ensuite, créez le script `setup.sh` et copier le contenu suivant à l'intérieur :

   ```bash
   #!/bin/bash
   git clone https://github.com/DominiqueComte/MockMock.git
   mvn clean install --file $(dirname "$0")/MockMock/pom.xml
   cp $(dirname "$0")/MockMock/target/MockMock-*-SNAPSHOT.one-jar.jar .
   docker build --tag mocksmtp/mockmocksmtpserver .
   ```

   Ce script va cloner le dépôt git contenant le serveur MockMock expliqué plus haut, le build puis créer une image docker, en se basant sur le fichier `Dockerfile` ci-dessous, qui permet d'exécuter le serveur MockMock.

3. Puis, créez le fichier `Dockerfile` et copier le contenu suivant à l'intérieur :

   ```dockerfile
    FROM eclipse-temurin:17
    RUN mkdir /opt/app
    RUN apt-get update && apt-get -y install netcat && apt-get clean
    COPY MockMock-*-SNAPSHOT.one-jar.jar /opt/app/serversmtp.jar
    EXPOSE 25
    EXPOSE 8282
    CMD ["java", "-jar", "/opt/app/serversmtp.jar"]
   ```

   Cela permet de créer un conteneur qui exécutera le serveur MockMock à son démarrage.

4. Une fois ces deux fichiers créés, exécutez la commande suivante pour rendre le script exécutable :

   ```bash
   chmod a+x setup.sh
   ```

5. Désormais, exécutez le script à l'aide la commande suivante afin de créer l'image docker :

   ```bash
   ./setup.sh
   ```

   et voilà, vous avez une image docker permettant de simuler un serveur SMTP.

6. Finalement, pour lancer un conteneur basé sur l'image précédemment créé, utilisez la commande suivante :

   ```bash
   docker run -p 25:25 -p 8282:8282 mocksmtp/mockmocksmtpserver
   ```

Et voilà, vous avez un serveur SMTP fictif fonctionnel. Pour pouvoir accéder à l'interface web, vous pouvez vous rendre à [localhost](http://localhost:8282).

## Utilisation de l'application client



## Instruction de configuration du client:  
Fichier config à disposition, --> remplacer les vals pour changer les params(port,
adresse et nb de grp)...

build l'app et la run..

## Implémentation
### UML



### Classes

#### ClientSMTP

#### Personne

#### Mail

#### Generator



 faire diagramme uml des classes... 
décrire vite fait ce qu'il se passe dans les classes etc...

ajouter screen shoot de l'app dialogue client-serveur



