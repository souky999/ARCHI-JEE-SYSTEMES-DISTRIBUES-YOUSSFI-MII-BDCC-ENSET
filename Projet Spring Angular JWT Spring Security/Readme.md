# 🏦 Projet Architecture JEE : Gestion Bancaire Intelligente avec Agent AI
### Évaluation Finale - Projet Spring Boot, Angular, JWT & Spring Security

**👨‍🏫 Enseignant :** M. Mohamed Youssfi  
**👤 Réalisé par :** Abderrahim Bajji  

---

## 📝 Description du Projet
Ce projet consiste en une application d'entreprise full-stack permettant de gérer des comptes bancaires (Courants et Épargnes) et leurs opérations associées (Débits et Crédits). L'application intègre un système d'audit traçant l'auteur de chaque action, un tableau de bord visuel pour l'aide à la décision, ainsi qu'un agent d'intelligence artificielle (Chatbot RAG) connecté à un client Telegram pour répondre aux questions des utilisateurs sur la réglementation bancaire.

---

## 🚀 Fonctionnalités Clés

### 1. Backend (Architecture Restful & Métier)
* **Modélisation JPA Avancée :** Gestion de l'héritage des comptes (`SINGLE_TABLE`) pour différencier les Comptes Courants (avec découvert autorisé) et les Comptes Épargne (avec taux d'intérêt).
* **Traçabilité & Audit :** Enregistrement automatique de l'identifiant de l'utilisateur authentifié ayant effectué chaque ajout ou modification sur les clients, comptes et opérations.
* **Documentation :** API entièrement documentée et testable via Swagger UI.

### 2. Sécurité (Spring Security & JWT)
* Sécurisation des endpoints REST par l'authentification basée sur les **JSON Web Tokens (JWT)**.
* Gestion des utilisateurs, des rôles, du processus de connexion et de modification des mots de passe.
* Configuration stricte des règles CORS pour sécuriser les échanges avec le frontend.

### 3. Frontend (Angular & Dashboard)
* Interface utilisateur moderne et intuitive pour la gestion CRUD des clients et des comptes (saisie, recherche, édition, suppression).
* **Éléments Visuels Riches :** Utilisation d'icônes dynamiques (vert pour les crédits, rouge pour les débits), d'avatars pour les profils clients, et d'un design sous forme de cartes (*cards*).
* UI enrichie avec une icône de microphone suggérant une commande vocale et une section de localisation pour le profil des clients.
* **Dashboard Décisionnel :** Intégration de graphiques via `ng2-charts` (ChartJS) montrant la répartition des types de comptes et l'évolution temporelle des soldes.

### 4. Agent AI & Chatbot RAG (Retrieval-Augmented Generation)
* Implémentation d'un service d'intelligence artificielle basé sur **Spring AI** et l'API **OpenAI**.
* Utilisation de la technique **RAG** avec un stockage vectoriel en mémoire (`SimpleVectorStore`) pour ingérer des documents de réglementation bancaire.
* **Intégration Telegram :** Un bot Telegram connecté écoute les requêtes des utilisateurs, interroge la base documentaire vectorielle, et fournit des réponses personnalisées et précises.

---

## 🛠️ Technologies Utilisées

* **Backend :** Spring Boot 3.x, Spring Data JPA, Spring Security, Spring AI, Lombok.
* **Documentation API :** Springdoc OpenAPI Starter WebMVC UI (v2.1.0).
* **Base de Données :** H2 Database (In-Memory).
* **Frontend :** Angular, TypeScript, TailwindCSS / Bootstrap.
* **Graphiques :** ChartJS / ng2-charts.
* **IA & Messagerie :** OpenAI API, Telegram Bots Core.

---

## 📦 Structure et Installation

### Prérequis
* Java 17 ou supérieur
* Node.js & Angular CLI v15+
* Un Token de Bot Telegram (obtenu via `@BotFather`)
* Une clé d'API OpenAI valide

### 1. Configuration et Démarrage du Backend
1. Clonez le projet et accédez au dossier backend.
2. Ouvrez le fichier `src/main/resources/application.properties` et configurez vos clés secrètes :
   ```properties
   # Configuration OpenAI & Telegram
   spring.ai.openai.api-key=VOTRE_CLE_OPENAI
   telegram.bot.token=VOTRE_TOKEN_TELEGRAM
   telegram.bot.username=NOM_DE_VOTRE_BOT