# 📱 SchemaFlash

SchemaFlash est une application Android développée en Kotlin avec Jetpack Compose. Elle permet d’afficher et d’apprendre des fiches pédagogiques illustrées. Chaque fiche contient une image, un titre et des zones à compléter.

## 🚀 Fonctionnalités

- 📷 Affichage d’images annotées pour l'apprentissage visuel
- 🧠 Mode "Apprentissage" avec zones de texte à compléter
- ➕ Ajout de nouvelles fiches (sur la branche `main`)
- 🔗 Intégration Firebase Firestore + Storage (sur la branche `firebase`)

---

## 🌿 Branches disponibles

### `main` 🌟
- **Fonctionnalités complètes** : ajouter, afficher, apprendre des fiches
- ⚠️ Les données sont **en dur dans le code** (pas de persistance)
- Utilisé pour le développement local rapide et les tests UI

### `firebase` 🔥
- Connexion à **Firebase Firestore et Storage**
- Affichage dynamique des fiches depuis la base de données
- **Pas encore de création de fiches** via l'application
- Base pour les fonctionnalités de persistance dans le cloud

---

## 🧱 Stack technique

- **Langage** : Kotlin
- **UI** : Jetpack Compose
- **Architecture** : Navigation Compose, State Hoisting
- **Base de données** : Firebase Firestore
- **Stockage images** : Firebase Storage
- **Image loading** : Coil (pour charger les images via URL)

---

## 📦 Structure du projet

/fr.ensim.android.schemaflash
│
├── MainActivity.kt # Navigation et logique globale
├── PageAccueilScreen.kt # Page d’accueil avec la grille des fiches
├── FlashCard.kt # Composable représentant une fiche
├── AjouterFicheScreen.kt # Écran de sélection d'image
├── CreerFicheScreen.kt # Formulaire de création de fiche
├── ApprendreFicheScreen.kt # Mode apprentissage avec zones à compléter
├── FlashCardData.kt # Modèle de données (titre, image, zones)
├── Firestore / Storage utils # Fonctions pour interagir avec Firebase


---

## 🔧 Lancer l’application

1. Clone le repo :
```bash
git clone https://github.com/Eileda6/SchemaFlash.git
cd SchemaFlash
