# TP2 — ORM JPA Hibernate Spring Data

## C'est quoi l'ORM ?

Normalement, pour sauvegarder un objet Java dans une base de données, on doit écrire du SQL à la main :
```sql
INSERT INTO product (name, price, quantity) VALUES ('Ordinateur', 15000, 10);
```

Avec **JPA + Hibernate**, on n'écrit plus de SQL. On travaille directement avec des objets Java, et Hibernate se charge de traduire ça en SQL tout seul.

C'est ce qu'on appelle un **ORM** (Object-Relational Mapping) : faire le lien entre les objets Java et les tables SQL.

---

## Ce qu'on a construit

### Partie 1 — Gestion des Produits

On a créé une entité `Product` avec 4 attributs :

| Attribut | Type | Description |
|----------|------|-------------|
| id | Long | Identifiant unique (généré automatiquement) |
| name | String | Nom du produit |
| price | double | Prix |
| quantity | int | Quantité en stock |

L'annotation `@Entity` dit à JPA que cette classe correspond à une table dans la base de données. Hibernate crée la table automatiquement au démarrage.

```java
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int quantity;
}
```

#### Le Repository — notre accès aux données
Avec Spring Data, on crée juste une interface. On n'écrit pas de code, Spring fait tout :

```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContains(String keyword);  // chercher par nom
    List<Product> findByPriceLessThan(double price);   // filtrer par prix
}
```

#### Les opérations testées
- **Ajouter** des produits
- **Consulter** tous les produits
- **Consulter** un produit par son id
- **Chercher** par mot-clé
- **Mettre à jour** un produit
- **Supprimer** un produit

---

### Partie 2 — Système Hospitalier

On a modélisé un système de gestion d'une clinique avec plusieurs entités liées entre elles.

#### Les entités et leurs relations

```
Patient ──────────────────────────────────────────┐
  │ (un patient peut avoir plusieurs rendez-vous)  │
  │                                                │
  ▼                                                │
RendezVous ◄─── Medecin                           │
  │                                               │
  ▼                                               │
Consultation                                      │
                                                  │
AppUser ◄────────────────► AppRole               │
  (plusieurs users peuvent avoir plusieurs rôles) │
```

**Patient** a :
- nom, dateNaissance, malade (oui/non), score
- une liste de rendez-vous (`@OneToMany`)

**Medecin** a :
- nom, email, spécialité
- une liste de rendez-vous (`@OneToMany`)

**RendezVous** relie un patient et un médecin :
- date, statut (PENDING / CANCELLED / DONE)
- lien vers Patient (`@ManyToOne`)
- lien vers Medecin (`@ManyToOne`)
- une consultation associée (`@OneToOne`)

**Consultation** :
- date de consultation, rapport médical
- lien vers le rendez-vous (`@OneToOne`)

**AppUser / AppRole** :
- Un utilisateur peut avoir plusieurs rôles
- Un rôle peut être attribué à plusieurs utilisateurs
- Relation `@ManyToMany` avec une table de jonction `user_roles`

---

### Migration H2 → MySQL

Au départ, on utilise **H2** : une base de données qui vit en mémoire (parfaite pour les tests, pas besoin d'installer quoi que ce soit).

Pour passer à **MySQL** (pour la production), il suffit de modifier `application.properties` :

```properties
# Commenter H2 :
# spring.datasource.url=jdbc:h2:mem:tp2db

# Décommenter MySQL :
spring.datasource.url=jdbc:mysql://localhost:3306/tp2db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
```

---

## Structure du projet

```
tp2-orm/
├── entities/
│   ├── Product.java        ← Entité produit
│   ├── Patient.java        ← Entité patient
│   ├── Medecin.java        ← Entité médecin
│   ├── RendezVous.java     ← Rendez-vous (relie patient et médecin)
│   ├── Consultation.java   ← Compte rendu médical
│   ├── AppUser.java        ← Utilisateur
│   ├── AppRole.java        ← Rôle
│   └── StatusRDV.java      ← Enum : PENDING, CANCELLED, DONE
├── repositories/
│   ├── ProductRepository.java
│   ├── PatientRepository.java
│   ├── MedecinRepository.java
│   ├── RendezVousRepository.java
│   ├── ConsultationRepository.java
│   ├── AppUserRepository.java
│   └── AppRoleRepository.java
└── Tp2OrmApplication.java  ← Point d'entrée + données de test
```

---

## Comment lancer

```bash
cd tp2-orm
mvn spring-boot:run
```

Les résultats s'affichent dans la console. On peut aussi ouvrir la console H2 sur :
**http://localhost:8080/h2-console**
(JDBC URL : `jdbc:h2:mem:tp2db`, username : `sa`, password : vide)

---

## Ce qu'on a appris

- Transformer une classe Java en table SQL avec `@Entity`
- Les relations entre tables : `@OneToMany`, `@ManyToOne`, `@OneToOne`, `@ManyToMany`
- Utiliser **Spring Data JPA** pour faire des requêtes sans écrire de SQL
- La différence entre H2 (développement) et MySQL (production)
- Comment Hibernate génère le schéma automatiquement (`ddl-auto=create-drop`)
