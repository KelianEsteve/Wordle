## Auteurs

Calvin Tom, Alguazil Florian, Allegre Romain, Esteve Kelian


## Présentation du logiciel

Le logiciel est basé sur le jeu du wordle. Au début de la partie, un mot aléatoire est sélectionné et le joueur dispose de six essais pour le deviner. Pour chaque tentative, un indicateur visuel informe si chaque lettre est bien positionnée dans le mot ou non.


## Organisation du code source

- **Main :** création de l'instance principale du jeu

- **Wordle :** création, gestion de la scène du jeu et recuperation des indices

- **GameWordle :** gestion des interactions de la grille

- **Dictionary :** récupération du dictionnaire

- **Difficulty :** définition des niveaux de difficulté

- **Score :** gestion du score

- **Timer :** gestion du timer

- **VirtualKeyboard et KeyButton :** gestion des touches et du clavier virtuel

- **TextBox :** gestion des cases de la grille

- **WordleGrid :** gestion de la grille et méthodes d'interactions grille/clavier


## Ressources nécessaires

1. **IDE Eclipse avec la bibliothèque JavaFX**

2. **IDE externe pour lancer le serveur Python et générer les indices**


## Procedure d'execution

1. **Lancer le programme python pour prendre en charge les indices (si ce dernier n'est pas lance le jeu fonctionnera snas indices)**

2. **Executer le main du wordle dans eclipse**


## Bibliotheque utilisees

1. java.fx

2. java.io

3. java.net

4. java.util

5. java.text


## References bibliographiques

1. [Worlde](https://wordle.louan.me/)

2. [JavaDoc](https://docs.oracle.com/javase/7/docs/api/)

3. model utilise pour la generation d'indices : word2vec