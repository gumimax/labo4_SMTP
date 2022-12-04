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

   Ce script va cloner le dépôt git contenant le serveur MockMock expliqué plus haut, le build puis créer une image docker, en se basant sur le fichier `Dockerfile` ci-dessous, qui permet d'exécuter le serveur MockMock dans un conteneur docker.

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

### Configuration

Les fichiers de configuration se trouve dans le dossier `MailSender/src/main/java/MailSender/config`

#### Serveur SMTP

Nom du fichier : `config.properties`

##### Exemple

```properties
smtpServerAddress=localhost
smtpServerPort=25
...
```

C'est maintenant que le serveur MockMock entre en jeu. Si vous souhaitez l'utiliser comme présenter ci-dessus, vous pouvez laisser le fichier de configuration telle quelle.

Sinon adaptez les paramètres par rapport à votre serveur SMTP.

#### Nombre de groupes

Nom du fichier : `config.properties`

##### Exemple

```properties
...
grpNb=5
```

Ici vous pouvez spécifiez le nombre de groupe qui va être formé à partir de la liste d'adresse mail fournie. Attention, il faut que le nombre de groupe soit 3 fois moins que le nombre de mails contenus dans la liste !

#### Adresses mails

Nom du fichier : `listOfAddress.utf8`

##### Exemple

```properties
blabla@bli.com
blablo@blu.com
blobli@blo.com
blabliblu@bla.com
test@erf.com
hophop@yep.com
ewlfwe@ffef.com
qwdef@fewfb.com
uhefewfewf@eefefe.com
ejfiefew@hop.com
testeebfehfef@wfgr.com
...
```

Ici vous pouviez spécifier les adresses mails qui vont être touchées par la campagne de farce faites à l'aide de mails forgés. Attention, le mail doit être conforme, sinon l'application ne marchera pas.

#### Corps de mail

Nom du fichier : `prank.utf8`

##### Exemple

```properties
Salut les gars,
Je vous écris pour vous dire que je vais déménager aux Etats-unis d'amérique.
==
Bip bop,
bop bip bip bop bop bop
bop bap bap bap bip ? bip bop bop !

bip,

bip bop
...
```

Ici vous pouvez spécifier une liste de corps de mail qui va être utilisée pour la campagne de farce. La séparation des corps de mails se fait grâce à un double égal, voici un exemple :

```properties
blabla
==
blabaa
```

### Compilation

## Instruction de configuration du client:  
Fichier config à disposition, --> remplacer les vals pour changer les params(port,
adresse et nb de grp)...

build l'app et la run..

## Implémentation
### UML

![classes](../media/classes.jpg)

### Classes

#### Personne

##### Attribut

###### address: String

Adresse e-mail de la personne

#### Generator

Cette classe permet de générer une liste de groupe aléatoire de taille donnée nbGroup à partir des e-mails contenus dans le fichier listOfAddress.utf8 dans le but d'effectuer une campagne de farce par mail forgé.

Les groupes sont constitués d'un expéditeur et d'au moins 2 destinataires.

##### Attributs

###### tab: Personne[\][]

Stock les groupes formés aléatoirement. Chaque premier membre de groupe est l'expéditeur et le reste sont les destinataires.

##### Méthodes

###### getGrpTab(k: int): Personne[]

Permet d'obtenir le groupe *k* de la liste de groupes formés aléatoirement.

#### Mail

Cette classe permet de générer une liste de requêtes SMTP permettant l'envoi d'un mail forgé de la part d'un expéditeur donné et de destinataires donnés. Ces derniers sont à fournir sous forme de tableau de Personne. Le premier du tableau correspond à l'expéditeur et les autres aux destinataires.

Le corps du mail est choisi aléatoirement dans le fichier prank.utf8.

##### Attributs

###### msg: String[]

Correspond à la liste des requêtes qui permettent l'envoi d'un mail.

#### ClientSMTP

##### Attributs

###### LOG: Logger

Logger pour la classe ClientSMTP.

###### nomServeur: String

Nom d'hôte du serveur SMTP.

###### port: int

Port du serveur SMTP.

###### mail: Mail

mail qui va être envoyé à l'aide du client SMTP.

##### Méthodes

###### SMTPRequests(): void

Initie la connexion avec le serveur SMTP et lance la fonction *handler* afin d'envoyer le mail.

###### handler(clientSocket: Socket, requete: String[]): void

Gère l'envoi des requêtes fournies en paramètres au serveur SMTP qui se trouve à l'autre bout du Socket fourni.

### Fonctionnement

![scenario](../media/scenario.jpg)



