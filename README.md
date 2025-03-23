# Projet Immobilier - Documentation

Ce projet est une application web pour la gestion immobilière, développée avec Angular. Il permet aux utilisateurs de gérer et de visualiser des biens immobiliers.

Il a été pensé "idx-first" mais peut être lancé sur n'importe quel environnement avec quelques modifications.

## Technologies utilisées

Le projet utilise les technologies suivantes :

* **Frontend :**
    * Angular (version 14.1.3)
    * Angular Material (version 14.1.3) pour la conception de l'interface utilisateur.
    * `@angular/animations` (14.1.3)
    * `@angular/cdk` (14.1.3)
    * `@angular/common` (14.1.3)
    * `@angular/compiler` (14.1.3)
    * `@angular/core` (14.1.3)
    * `@angular/flex-layout` (14.0.0-beta.40)
    * `@angular/forms` (14.1.3)
    * `@angular/material` (14.1.3)
    * `@angular/platform-browser` (14.1.3)
    * `@angular/platform-browser-dynamic` (14.1.3)
    * `@angular/router` (14.1.3)
    * `ngx-material-file-input` (4.0.0) pour la gestion d'upload de fichier.
    * `rxjs` (7.5.6)
    * `tslib` (2.4.0)
    * `zone.js` (0.11.8)

* **Backend :**
    * Maven **3.9.6**
    * Java Development Kit (JDK) **17**
    * Spring Boot **3.1.5**
    * `spring-boot-starter-web` **3.1.5**
    * `spring-boot-starter-data-jpa` **3.1.5**
    * `spring-boot-starter-security` **3.1.5**
    * `spring-boot-starter-validation` **3.1.5**
    * MySQL Server **8.0.35**
    * `mysql-connector-java` **8.0.33** : Driver JDBC pour la connexion à MySQL.

## Base de données MySQL

Le schéma de la base de données MySQL se trouve dans `scripts/db-init.sql`.

Le script pour créer des données de test se trouve dans `scripts/db-mock-data.sql`.

La base de donnée peut être initialisée directement en tant que service dans le fichier .nix si vous utilisez projet IDX, ou bien avec docker en utilisant le fichier docker/docker-compose.

Si vous utilisez Docker n'oubliez pas de changer l'url de la base de donnée dans le fichier .nix

## Prérequis

Avant de commencer, assurez-vous d'avoir les éléments suivants installés :

* Node.js (version recommandée : 18)
* npm (gestionnaire de paquets Node.js)

## Installation et configuration

1. **Cloner le dépôt :**

    ```bash
    git clone https://github.com/Theodagan/oc3-chatop.git
    cd estate
    ```

2. **Installer les dépendances :**

    ```bash
    npm install
    ```

## Lancement du projet

Pour lancer l'application en mode développement, utiliser les commandes intégrées à idx en démarrant simplement le projet. 

Pour lancer l'application sur un autre environnement  utiliser le script de démarrage disponible dans `scripts/start_project.sh`
    
## Scripts disponibles

Les scripts suivants sont disponibles dans le fichier `package.json` :

*   `ng` : Commande Angular CLI.
*   `start` : Lance le serveur de développement Angular.
*   `build` : Construit l'application pour la production.
*   `watch` : Construit l'application en mode "watch" pour le développement.
*   `test` : Lance les tests unitaires.
*   `lint` : Lance l'analyse lint pour vérifier la qualité du code.

## Configuration de l'environnement avec IDX

Ce projet est pensé pour être utilisé dans Project IDX, l'environnement de développement web de Google.

### Personnalisation de l'environnement

Pour personnaliser l'environnement, modifiez le fichier `.idx/dev.nix`. Ce fichier utilise le langage Nix pour définir la configuration de l'environnement.

Exemple de configuration dans `.idx/dev.nix` :
```nix
    { pkgs, ... }: {
    # Canal nixpkgs à utiliser.
    channel = "stable-23.11"; # ou "unstable"

    # Services tiers requis par l'environement
    services = {
        #mysql, docker, etc..
        mysql = {enable = true;};
    };

    # Utiliser https://search.nixos.org/packages pour trouver des paquets
    packages = [ pkgs.nodejs_18 ];

    # Définit les variables d'environnement dans l'espace de travail
    env = { SOME_ENV_VAR = "hello"; };

    # Recherchez les extensions que vous souhaitez sur https://open-vsx.org/ et utilisez "éditeur.id"
    idx.extensions = [ "angular.ng-template" ];

    # Activer et configurer les aperçus 
    idx.previews = {
            enable = true;
            previews = {
                web = {
                command = [ "npm" "run" "start" "--" "--port" "\$PORT" "--host" "0.0.0.0" "--disable-host-check" ];
                manager = "web";
                };
            };
        };  
    }

    # Configuration de l'environnement
    idx.workspace = {
      onCreate = {
        install = ''
          #commands to run on creation of the environnement
          cd backend && mvn clean install # Build backend
          cd .. && cd frontend && npm install # Install frontend deps
        '';
      };
      onStart = {
      runServer = ''
            #commands to run on build of the environnement
            cd backend && mvn spring-boot:run &> /dev/null &
            cd ../frontend && ng serve 
        '';
      };
    
    };
```

Pour plus d'informations sur Project IDX, consultez le guide : [https://developers.google.com/idx/guides/introduction](https://developers.google.com/idx/guides/introduction)

## Configuration de l'environnement sans IDX

1. Déplacer les variables d'environnement de `dev.nix` à `.env`
