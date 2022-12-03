### Projet: Automatisation d'envoie de mails contenant des blagues  
T. Germano, G. Courbat
___
## Description:

Cette application java permet d'envoyer des mails contenant des blagues via une 
liste  prédéfinies de victimes à un serveur SMTP.

## Implémentation de testes:
Pour ce projet, nous avons utilisé un serveur mockmock STMP disponible à cette 
[adresse](https://github.com/DominiqueComte/MockMock).  
Un Serveur Mock est un serveur utilisé pour effectuer des testes. Nous l'avons 
utilisé afin de tester nos envoies de mails en local. Pour cela, nous avons 
implémenté un conteneur Docker plutôt que de faire tourner le serveur directement 
sur la machine

### Mise en place du serveur STMP avec Docker:

1) Cloner le repo du serveur mockmock sur sa machine.
2) Installer Docker sur sa machine ([Site de Docker](https://www.docker.com/)) et 
   suivre le tutoriel d'installation sur le site.
3) Créer une image du serveur avec un script par exemple. Le script devrait 
   ressembler à ceci mais en remplaçant les chemins d'accès.  


    #!/bin/bash
    mvn clean install --file /home/axion/DAI/lab4/mockmock/MockMock/pom.xml
    cp /home/axion/DAI/lab4/mockmock/MockMock/target/MockMock-1.4.1-SNAPSHOT.one-jar.jar .
    docker build --tag axion/mockmocksmtpserver .

4) Créer le Dockerfile :  
   Bien entendu, les chemins d'accès seront different. Il faut aussi choisir sa 
   bonne version de Openjdk.
    

    FROM eclipse-temurin:17
    RUN mkdir /opt/app
    RUN apt-get update && apt-get -y install netcat && apt-get clean
    COPY MockMock-1.4.1-SNAPSHOT.one-jar.jar /opt/app/serversmtp.jar
    EXPOSE 25
    EXPOSE 8282
    CMD ["java", "-jar", "/opt/app/serversmtp.jar"]

5) Créer un script qui va lancer/créer le conteneur


    #!/bin/bash
    docker run -p 25:25 -p 8282:8282 axion/mockmocksmtpserver

6) (pour relancer/quitter le serveur smtp, il suffit d'aller dans l'app docker et 
   démarrer/éteindre le conteneur)

## Instruction de configuration du client:  
Fichier config à disposition, --> remplacer les vals pour changer les params(port,
adresse et nb de grp)...

build l'app et la run..

## Implémentation:  
 faire diagramme uml des classes... 
décrire vite fait ce qu'il se passe dans les classes etc...

ajouter screen shoot de l'app dialogue client-serveur




